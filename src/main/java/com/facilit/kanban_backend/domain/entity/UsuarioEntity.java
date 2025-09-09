package com.facilit.kanban_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    Long id;

    @Column( name = "nome")
    String nome;

    @Column( name = "email")
    String email;

    @Column( name = "password")
    String password;
}
