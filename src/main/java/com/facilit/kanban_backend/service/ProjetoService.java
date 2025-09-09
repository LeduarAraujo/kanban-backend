package com.facilit.kanban_backend.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.baeldung.openapi.model.SucessMessageRepresentation;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facilit.kanban_backend.domain.enums.ProjetoStatusEnum;
import com.facilit.kanban_backend.domain.entity.Responsavel;
import com.facilit.kanban_backend.exception.BusinessException;
import com.facilit.kanban_backend.repository.ProjetoRepository;
import com.facilit.kanban_backend.repository.ResponsavelRepository;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final ResponsavelRepository responsavelRepository;

    public ProjetoService(ProjetoRepository pProjetoRepository, ResponsavelRepository pResponsavelRepository) {
        this.projetoRepository = pProjetoRepository;
        this.responsavelRepository = pResponsavelRepository;
    }

    @Transactional
    public SucessMessageRepresentation criarOuAtualizar(ProjetoEntity pProjetoEntity, Set<Long> responsaveisIds) {
        if (responsaveisIds != null) {
            Set<Responsavel> rs = responsavelRepository.findAllById(responsaveisIds).stream().collect(Collectors.toSet());
            pProjetoEntity.setResponsaveis(rs);
        }
        aplicarRegrasCalculo(pProjetoEntity);
        projetoRepository.save(pProjetoEntity);

        return SucessMessageRepresentation.builder()
                .message("Projeto criado !")
                .code(0)
                .build();
    }

    public Optional<ProjetoEntity> buscar(Long id) {
        return projetoRepository.findById(id);
    }

    public List<ProjetoEntity> listar() {
        return projetoRepository.findAll();
    }

    public List<ProjetoEntity> listarPorStatus(ProjetoStatusEnum status) {
        return projetoRepository.findByStatus(status);
    }

    @Transactional
    public void deletar(Long id) {
        projetoRepository.deleteById(id);
    }

    @Transactional
    public ProjetoEntity transicionar(Long id, ProjetoStatusEnum para) {
        ProjetoEntity projetoEntity = projetoRepository.findById(id).orElseThrow(() -> new BusinessException("Projeto não encontrado"));

        LocalDate hoje = LocalDate.now();
        ProjetoStatusEnum de = projetoEntity.getStatus();

        switch (de) {
            case A_INICIAR -> {
                if (para == ProjetoStatusEnum.EM_ANDAMENTO) {
                    projetoEntity.setInicioRealizado(hoje);
                } else if (para == ProjetoStatusEnum.ATRASADO) {
                    if (projetoEntity.getInicioPrevisto() != null && hoje.compareTo(projetoEntity.getInicioPrevisto()) < 0) {
                        throw new BusinessException("Não pode marcar ATRASADO antes do início previsto.");
                    }
                } else if (para == ProjetoStatusEnum.CONCLUIDO) {
                    projetoEntity.setTerminoRealizado(hoje);
                }
            }
            case EM_ANDAMENTO -> {
                if (para == ProjetoStatusEnum.A_INICIAR) {
                    projetoEntity.setInicioRealizado(null);
                } else if (para == ProjetoStatusEnum.ATRASADO) {
                    throw new BusinessException("Para marcar ATRASADO partindo de EM_ANDAMENTO, ajuste Início/Término Previsto para data < hoje ou remova Início Realizado.");
                } else if (para == ProjetoStatusEnum.CONCLUIDO) {
                    projetoEntity.setTerminoRealizado(hoje);
                }
            }
            case ATRASADO -> {
                if (para == ProjetoStatusEnum.A_INICIAR) {
                    throw new BusinessException("Para voltar a A_INICIAR partindo de ATRASADO, remova Início Realizado e ajuste datas previstas para > hoje.");
                } else if (para == ProjetoStatusEnum.EM_ANDAMENTO) {
                    throw new BusinessException("Para ir a EM_ANDAMENTO partindo de ATRASADO, ajuste datas previstas para > hoje.");
                } else if (para == ProjetoStatusEnum.CONCLUIDO) {
                    projetoEntity.setTerminoRealizado(hoje);
                }
            }
            case CONCLUIDO -> {
                if (para == ProjetoStatusEnum.A_INICIAR) {
                    throw new BusinessException("Para voltar a A_INICIAR partindo de CONCLUIDO, remova Término Realizado e ajuste datas previstas para > hoje.");
                } else if (para == ProjetoStatusEnum.EM_ANDAMENTO) {
                    projetoEntity.setTerminoRealizado(null);
                    // Validar se não fica ATRASADO
                } else if (para == ProjetoStatusEnum.ATRASADO) {
                    projetoEntity.setTerminoRealizado(null);
                }
            }
        }

        aplicarRegrasCalculo(projetoEntity);

        // Se após aplicar efeitos, o status calculado não é o solicitado, bloquear
        if (projetoEntity.getStatus() != para) {
            throw new BusinessException("Transição inválida com as datas atuais. Status calculado: " + projetoEntity.getStatus() + ". Ajuste datas conforme regras e tente novamente.");
        }

        return projetoRepository.save(projetoEntity);
    }

    private void aplicarRegrasCalculo(ProjetoEntity pProjetoEntity) {
        // Recalcular status por regras
        pProjetoEntity.setStatus(calcularStatus(pProjetoEntity));
        // Calcular métricas
        pProjetoEntity.setPercentualTempoRestante(calcularPercentualTempoRestante(pProjetoEntity));
        pProjetoEntity.setDiasAtraso(calcularDiasAtraso(pProjetoEntity));
    }

    private ProjetoStatusEnum calcularStatus(ProjetoEntity pProjetoEntity) {
        LocalDate hoje = LocalDate.now();

        boolean inicioRealizadoVazio = pProjetoEntity.getInicioRealizado() == null;
        boolean terminoRealizadoVazio = pProjetoEntity.getTerminoRealizado() == null;

        // Concluído
        if (!terminoRealizadoVazio) {
            return ProjetoStatusEnum.CONCLUIDO;
        }

        // A iniciar
        if (inicioRealizadoVazio && terminoRealizadoVazio) {
            return ProjetoStatusEnum.A_INICIAR;
        }

        // Em andamento
        if (!inicioRealizadoVazio && terminoRealizadoVazio && (pProjetoEntity.getTerminoPrevisto() == null || pProjetoEntity.getTerminoPrevisto().isAfter(hoje) || pProjetoEntity.getTerminoPrevisto().isEqual(hoje))) {
            return ProjetoStatusEnum.EM_ANDAMENTO;
        }

        // Atrasado
        boolean cond1 = pProjetoEntity.getInicioPrevisto() != null && pProjetoEntity.getInicioPrevisto().isBefore(hoje) && inicioRealizadoVazio;
        boolean cond2 = pProjetoEntity.getTerminoPrevisto() != null && pProjetoEntity.getTerminoPrevisto().isBefore(hoje) && terminoRealizadoVazio;
        if (cond1 || cond2) {
            return ProjetoStatusEnum.ATRASADO;
        }

        // Default
        return ProjetoStatusEnum.A_INICIAR;
    }

    private int calcularPercentualTempoRestante(ProjetoEntity pProjetoEntity) {
        LocalDate inicio = pProjetoEntity.getInicioPrevisto();
        LocalDate fim = pProjetoEntity.getTerminoPrevisto();
        LocalDate hoje = LocalDate.now();

        if (inicio == null || fim == null) return 0;

        long total = ChronoUnit.DAYS.between(inicio, fim);
        if (total <= 0) return 0;

        long usado = ChronoUnit.DAYS.between(inicio, hoje);
        long restante = total - usado;
        long perc = Math.round((restante * 100.0) / total);
        if (pProjetoEntity.getStatus() == ProjetoStatusEnum.CONCLUIDO) return 0;
        if (hoje.isAfter(fim)) return 0;
        if (perc < 0) return 0;
        if (perc > 100) return 100;
        return (int) perc;
    }

    private int calcularDiasAtraso(ProjetoEntity pProjetoEntity) {
        LocalDate fimPrev = pProjetoEntity.getTerminoPrevisto();
        LocalDate fimReal = pProjetoEntity.getTerminoRealizado();
        LocalDate hoje = LocalDate.now();

        if (pProjetoEntity.getStatus() == ProjetoStatusEnum.CONCLUIDO) return 0;
        if (fimPrev != null && fimReal == null && fimPrev.isBefore(hoje)) {
            return (int) ChronoUnit.DAYS.between(fimPrev, hoje);
        }
        return 0;
    }

    // Indicadores
    public Map<ProjetoStatusEnum, Double> atrasoMedioPorStatus() {
        Map<ProjetoStatusEnum, List<ProjetoEntity>> by = projetoRepository.findAll().stream().collect(Collectors.groupingBy(ProjetoEntity::getStatus));
        Map<ProjetoStatusEnum, Double> out = new EnumMap<>(ProjetoStatusEnum.class);
        for (var e : by.entrySet()) {
            out.put(e.getKey(), e.getValue().stream().mapToInt(ProjetoEntity::getDiasAtraso).average().orElse(0));
        }
        return out;
    }

    public Map<ProjetoStatusEnum, Long> quantidadePorStatus() {
        return projetoRepository.findAll().stream().collect(Collectors.groupingBy(ProjetoEntity::getStatus, () -> new EnumMap<>(ProjetoStatusEnum.class), Collectors.counting()));
    }
}
