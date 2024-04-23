package com.soulcode.demo.controllers;

import com.soulcode.demo.models.Chamado;
import com.soulcode.demo.models.Status;
import com.soulcode.demo.repositories.ChamadoRepository;
import com.soulcode.demo.repositories.StatusRepository;
import com.soulcode.demo.services.ChamadoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String paginaTecnico(Model model, HttpServletRequest request, @RequestParam(required = false) String status, @RequestParam(required = false) String nome) {
        if ("atualizado".equals(status)) {
            return "redirect:/pagina-tecnico";
        }

        model.addAttribute("nome", nome);

        if (!chamadosForamRegistrados) {
            chamadoService.registrarChamadosFicticios(request);
            chamadosForamRegistrados = true;
        }

        List<Chamado> chamadosDisponiveis = new ArrayList<>();
        List<Chamado> chamadosEmAtendimento = new ArrayList<>();
        List<Chamado> todosChamados = getChamadosFicticiosDoBancoDeDados();

        for (Chamado chamado : todosChamados) {
            if (chamado.getStatus().getId() == 1) {
                chamadosDisponiveis.add(chamado);
            } else if (chamado.getStatus().getId() == 2 || chamado.getStatus().getId() == 4) {
                chamadosEmAtendimento.add(chamado);
            }
        }

        model.addAttribute("chamadosDisponiveis", chamadosDisponiveis);
        model.addAttribute("chamadosEmAtendimento", chamadosEmAtendimento);

        return "pagina-tecnico";
    }

    @GetMapping("/detalhes-chamado/{id}")
    public String detalhesChamado(@PathVariable("id") int id, Model model) {
        Chamado chamado = chamadoService.obterChamadoPorId(id);

        model.addAttribute("chamado", chamado);
        model.addAttribute("setor", chamado.getSetor().toString());

        return "detalhes-chamado";
    }

    @PostMapping("/mudar-status")
    public String mudarStatusChamado(@RequestParam int id, @RequestParam int status) {
        Chamado chamado = chamadoService.obterChamadoPorId(id);

        if (status == 1) {
            Status novoStatus = statusRepository.findById(2).orElse(null);
            if (novoStatus != null) {
                chamado.setStatus(novoStatus);
                chamadoService.salvarChamado(chamado);

                return "redirect:/pagina-tecnico?status=atualizado";
            }
        } else if (status == 2) {
            Status novoStatus = statusRepository.findById(4).orElse(null);
            if (novoStatus != null) {
                chamado.setStatus(novoStatus);
                chamadoService.salvarChamado(chamado);

                return "redirect:/pagina-tecnico?status=atualizado";
            }
        }

        return "redirect:/pagina-tecnico";
    }

    public List<Chamado> getChamadosFicticiosDoBancoDeDados() {
        return chamadoRepository.findAll();
    }

}