package com.facilit.kanban_backend.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record ResponsavelDTO(
        UUID id,
        @NotBlank String nome,
        @Email @NotBlank String email,
        String cargo,
        String secretaria
) {}
