package com.facilit.kanban_backend.api.mapper;

import com.facilit.kanban_backend.api.dto.ResponsavelDTO;
import com.facilit.kanban_backend.domain.Responsavel;

public class ResponsavelMapper {
    public static ResponsavelDTO toDTO(Responsavel r) {
        return new ResponsavelDTO(r.getId(), r.getNome(), r.getEmail(), r.getCargo(), r.getSecretaria());
    }
}
