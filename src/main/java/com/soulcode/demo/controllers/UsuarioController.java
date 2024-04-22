package com.soulcode.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @GetMapping("/login-usuario")
    public String paginaLoginUsuario() {
        return "login-usuario";
    }

    @GetMapping("/pagina-usuario")
    public String paginaUsuario(@RequestParam("nome") String nome, Model model) {
        model.addAttribute("nome", nome);
        return "pagina-usuario";
    }

    @GetMapping("/abertura-chamado")
    public String paginaAberturaChamado() {
        return "abertura-chamado";
    }

    @GetMapping("/cadastro-usuario")
    public String criarUsuario() {
        return "cadastro-usuario";
    }

}
