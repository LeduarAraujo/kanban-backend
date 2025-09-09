package com.facilit.kanban_backend.api.dto;

import com.facilit.kanban_backend.domain.ProjetoStatus;

public record TransicaoRequest(ProjetoStatus para) {}
