package com.facilit.kanban_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "secretaria")
public class SecretariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nmSecretaria")
    String nmSecretaria;

    @Column(name = "descricao")
    String descricao;
}
