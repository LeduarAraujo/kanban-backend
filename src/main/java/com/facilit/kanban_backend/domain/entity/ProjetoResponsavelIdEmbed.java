package com.facilit.kanban_backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ProjetoResponsavelIdEmbed implements Serializable {

    @Column(name = "projeto_id")
    private Long projetoId;

    @Column(name = "responsavel_id")
    private Long responsavelId;
}
