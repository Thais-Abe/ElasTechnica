package com.soulcode.demo.models;
//import jakarta.persistence.*;


import java.util.Date;

//@Entity
public class Chamado {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Titulo;
    private String Descricao;
    private int Prioridade;
    private Date DataInicio;
    private Setor Setor;
    private Status Status;
    private Pessoa Tecnico;
    private Pessoa Usuario;

}
