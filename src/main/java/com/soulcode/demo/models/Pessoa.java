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

}
