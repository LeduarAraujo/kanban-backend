package com.facilit.kanban_backend.domain.entity;

import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "projeto")
@EntityListeners(AuditingEntityListener.class)
public class ProjetoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @ManyToMany
    @JoinTable(
            name = "projeto_responsavel", // tabela de junção
            joinColumns = @JoinColumn(name = "projeto_id"), // chave estrangeira desta entidade
            inverseJoinColumns = @JoinColumn(name = "responsavel_id") // chave estrangeira da outra entidade
    )
    private Set<ResponsavelEntity> responsaveis = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private StatusProjetoEnum status;

    @Column(name = "inicio_previsto")
    private LocalDate inicioPrevisto;

    @Column(name = "termino_previsto")
    private LocalDate terminoPrevisto;

    @Column(name = "inicio_realizado")
    private LocalDate inicioRealizado;

    @Column(name = "termino_realizado")
    private LocalDate terminoRealizado;

    @Column(name = "dias_atraso")
    private Integer diasAtraso;

    @Column(name = "percentual_tempo_restante")
    private Float percentualTempoRestante;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
