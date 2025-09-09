//package com.facilit.kanban_backend.domain.entity;
//
//import com.facilit.kanban_backend.domain.enums.ProjetoStatusEnum;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import java.time.LocalDate;
//import java.util.*;
//
//@Entity
//@Table(name = "projeto")
//public class Projeto {
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @NotBlank
//    private String nome;
//
//    @Enumerated(EnumType.STRING)
//    private ProjetoStatusEnum status = ProjetoStatusEnum.A_INICIAR;
//
//    private LocalDate inicioPrevisto;
//    private LocalDate terminoPrevisto;
//    private LocalDate inicioRealizado;
//    private LocalDate terminoRealizado;
//
//    private Integer diasAtraso = 0;
//    private Integer percentualTempoRestante = 0;
//
//    @ManyToMany
//    @JoinTable(name = "projeto_responsavel", joinColumns = @JoinColumn(name = "projeto_id"), inverseJoinColumns = @JoinColumn(name = "responsavel_id"))
//    private Set<Responsavel> responsaveis = new HashSet<>();
//
//    @Column(updatable = false)
//    private LocalDate createdAt = LocalDate.now();
//    private LocalDate updatedAt = LocalDate.now();
//
//    @PreUpdate
//    public void preUpdate() {
//        this.updatedAt = LocalDate.now();
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public ProjetoStatusEnum getStatus() {
//        return status;
//    }
//
//    public void setStatus(ProjetoStatusEnum status) {
//        this.status = status;
//    }
//
//    public LocalDate getInicioPrevisto() {
//        return inicioPrevisto;
//    }
//
//    public void setInicioPrevisto(LocalDate inicioPrevisto) {
//        this.inicioPrevisto = inicioPrevisto;
//    }
//
//    public LocalDate getTerminoPrevisto() {
//        return terminoPrevisto;
//    }
//
//    public void setTerminoPrevisto(LocalDate terminoPrevisto) {
//        this.terminoPrevisto = terminoPrevisto;
//    }
//
//    public LocalDate getInicioRealizado() {
//        return inicioRealizado;
//    }
//
//    public void setInicioRealizado(LocalDate inicioRealizado) {
//        this.inicioRealizado = inicioRealizado;
//    }
//
//    public LocalDate getTerminoRealizado() {
//        return terminoRealizado;
//    }
//
//    public void setTerminoRealizado(LocalDate terminoRealizado) {
//        this.terminoRealizado = terminoRealizado;
//    }
//
//    public Integer getDiasAtraso() {
//        return diasAtraso;
//    }
//
//    public void setDiasAtraso(Integer diasAtraso) {
//        this.diasAtraso = diasAtraso;
//    }
//
//    public Integer getPercentualTempoRestante() {
//        return percentualTempoRestante;
//    }
//
//    public void setPercentualTempoRestante(Integer percentualTempoRestante) {
//        this.percentualTempoRestante = percentualTempoRestante;
//    }
//
//    public Set<Responsavel> getResponsaveis() {
//        return responsaveis;
//    }
//
//    public void setResponsaveis(Set<Responsavel> responsaveis) {
//        this.responsaveis = responsaveis;
//    }
//
//    public LocalDate getCreatedAt() {
//        return createdAt;
//    }
//
//    public LocalDate getUpdatedAt() {
//        return updatedAt;
//    }
//}
