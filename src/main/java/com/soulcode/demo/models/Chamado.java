package com.soulcode.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "chamado")
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
    private Status status;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dataAtual = LocalDateTime.now();
        String dataFormatada = dataAtual.format(formatter);
        LocalDateTime dataConvertida = LocalDateTime.parse(dataFormatada, formatter);
        return dataConvertida;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        DataInicio = dataInicio;
    }

    public com.soulcode.demo.models.Setor getSetor() {
        return Setor;
    }

    public void setSetor(com.soulcode.demo.models.Setor setor) {
        Setor = setor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
