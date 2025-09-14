package com.facilit.kanban_backend.domain.entity;

import com.facilit.kanban_backend.domain.enums.PrioridadeEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "item_projeto")
public class ItemProjetoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private ProjetoEntity projeto;

    private String titulo;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "responsavel_id", nullable = false)
    private ResponsavelEntity responsavel;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusItemProjetoEntity status;

    @Enumerated(EnumType.STRING)
    private PrioridadeEnum prioridade;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}


