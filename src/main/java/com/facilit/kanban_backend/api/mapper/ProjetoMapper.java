package com.facilit.kanban_backend.api.mapper;

import com.facilit.kanban_backend.api.dto.ProjetoDTO;
import com.facilit.kanban_backend.domain.Projeto;
import com.facilit.kanban_backend.domain.Responsavel;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProjetoMapper {
    public static ProjetoDTO toDTO(Projeto p) {
        Set<UUID> ids = p.getResponsaveis().stream().map(Responsavel::getId).collect(Collectors.toSet());
        return new ProjetoDTO(
                p.getId(), p.getNome(), p.getStatus(),
                p.getInicioPrevisto(), p.getTerminoPrevisto(),
                p.getInicioRealizado(), p.getTerminoRealizado(),
                p.getDiasAtraso(), p.getPercentualTempoRestante(), ids
        );
    }
}
