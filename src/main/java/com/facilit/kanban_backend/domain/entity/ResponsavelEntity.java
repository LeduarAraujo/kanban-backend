package com.facilit.kanban_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "responsavel")
@EntityListeners(AuditingEntityListener.class)
public class ResponsavelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "secretaria_id", nullable = false)
    SecretariaEntity secretaria;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    CargoEntity cargo;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "responsaveis")
    private Set<ProjetoEntity> projetos = new HashSet<>();
}
