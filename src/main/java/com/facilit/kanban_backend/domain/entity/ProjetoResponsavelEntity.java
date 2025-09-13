package com.facilit.kanban_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PROJETO_RESPONSAVEL")
public class ProjetoResponsavelEntity {
    @EmbeddedId
    private ProjetoResponsavelIdEmbed id;

    @ManyToOne
    @MapsId("projetoId")
    @JoinColumn(name = "projeto_id")
    private ProjetoEntity projeto;

    @ManyToOne
    @MapsId("responsavelId")
    @JoinColumn(name = "responsavel_id")
    private ResponsavelEntity responsavel;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
