package com.facilit.kanban_backend.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facilit.kanban_backend.domain.entity.Projeto;
import com.facilit.kanban_backend.domain.entity.ProjetoStatus;
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
    public Projeto criarOuAtualizar(Projeto p, Set<UUID> responsaveisIds) {
        if (responsaveisIds != null) {
            Set<Responsavel> rs = responsavelRepository.findAllById(responsaveisIds).stream()
                    .collect(Collectors.toSet());
            p.setResponsaveis(rs);
        }
        aplicarRegrasCalculo(p);
        return projetoRepository.save(p);
    }

    public Optional<Projeto> buscar(UUID id) {
        return projetoRepository.findById(id);
    }

    public List<Projeto> listar() {
        return projetoRepository.findAll();
    }

    public List<Projeto> listarPorStatus(ProjetoStatus status) {
        return projetoRepository.findByStatus(status);
    }

    @Transactional
    public void deletar(UUID id) {
        projetoRepository.deleteById(id);
    }

    @Transactional
    public Projeto transicionar(UUID id, ProjetoStatus para) {
        Projeto p = projetoRepository.findById(id).orElseThrow(() -> new BusinessException("Projeto não encontrado"));

        LocalDate hoje = LocalDate.now();
        ProjetoStatus de = p.getStatus();

        switch (de) {
            case A_INICIAR -> {
                if (para == ProjetoStatus.EM_ANDAMENTO) {
                    p.setInicioRealizado(hoje);
                } else if (para == ProjetoStatus.ATRASADO) {
                    if (p.getInicioPrevisto() != null && hoje.compareTo(p.getInicioPrevisto()) < 0) {
                        throw new BusinessException("Não pode marcar ATRASADO antes do início previsto.");
                    }
                } else if (para == ProjetoStatus.CONCLUIDO) {
                    p.setTerminoRealizado(hoje);
                }
            }
            case EM_ANDAMENTO -> {
                if (para == ProjetoStatus.A_INICIAR) {
                    p.setInicioRealizado(null);
                } else if (para == ProjetoStatus.ATRASADO) {
                    throw new BusinessException(
                            "Para marcar ATRASADO partindo de EM_ANDAMENTO, ajuste Início/Término Previsto para data < hoje ou remova Início Realizado.");
                } else if (para == ProjetoStatus.CONCLUIDO) {
                    p.setTerminoRealizado(hoje);
                }
            }
            case ATRASADO -> {
                if (para == ProjetoStatus.A_INICIAR) {
                    throw new BusinessException(
                            "Para voltar a A_INICIAR partindo de ATRASADO, remova Início Realizado e ajuste datas previstas para > hoje.");
                } else if (para == ProjetoStatus.EM_ANDAMENTO) {
                    throw new BusinessException(
                            "Para ir a EM_ANDAMENTO partindo de ATRASADO, ajuste datas previstas para > hoje.");
                } else if (para == ProjetoStatus.CONCLUIDO) {
                    p.setTerminoRealizado(hoje);
                }
            }
            case CONCLUIDO -> {
                if (para == ProjetoStatus.A_INICIAR) {
                    throw new BusinessException(
                            "Para voltar a A_INICIAR partindo de CONCLUIDO, remova Término Realizado e ajuste datas previstas para > hoje.");
                } else if (para == ProjetoStatus.EM_ANDAMENTO) {
                    p.setTerminoRealizado(null);
                    // Validar se não fica ATRASADO
                } else if (para == ProjetoStatus.ATRASADO) {
                    p.setTerminoRealizado(null);
                }
            }
        }

        aplicarRegrasCalculo(p);

        // Se após aplicar efeitos, o status calculado não é o solicitado, bloquear
        if (p.getStatus() != para) {
            throw new BusinessException("Transição inválida com as datas atuais. Status calculado: " + p.getStatus() +
                    ". Ajuste datas conforme regras e tente novamente.");
        }

        return projetoRepository.save(p);
    }

    private void aplicarRegrasCalculo(Projeto p) {
        // Recalcular status por regras
        p.setStatus(calcularStatus(p));
        // Calcular métricas
        p.setPercentualTempoRestante(calcularPercentualTempoRestante(p));
        p.setDiasAtraso(calcularDiasAtraso(p));
    }

    private ProjetoStatus calcularStatus(Projeto p) {
        LocalDate hoje = LocalDate.now();

        boolean inicioRealizadoVazio = p.getInicioRealizado() == null;
        boolean terminoRealizadoVazio = p.getTerminoRealizado() == null;

        // Concluído
        if (!terminoRealizadoVazio) {
            return ProjetoStatus.CONCLUIDO;
        }

        // A iniciar
        if (inicioRealizadoVazio && terminoRealizadoVazio) {
            return ProjetoStatus.A_INICIAR;
        }

        // Em andamento
        if (!inicioRealizadoVazio && terminoRealizadoVazio &&
                (p.getTerminoPrevisto() == null || p.getTerminoPrevisto().isAfter(hoje)
                        || p.getTerminoPrevisto().isEqual(hoje))) {
            return ProjetoStatus.EM_ANDAMENTO;
        }

        // Atrasado
        boolean cond1 = p.getInicioPrevisto() != null && p.getInicioPrevisto().isBefore(hoje) && inicioRealizadoVazio;
        boolean cond2 = p.getTerminoPrevisto() != null && p.getTerminoPrevisto().isBefore(hoje)
                && terminoRealizadoVazio;
        if (cond1 || cond2) {
            return ProjetoStatus.ATRASADO;
        }

        // Default
        return ProjetoStatus.A_INICIAR;
    }

    private int calcularPercentualTempoRestante(Projeto p) {
        LocalDate inicio = p.getInicioPrevisto();
        LocalDate fim = p.getTerminoPrevisto();
        LocalDate hoje = LocalDate.now();

        if (inicio == null || fim == null)
            return 0;

        long total = ChronoUnit.DAYS.between(inicio, fim);
        if (total <= 0)
            return 0;

        long usado = ChronoUnit.DAYS.between(inicio, hoje);
        long restante = total - usado;
        long perc = Math.round((restante * 100.0) / total);
        if (p.getStatus() == ProjetoStatus.CONCLUIDO)
            return 0;
        if (hoje.isAfter(fim))
            return 0;
        if (perc < 0)
            return 0;
        if (perc > 100)
            return 100;
        return (int) perc;
    }

    private int calcularDiasAtraso(Projeto p) {
        LocalDate fimPrev = p.getTerminoPrevisto();
        LocalDate fimReal = p.getTerminoRealizado();
        LocalDate hoje = LocalDate.now();

        if (p.getStatus() == ProjetoStatus.CONCLUIDO)
            return 0;
        if (fimPrev != null && fimReal == null && fimPrev.isBefore(hoje)) {
            return (int) ChronoUnit.DAYS.between(fimPrev, hoje);
        }
        return 0;
    }

    // Indicadores
    public Map<ProjetoStatus, Double> atrasoMedioPorStatus() {
        Map<ProjetoStatus, List<Projeto>> by = projetoRepository.findAll().stream()
                .collect(Collectors.groupingBy(Projeto::getStatus));
        Map<ProjetoStatus, Double> out = new EnumMap<>(ProjetoStatus.class);
        for (var e : by.entrySet()) {
            out.put(e.getKey(), e.getValue().stream().mapToInt(Projeto::getDiasAtraso).average().orElse(0));
        }
        return out;
    }

    public Map<ProjetoStatus, Long> quantidadePorStatus() {
        return projetoRepository.findAll().stream()
                .collect(Collectors.groupingBy(Projeto::getStatus, () -> new EnumMap<>(ProjetoStatus.class),
                        Collectors.counting()));
    }
}
