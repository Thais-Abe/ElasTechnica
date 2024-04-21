package com.soulcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private String Nome;

    @Column
    private String Email;

    @Column
    private String Senha;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor Setor;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public com.soulcode.demo.models.Setor getSetor() {
        return Setor;
    }

    public void setSetor(com.soulcode.demo.models.Setor setor) {
        Setor = setor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
