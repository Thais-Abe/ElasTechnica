package com.soulcode.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

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

//    @GetMapping("/pagina-atendente")
//    public String mostrarPaginaAtendente(@RequestParam("nome") String nomeUsuario, Model model) {
//        model.addAttribute("nome", nomeUsuario);
//
//        // Pedidos em aberto
//        List<Pedido> pedidosEmAberto = Arrays.asList(
//                new Pedido("Pedido 1", "Torta de Morango", TipoComida.DOCE, "10/04/2024"),
//                new Pedido("Pedido 2", "Suco de laranja", TipoComida.BEBIDA, "15/04/2024"),
//                new Pedido("Pedido 3", "Coxinha de frango", TipoComida.BEBIDA, "23/04/2024"),
//                new Pedido("Pedido 4", "Torta de frango", TipoComida.SALGADO, "21/04/2024"),
//                new Pedido("Pedido 5", "Bolo de chocolate", TipoComida.DOCE, "22/04/2024")
//        );
//
//        // Pedidos atribuídos
//        List<Pedido> pedidosAtribuidos = Arrays.asList(
//                new Pedido("Pedido 6", "Hambúrguer", TipoComida.SALGADO, "25/04/2024"),
//                new Pedido("Pedido 7", "Pizza", TipoComida.SALGADO, "26/04/2024")
//        );
//
//        model.addAttribute("pedidosEmAberto", pedidosEmAberto);
//        model.addAttribute("pedidosAtribuidos", pedidosAtribuidos);
//
//        return "pagina-atendente";
//    }


}