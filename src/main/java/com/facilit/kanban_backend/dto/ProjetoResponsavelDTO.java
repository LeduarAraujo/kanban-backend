package com.facilit.kanban_backend.dto;

import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;

public interface ProjetoResponsavelDTO {
    String getNomeProjeto();
    String getNomeResponsavel();
    String getNomeSecretaria();
    String getNomeCargo();
    StatusProjetoEnum getStatusProjeto();
}
