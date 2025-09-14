package com.facilit.kanban_backend.mapper;

import com.baeldung.openapi.model.ProjetoRepresentation;
import com.baeldung.openapi.model.StatusProjetoRepresentation;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;

public class ProjetoMapper {

    public static ProjetoRepresentation toRepresentation(ProjetoEntity pProjetoEntity) {
        if (pProjetoEntity == null) {
            return null;
        }

        ProjetoRepresentation representation = new ProjetoRepresentation();

        representation.setId(pProjetoEntity.getId());
        representation.setNome(pProjetoEntity.getNome());
        representation.setStatus(StatusProjetoRepresentation.valueOf(pProjetoEntity.getStatus().toString()));
        representation.setDtInicioPrevisto(pProjetoEntity.getInicioPrevisto());
        representation.setDtTerminoPrevisto(pProjetoEntity.getTerminoPrevisto());
        representation.setDtInicioRealizado(pProjetoEntity.getInicioRealizado());
        representation.setDtTerminoRealizado(pProjetoEntity.getTerminoRealizado());
        representation.setDiasAtraso(pProjetoEntity.getDiasAtraso());
        representation.setPercentualTempoRestante(pProjetoEntity.getPercentualTempoRestante());

        return representation;
    }

}
