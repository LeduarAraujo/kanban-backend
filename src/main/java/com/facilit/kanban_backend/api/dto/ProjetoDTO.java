package com.facilit.kanban_backend.api.dto;

import com.facilit.kanban_backend.domain.ProjetoStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record ProjetoDTO(
        UUID id,
        @NotBlank String nome,
        ProjetoStatus status,
        LocalDate inicioPrevisto,
        LocalDate terminoPrevisto,
        LocalDate inicioRealizado,
        LocalDate terminoRealizado,
        Integer diasAtraso,
        Integer percentualTempoRestante,
        Set<UUID> responsaveisIds
) {}
