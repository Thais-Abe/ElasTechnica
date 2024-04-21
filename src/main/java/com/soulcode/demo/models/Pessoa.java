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
    private Setor Setor;
    private Tipo Tipo;

}
