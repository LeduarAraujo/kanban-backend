package com.facilit.kanban_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;


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
}
