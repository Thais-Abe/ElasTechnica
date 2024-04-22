package com.soulcode.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Chamado")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String Titulo;

    private String Descricao;

    private int Prioridade;

    private LocalDateTime DataInicio;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor Setor;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status Status;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Pessoa Tecnico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Pessoa Usuario;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getPrioridade() {
        return Prioridade;
    }

    public void setPrioridade(int prioridade) {
        Prioridade = prioridade;
    }

    public LocalDateTime getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        DataInicio = dataInicio;
    }

    public Setor getSetor() {
        return Setor;
    }

    public void setSetor(Setor setor) {
        Setor = setor;
    }

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status status) {
        Status = status;
    }

    public Pessoa getTecnico() {
        return Tecnico;
    }

    public void setTecnico(Pessoa tecnico) {
        Tecnico = tecnico;
    }

    public Pessoa getUsuario() {
        return Usuario;
    }

    public void setUsuario(Pessoa usuario) {
        Usuario = usuario;
    }

    public String getNomeSolicitante() {
        if (Usuario != null) {
            return Usuario.getNome();
        } else {
            return null;
        }
    }

}
