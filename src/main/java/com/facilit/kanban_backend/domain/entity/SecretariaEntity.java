package com.facilit.kanban_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SECRETARIA")
public class SecretariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    UsuarioEntity usuario;
}
