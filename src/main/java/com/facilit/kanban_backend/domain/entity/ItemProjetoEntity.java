package com.facilit.kanban_backend.domain.entity;

import com.facilit.kanban_backend.domain.enums.PrioridadeEnum;
import com.facilit.kanban_backend.domain.enums.StatusItemProjetoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "item_projeto_responsavel", // tabela de junção
            joinColumns = @JoinColumn(name = "item_projeto_id"), // chave estrangeira desta entidade
            inverseJoinColumns = @JoinColumn(name = "responsavel_id") // chave estrangeira da outra entidade
    )
    private Set<ResponsavelEntity> responsaveis = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private StatusItemProjetoEnum status;

    @Enumerated(EnumType.STRING)
    private PrioridadeEnum prioridade;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}


