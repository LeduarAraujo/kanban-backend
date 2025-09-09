package com.facilit.kanban_backend.domain.entity;

import com.facilit.kanban_backend.domain.enums.ProjetoStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "PROJETO")
public class ProjetoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    Long id;

    @Column( name = "nome")
    String nome;

    @Column( name = "status")
    ProjetoStatusEnum status = ProjetoStatusEnum.A_INICIAR;

    @Column( name = "inicioPrevisto")
    LocalDate inicioPrevisto;

    @Column( name = "terminoPrevisto")
    LocalDate terminoPrevisto;

    @Column( name = "inicioRealizado")
    LocalDate inicioRealizado;

    @Column( name = "terminoRealizado")
    LocalDate terminoRealizado;

    @Column( name = "diasAtraso")
    Integer diasAtraso = 0;

    @Column( name = "percentualTempoRestante")
    Integer percentualTempoRestante = 0;

    @Column( name = "responsaveis")
    Set<Responsavel> responsaveis = new HashSet<>();
}
