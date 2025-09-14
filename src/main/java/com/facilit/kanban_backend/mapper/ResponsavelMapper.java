package com.facilit.kanban_backend.mapper;

import com.baeldung.openapi.model.ResponsavelRepresentation;
import com.facilit.kanban_backend.domain.entity.ResponsavelEntity;
import jakarta.persistence.*;

public class ResponsavelMapper {

    public static ResponsavelRepresentation  toRepresentation(ResponsavelEntity pResponsavelEntity) {
        if (pResponsavelEntity == null) {
            return null;
        }

        ResponsavelRepresentation responsavelRepresentation = new ResponsavelRepresentation();
        responsavelRepresentation.setId(pResponsavelEntity.getId());
//        responsavelRepresentation.setUsuarioId(pResponsavelEntity.getUsuario().getId());
        responsavelRepresentation.setSecretariaId(pResponsavelEntity.getSecretaria().getId());
        responsavelRepresentation.setCargoId(pResponsavelEntity.getCargo().getId());

        return responsavelRepresentation;
    }
}
