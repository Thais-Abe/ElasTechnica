package com.soulcode.demo.controllers;

import com.soulcode.demo.models.Chamado;
import com.soulcode.demo.models.Pessoa;
import com.soulcode.demo.models.Status;
import com.soulcode.demo.repositories.ChamadoRepository;
import com.soulcode.demo.repositories.StatusRepository;
import com.soulcode.demo.services.ChamadoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TecnicoController {

    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired
    ChamadoService chamadoService;

    @Autowired
    StatusRepository statusRepository;

    private static boolean chamadosForamRegistrados = false;

    @GetMapping("/pagina-tecnico")
    public String paginaTecnico(@RequestParam(required = false) String nome, Model model, HttpServletRequest request) {
        if (!chamadosForamRegistrados) {
            chamadoService.registrarChamadosFicticios(request);
            chamadosForamRegistrados = true;
        }

        List<Chamado> chamadosDisponiveis = new ArrayList<>();
        List<Chamado> chamadosEmAtendimento = new ArrayList<>();
        List<Chamado> todosChamados = getChamadosFicticiosDoBancoDeDados();

        for (Chamado chamado : todosChamados) {
            int statusId = chamado.getStatus().getId();
            (statusId == 1 ? chamadosDisponiveis : chamadosEmAtendimento).add(chamado);
        }

        List<Chamado> chamadosDoBancoComStatusInicial = chamadoService.getChamadosComStatus(1);

        chamadosDoBancoComStatusInicial = chamadosDoBancoComStatusInicial.stream()
                .filter(chamado -> !chamadosDisponiveis.contains(chamado)).toList();

        chamadosDisponiveis.addAll(chamadosDoBancoComStatusInicial);

        model.addAttribute("chamadosDisponiveis", chamadosDisponiveis);
        model.addAttribute("chamadosEmAtendimento", chamadosEmAtendimento);
        model.addAttribute("nome", nome);

        return "pagina-tecnico";
    }

    @GetMapping("/detalhes-chamado/{id}")
    public String detalhesChamado(@PathVariable("id") int id, Model model) {
        Chamado chamado = chamadoService.obterChamadoPorId(id);

        model.addAttribute("chamado", chamado);
        model.addAttribute("setor", chamado.getSetor().toString());

        return "detalhes-chamado";
    }

    @RequestMapping(value = "/mudar-status", method = RequestMethod.POST)
    public String mudarStatusChamado(@RequestParam int id, @RequestParam int status, HttpSession session) {

        Chamado chamado = chamadoService.obterChamadoPorId(id);
        Pessoa tecnicoLogado = (Pessoa) session.getAttribute("usuarioLogado");
        session.setAttribute("tecnicoLogadoNome", tecnicoLogado.getNome());

        Status statusAtualizado = null;

        switch (status) {
            case 1 -> statusAtualizado = statusRepository.findById(2).orElse(null); // na base de dados o id 2 = Em atendimento
            case 2 -> statusAtualizado = statusRepository.findById(3).orElse(null); // na base de dados o id 3 = Escalado para outro setor
            case 3 -> statusAtualizado = statusRepository.findById(4).orElse(null); // na base de dados o id 4 = Finalizado
            default -> { return "redirect:/pagina-tecnico?nome=" + tecnicoLogado.getNome();}
        }

        if (statusAtualizado != null) {
            chamado.setStatus(statusAtualizado);
            chamado.setTecnico(tecnicoLogado);
            chamadoService.salvarChamado(chamado);

            return "redirect:/pagina-tecnico?nome=" + tecnicoLogado.getNome();
        } else {
            return "redirect:/pagina-tecnico";
        }
    }

    public List<Chamado> getChamadosFicticiosDoBancoDeDados() {
        return chamadoRepository.findAll();
    }

}