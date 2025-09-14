package com.facilit.kanban_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "projeto_responsavel")
public class ProjetoResponsavelEntity {

    @ManyToOne
    @MapsId("projetoId")
    @JoinColumn(name = "projeto_id")
    private ProjetoEntity projeto;

    @ManyToOne
    @MapsId("responsavelId")
    @JoinColumn(name = "responsavel_id")
    private ResponsavelEntity responsavel;
}
