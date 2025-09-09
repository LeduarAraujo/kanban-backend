package com.facilit.kanban_backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "responsavel", uniqueConstraints = {
        @UniqueConstraint(name = "uk_responsavel_email", columnNames = "email")
})
public class Responsavel {
    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    private String cargo;

    private String secretaria;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getSecretaria() { return secretaria; }
    public void setSecretaria(String secretaria) { this.secretaria = secretaria; }
}
