package com.facilit.kanban_backend.dto;

import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;

public interface RespostaContagemProjetosPorStatusDTO {

    StatusProjetoEnum getStatusProjeto();
    Long getQuantidade();
}