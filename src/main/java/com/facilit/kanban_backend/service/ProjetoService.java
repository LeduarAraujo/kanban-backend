package com.facilit.kanban_backend.service;

import com.baeldung.openapi.model.*;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ResponsavelEntity;
import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import com.facilit.kanban_backend.exception.BusinessException;
import com.facilit.kanban_backend.mapper.ProjetoMapper;
import com.facilit.kanban_backend.repository.ItemProjetoRepository;
import com.facilit.kanban_backend.repository.ProjetoRepository;
import com.facilit.kanban_backend.repository.ResponsavelRepository;
import com.facilit.kanban_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ItemProjetoRepository itemProjetoRepository;

    @Transactional(rollbackFor = Exception.class)
    public ProjetoRepresentation cadastrarProjeto(String pAcessToken, Long pIdUsuarioLogado
            , CadastrarProjetoRequestRepresentation pCadastrarProjetoRequestRepresentation) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        ProjetoEntity projeto = new ProjetoEntity();

        projeto.setNome(pCadastrarProjetoRequestRepresentation.getNome());
        projeto.setDescricao(pCadastrarProjetoRequestRepresentation.getDescricao());
        projeto.setStatus(StatusProjetoEnum.valueOf(pCadastrarProjetoRequestRepresentation.getStatus().toString()));
        projeto.setInicioPrevisto(pCadastrarProjetoRequestRepresentation.getDtInicioPrevisto());
        projeto.setTerminoPrevisto(pCadastrarProjetoRequestRepresentation.getDtTerminoPrevisto());
        projeto.setInicioRealizado(pCadastrarProjetoRequestRepresentation.getDtInicioRealizado());
        projeto.setTerminoRealizado(pCadastrarProjetoRequestRepresentation.getDtTerminoRealizado());

        // 🔹 Buscar os responsáveis a partir da lista de objetos que contêm o id
        Set<ResponsavelEntity> responsaveis = pCadastrarProjetoRequestRepresentation.getResponsavelId().stream()
                .map(inner -> responsavelRepository.findById(inner.getId())
                        .orElseThrow(() -> new RuntimeException("Responsável não encontrado: " + inner.getId())))
                .collect(Collectors.toSet());

        projeto.setResponsaveis(responsaveis);

        // 🔹 Salvar projeto (JPA cuida da tabela de junção automaticamente)
        ProjetoEntity saveResponse = projetoRepository.save(projeto);

        return ProjetoMapper.toRepresentation(saveResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    public SuccessMessageRepresentation excluirProjeto(String pAcessToken, Long pIdUsuarioLogado
            , Long pIdProjeto) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        // Necessário buscar os itens do projeto para fazer uma limpeza em cascata.
        itemProjetoRepository.deleteAll(itemProjetoRepository.findByProjetoId(pIdProjeto));

        // Necessário excluir os responsáveis associados ao projeto antes de excluir o projeto
        ProjetoEntity projeto = projetoRepository.findById(pIdProjeto)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + pIdProjeto));

        projeto.getResponsaveis().clear();
        projetoRepository.save(projeto);
        projetoRepository.deleteById(pIdProjeto);

        return SuccessMessageRepresentation.builder()
                .message("O projeto foi excluido com sucesso!")
                .code(0)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public ProjetoRepresentation atualizarProjeto(Long pIdProjeto, String pAcessToken, Long pIdUsuarioLogado
            , AtualizarProjetoRequestRepresentation pAtualizarProjetoRequestRepresentation) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        Optional<ProjetoEntity> projetoEntity = projetoRepository.findById(pIdProjeto);
        if (projetoEntity.isEmpty()) {
            throw new BusinessException("Projeto não encontrado");
        }

        ProjetoEntity projeto = projetoEntity.get();

        projeto.setNome(pAtualizarProjetoRequestRepresentation.getNome());
        projeto.setDescricao(pAtualizarProjetoRequestRepresentation.getDescricao());
        projeto.setStatus(StatusProjetoEnum.valueOf(pAtualizarProjetoRequestRepresentation.getStatus().toString()));
        projeto.setInicioPrevisto(pAtualizarProjetoRequestRepresentation.getDtInicioPrevisto());
        projeto.setTerminoPrevisto(pAtualizarProjetoRequestRepresentation.getDtTerminoPrevisto());
        projeto.setInicioRealizado(pAtualizarProjetoRequestRepresentation.getDtInicioRealizado());
        projeto.setTerminoRealizado(pAtualizarProjetoRequestRepresentation.getDtTerminoRealizado());
        projeto.setPercentualTempoRestante(pAtualizarProjetoRequestRepresentation.getPercentualTempoRestante());
        projeto.setDiasAtraso(pAtualizarProjetoRequestRepresentation.getDiasAtraso());

        projeto.getResponsaveis().clear();

        // 🔹 Buscar os responsáveis a partir da lista de objetos que contêm o id
        Set<ResponsavelEntity> responsaveis = new HashSet<>();
        pAtualizarProjetoRequestRepresentation.getResponsavelId().stream().forEach(responsavelId -> {
            responsaveis.add(responsavelRepository.findById(responsavelId).get());
        });
        projeto.setResponsaveis(responsaveis);

        final ProjetoEntity saveResponse = projetoRepository.save(projeto);
        recalcularMetricasEStatus(saveResponse);
        return ProjetoMapper.toRepresentation(saveResponse);
    }

    public ProjetoRepresentation buscarProjetoPorId(Long pIdProjeto, String pAcessToken, Long pIdUsuarioLogado) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        return ProjetoMapper.toRepresentation(
                projetoRepository.findById(pIdProjeto)
                .orElseThrow(() -> new BusinessException("Projeto não encontrado")));
    }

    public ListaProjetoResponseRepresentation listarProjetos(String pAcessToken, Long pIdUsuarioLogado, Pageable pPageable) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        Page<ProjetoEntity> retorno = projetoRepository.findAll(pPageable);
        return ListaProjetoResponseRepresentation.builder()
                .content(retorno.map(ProjetoMapper::toRepresentation).getContent())
                .pageable(ListaProjetoResponsePageableRepresentation.builder()
                        .pageNumber(retorno.getNumber())
                        .pageSize(retorno.getTotalPages())
                        .offset((int) retorno.getPageable().getOffset()).build())
                .totalElements( (int) retorno.getTotalElements())
                .totalPages(retorno.getTotalPages())
                .last(retorno.isLast())
                .first(retorno.isFirst())
                .numberOfElements(retorno.getNumberOfElements())
                .size(retorno.getSize())
                .build();
    }

    public ListaProjetoResponseRepresentation listarProjetosPorStatus(String pAcessToken, Long pIdUsuarioLogado
            , StatusProjetoRepresentation pStatusProjetoRepresentation, Pageable pPageable) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        Page<ProjetoEntity> retorno = projetoRepository.findByStatus(StatusProjetoEnum.valueOf(pStatusProjetoRepresentation.toString()), pPageable);
        return ListaProjetoResponseRepresentation.builder()
                .content(retorno.map(ProjetoMapper::toRepresentation).getContent())
                .pageable(ListaProjetoResponsePageableRepresentation.builder()
                        .pageNumber(retorno.getNumber())
                        .pageSize(retorno.getTotalPages())
                        .offset((int) retorno.getPageable().getOffset()).build())
                .totalElements( (int) retorno.getTotalElements())
                .totalPages(retorno.getTotalPages())
                .last(retorno.isLast())
                .first(retorno.isFirst())
                .numberOfElements(retorno.getNumberOfElements())
                .size(retorno.getSize())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public ProjetoRepresentation moverStatus(String pAcessToken, Long pIdUsuarioLogado
            , Long pIdProjeto, StatusProjetoRepresentation novoStatus) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        ProjetoEntity projeto = projetoRepository.findById(pIdProjeto).get();
        aplicarAcoesAutomaticasDeTransicao(projeto, StatusProjetoEnum.valueOf(novoStatus.toString()));
        recalcularMetricasEStatus(projeto);

        return ProjetoMapper.toRepresentation(projetoRepository.save(projeto));
    }

    public List<QuantidadeProjetosPorStatus200ResponseInnerRepresentation> quantidadeProjetosPorStatus(String pAcessToken, Long pIdUsuarioLogado) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        List<QuantidadeProjetosPorStatus200ResponseInnerRepresentation> retorno = new ArrayList<>();
        projetoRepository.countProjetosByStatus().stream().forEach(projeto -> {
            retorno.add(QuantidadeProjetosPorStatus200ResponseInnerRepresentation.builder()
                    .statusProjeto(StatusProjetoRepresentation.fromValue(projeto.getStatusProjeto().toString()))
                    .quantidade(projeto.getQuantidade())
                    .build());
        });

        return retorno;
    }

    public Double mediaDiasAtrasoPorStatus(String pAcessToken, Long pIdUsuarioLogado
            , StatusProjetoRepresentation pStatusProjetoRepresentation) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        return projetoRepository.avgDiasAtrasoByStatus(StatusProjetoEnum.valueOf(pStatusProjetoRepresentation.toString()));
    }

    private void aplicarAcoesAutomaticasDeTransicao(ProjetoEntity projeto, StatusProjetoEnum novoStatus) {
        StatusProjetoEnum atual = projeto.getStatus();
        LocalDate agora = LocalDate.now();

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
            if (projeto.getInicioPrevisto() != null && projeto.getInicioPrevisto().isAfter(hoje)) {
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

    public StatusProjetoEnum calcularStatus(ProjetoEntity pProjetoEntity) {
        LocalDate hoje = LocalDate.now();

        if (pProjetoEntity.getTerminoRealizado() != null) {
            return StatusProjetoEnum.CONCLUIDO;
        }

        boolean inicioRealizado = pProjetoEntity.getInicioRealizado() != null;
        boolean terminoPrevistoMaiorHoje = pProjetoEntity.getTerminoPrevisto() != null && pProjetoEntity.getTerminoPrevisto().isAfter(hoje);
        boolean terminoRealizadoVazio = pProjetoEntity.getTerminoRealizado() == null;

        if (inicioRealizado && terminoPrevistoMaiorHoje && terminoRealizadoVazio) {
            return StatusProjetoEnum.EM_ANDAMENTO;
        }

        boolean inicioPrevistoMenorHojeESemInicioRealizado = pProjetoEntity.getInicioPrevisto() != null
                && pProjetoEntity.getInicioPrevisto().isBefore(hoje)
                && pProjetoEntity.getInicioRealizado() == null;

        boolean terminoPrevistoMenorHojeESemTerminoRealizado = pProjetoEntity.getTerminoPrevisto() != null
                && pProjetoEntity.getTerminoPrevisto().isBefore(hoje)
                && pProjetoEntity.getTerminoRealizado() == null;

        if (inicioPrevistoMenorHojeESemInicioRealizado || terminoPrevistoMenorHojeESemTerminoRealizado) {
            return StatusProjetoEnum.ATRASADO;
        }

        if (pProjetoEntity.getInicioRealizado() == null && pProjetoEntity.getTerminoRealizado() == null) {
            return StatusProjetoEnum.A_INICIAR;
        }

        return pProjetoEntity.getStatus() != null ? pProjetoEntity.getStatus() : StatusProjetoEnum.A_INICIAR;
    }

    public Float calcularPercentualTempoRestante(ProjetoEntity p) {
        if (p.getInicioPrevisto() == null || p.getTerminoPrevisto() == null) {
            return 0.0f;
        }

        long total = ChronoUnit.DAYS.between(p.getInicioPrevisto(), p.getTerminoPrevisto());
        if (total <= 0) {
            return 0.0f;
        }

        long usado = ChronoUnit.DAYS.between(p.getInicioPrevisto(), LocalDate.now());
        long restante = total - usado;

        if (p.getStatus() == StatusProjetoEnum.CONCLUIDO) {
            return 0.0f;
        }
        if (LocalDate.now().isAfter(p.getTerminoPrevisto())) {
            return 0.0f;
        }

        return  Math.max(0.0f, Float.parseFloat(String.valueOf((restante * 100) / total)));
    }

    public int calcularDiasAtraso(ProjetoEntity p) {
        if (p.getStatus() == StatusProjetoEnum.CONCLUIDO) {
            return 0;
        }
        if (p.getTerminoRealizado() == null && p.getTerminoPrevisto() != null
                && p.getTerminoPrevisto().isBefore(LocalDate.now())) {
            long dias = ChronoUnit.DAYS.between(p.getTerminoPrevisto(), LocalDate.now());
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
