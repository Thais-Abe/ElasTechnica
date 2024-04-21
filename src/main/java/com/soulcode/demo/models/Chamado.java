package com.soulcode.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

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

}
