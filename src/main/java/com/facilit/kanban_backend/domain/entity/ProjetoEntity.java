package com.facilit.kanban_backend.domain.entity;

import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PROJETO")
@EntityListeners(AuditingEntityListener.class)
public class ProjetoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private StatusProjetoEnum status;

    @Column(name = "inicio_previsto")
    private LocalDateTime inicioPrevisto;

    @Column(name = "termino_previsto")
    private LocalDateTime terminoPrevisto;

    @Column(name = "inicio_realizado")
    private LocalDateTime inicioRealizado;

    @Column(name = "termino_realizado")
    private LocalDateTime terminoRealizado;

    @Column(name = "dias_atraso")
    private Integer diasAtraso;

    @Column(name = "percentual_tempo_restante")
    private Integer percentualTempoRestante;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
