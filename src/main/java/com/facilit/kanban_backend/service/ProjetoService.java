package com.facilit.kanban_backend.service;

import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import com.facilit.kanban_backend.dto.RespostaContagemProjetosPorStatusDTO;
import com.facilit.kanban_backend.exception.BusinessException;
import com.facilit.kanban_backend.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoEntity salvar(ProjetoEntity projeto) {
        recalcularMetricasEStatus(projeto);
        return projetoRepository.save(projeto);
    }

    public ProjetoEntity buscarOuFalhar(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Projeto não encontrado"));
    }

    public Page<ProjetoEntity> listar(Pageable pageable) {
        return projetoRepository.findAll(pageable);
    }

    public Page<ProjetoEntity> listarPorStatus(StatusProjetoEnum status, Pageable pageable) {
        return projetoRepository.findByStatus(status, pageable);
    }

    public ProjetoEntity moverStatus(Long id, StatusProjetoEnum novoStatus) {
        ProjetoEntity projeto = buscarOuFalhar(id);

        aplicarAcoesAutomaticasDeTransicao(projeto, novoStatus);
        recalcularMetricasEStatus(projeto);

        if (projeto.getStatus() != novoStatus) {
            throw new BusinessException(mensagemInconsistenciaStatus(projeto, novoStatus));
        }

        return projetoRepository.save(projeto);
    }

    public List<RespostaContagemProjetosPorStatusDTO> quantidadeProjetosPorStatus() {
        return projetoRepository.countProjetosByStatus();
    }

    public Double mediaDiasAtrasoPorStatus(StatusProjetoEnum status) {
        return projetoRepository.avgDiasAtrasoByStatus(status);
    }

    private void aplicarAcoesAutomaticasDeTransicao(ProjetoEntity projeto, StatusProjetoEnum novoStatus) {
        StatusProjetoEnum atual = projeto.getStatus();
        LocalDateTime agora = LocalDateTime.now();

        // A iniciar -> Em andamento
        if (atual == StatusProjetoEnum.A_INICIAR && novoStatus == StatusProjetoEnum.EM_ANDAMENTO) {
            projeto.setInicioRealizado(agora);
            return;
        }
        // A iniciar -> Concluido
        if (atual == StatusProjetoEnum.A_INICIAR && novoStatus == StatusProjetoEnum.CONCLUIDO) {
            projeto.setTerminoRealizado(agora);
            return;
        }
        // Em andamento -> A iniciar
        if (atual == StatusProjetoEnum.EM_ANDAMENTO && novoStatus == StatusProjetoEnum.A_INICIAR) {
            projeto.setInicioRealizado(null);
            return;
        }
        // A iniciar -> Atrasado
        if (atual == StatusProjetoEnum.A_INICIAR && novoStatus == StatusProjetoEnum.ATRASADO) {
            LocalDate hoje = LocalDate.now();
            if (projeto.getInicioPrevisto() != null && projeto.getInicioPrevisto().toLocalDate().isAfter(hoje)) {
                throw new BusinessException("Não é permitido mover para ATRASADO antes do Início Previsto. Ajuste as datas.");
            }
            // sem ação automática; recálculo determinará se realmente é atrasado
            return;
        }

        // Em andamento -> Atrasado (bloquear; orientar)
        if (atual == StatusProjetoEnum.EM_ANDAMENTO && novoStatus == StatusProjetoEnum.ATRASADO) {
            throw new BusinessException("Para ir a ATRASADO a partir de EM ANDAMENTO, remova Início Realizado (voltará a não iniciado) ou ajuste Início/Término Previsto para data < hoje.");
        }

        // Em andamento -> Concluido
        if (atual == StatusProjetoEnum.EM_ANDAMENTO && novoStatus == StatusProjetoEnum.CONCLUIDO) {
            projeto.setTerminoRealizado(agora);
            return;
        }

        // Atrasado -> A iniciar (bloquear; orientar)
        if (atual == StatusProjetoEnum.ATRASADO && novoStatus == StatusProjetoEnum.A_INICIAR) {
            throw new BusinessException("Ajuste Início/Término Previsto para datas >= hoje e garanta Início/Término Realizados vazios para voltar a A INICIAR.");
        }

        // Atrasado -> Em andamento (bloquear; orientar)
        if (atual == StatusProjetoEnum.ATRASADO && novoStatus == StatusProjetoEnum.EM_ANDAMENTO) {
            throw new BusinessException("Para ir a EM ANDAMENTO a partir de ATRASADO, ajuste Início/Término Previsto para datas > hoje e defina Início Realizado.");
        }

        // Atrasado -> Concluido
        if (atual == StatusProjetoEnum.ATRASADO && novoStatus == StatusProjetoEnum.CONCLUIDO) {
            projeto.setTerminoRealizado(agora);
            return;
        }

        // Concluido -> A iniciar (bloquear; orientar)
        if (atual == StatusProjetoEnum.CONCLUIDO && novoStatus == StatusProjetoEnum.A_INICIAR) {
            throw new BusinessException("Remova Término Realizado e ajuste Início/Término Previsto para data > hoje para voltar a A INICIAR.");
        }

        // Concluido -> Em andamento (remover término; validar não vira ATRASADO)
        if (atual == StatusProjetoEnum.CONCLUIDO && novoStatus == StatusProjetoEnum.EM_ANDAMENTO) {
            projeto.setTerminoRealizado(null);
            StatusProjetoEnum statusApósRemocao = calcularStatus(projeto);
            if (statusApósRemocao == StatusProjetoEnum.ATRASADO) {
                throw new BusinessException("Remover Término Realizado levará o projeto a ATRASADO. Ajuste as datas previstas antes.");
            }
            return;
        }

        // Concluido -> Atrasado (permitir apenas se ao remover término, regras classificarem como ATRASADO)
        if (atual == StatusProjetoEnum.CONCLUIDO && novoStatus == StatusProjetoEnum.ATRASADO) {
            projeto.setTerminoRealizado(null);
            StatusProjetoEnum statusApósRemocao = calcularStatus(projeto);
            if (statusApósRemocao != StatusProjetoEnum.ATRASADO) {
                throw new BusinessException("Para ir a ATRASADO a partir de CONCLUIDO, ao remover Término Realizado o status deve ficar ATRASADO; ajuste as datas.");
            }
            return;
        }
    }

    private void recalcularMetricasEStatus(ProjetoEntity projeto) {
        StatusProjetoEnum statusCalculado = calcularStatus(projeto);
        projeto.setStatus(statusCalculado);
        projeto.setDiasAtraso(calcularDiasAtraso(projeto));
        projeto.setPercentualTempoRestante(calcularPercentualTempoRestante(projeto));
    }

    public StatusProjetoEnum calcularStatus(ProjetoEntity p) {
        LocalDate hoje = LocalDate.now();

        if (p.getTerminoRealizado() != null) {
            return StatusProjetoEnum.CONCLUIDO;
        }

        boolean inicioRealizado = p.getInicioRealizado() != null;
        boolean terminoPrevistoMaiorHoje = p.getTerminoPrevisto() != null && p.getTerminoPrevisto().toLocalDate().isAfter(hoje);
        boolean terminoRealizadoVazio = p.getTerminoRealizado() == null;

        if (inicioRealizado && terminoPrevistoMaiorHoje && terminoRealizadoVazio) {
            return StatusProjetoEnum.EM_ANDAMENTO;
        }

        boolean inicioPrevistoMenorHojeESemInicioRealizado = p.getInicioPrevisto() != null
                && p.getInicioPrevisto().toLocalDate().isBefore(hoje)
                && p.getInicioRealizado() == null;

        boolean terminoPrevistoMenorHojeESemTerminoRealizado = p.getTerminoPrevisto() != null
                && p.getTerminoPrevisto().toLocalDate().isBefore(hoje)
                && p.getTerminoRealizado() == null;

        if (inicioPrevistoMenorHojeESemInicioRealizado || terminoPrevistoMenorHojeESemTerminoRealizado) {
            return StatusProjetoEnum.ATRASADO;
        }

        if (p.getInicioRealizado() == null && p.getTerminoRealizado() == null) {
            return StatusProjetoEnum.A_INICIAR;
        }

        return p.getStatus() != null ? p.getStatus() : StatusProjetoEnum.A_INICIAR;
    }

    public int calcularPercentualTempoRestante(ProjetoEntity p) {
        if (p.getInicioPrevisto() == null || p.getTerminoPrevisto() == null) {
            return 0;
        }

        long total = ChronoUnit.DAYS.between(p.getInicioPrevisto().toLocalDate(), p.getTerminoPrevisto().toLocalDate());
        if (total <= 0) {
            return 0;
        }

        long usado = ChronoUnit.DAYS.between(p.getInicioPrevisto().toLocalDate(), LocalDate.now());
        long restante = total - usado;

        if (p.getStatus() == StatusProjetoEnum.CONCLUIDO) {
            return 0;
        }
        if (LocalDate.now().isAfter(p.getTerminoPrevisto().toLocalDate())) {
            return 0;
        }

        long percent = Math.max(0, (restante * 100) / total);
        return (int) percent;
    }

    public int calcularDiasAtraso(ProjetoEntity p) {
        if (p.getStatus() == StatusProjetoEnum.CONCLUIDO) {
            return 0;
        }
        if (p.getTerminoRealizado() == null && p.getTerminoPrevisto() != null
                && p.getTerminoPrevisto().toLocalDate().isBefore(LocalDate.now())) {
            long dias = ChronoUnit.DAYS.between(p.getTerminoPrevisto().toLocalDate(), LocalDate.now());
            return (int) Math.max(0, dias);
        }
        return 0;
    }

    private String mensagemInconsistenciaStatus(ProjetoEntity p, StatusProjetoEnum desejado) {
        String base = "Transição inválida: status calculado='" + calcularStatus(p) + "' diferente do solicitado='" + desejado + "'. ";
        switch (desejado) {
            case EM_ANDAMENTO:
                return base + "Dica: defina Início Realizado (hoje) e garanta Término Previsto > hoje.";
            case ATRASADO:
                return base + "Dica: verifique se Início Previsto < hoje sem Início Realizado, ou Término Previsto < hoje sem Término Realizado.";
            case CONCLUIDO:
                return base + "Dica: defina Término Realizado.";
            case A_INICIAR:
            default:
                return base + "Dica: limpe Início/Término Realizados.";
        }
    }
}
