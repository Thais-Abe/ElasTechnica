package com.soulcode.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.time.LocalDate;

@Controller
public class TecnicoController {

    @GetMapping("/login-tecnico")
    public String paginaLoginTecnico(){
        return "login-tecnico";
    }

    @GetMapping("/pagina-tecnico")
    public String paginaTecnico(){
        return "pagina-tecnico";
    }


}