package com.soulcode.demo.controllers;

import com.soulcode.demo.models.Chamado;
import com.soulcode.demo.models.Pessoa;
import com.soulcode.demo.models.Setor;
import com.soulcode.demo.models.Status;
import com.soulcode.demo.repositories.ChamadoRepository;
import com.soulcode.demo.repositories.PessoaRepository;
import com.soulcode.demo.services.ChamadoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private final ChamadoRepository chamadoRepository;

    @Autowired
    ChamadoService chamadoService;

    @Autowired
    PessoaRepository pessoaRepository;

    Chamado chamado = new Chamado();
    Setor setor = new Setor();
    Status statusInicial = new Status();

    public UsuarioController(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    @GetMapping("/login-usuario")
    public String paginaLoginUsuario() {
        return "login-usuario";
    }

    @GetMapping("/abertura-chamado")
    public String paginaAberturaChamado() {
        return "abertura-chamado";
    }

    @GetMapping("/cadastro-usuario")
    public String criarUsuario() {
        return "cadastro-usuario";
    }

    @GetMapping("/detalhes-chamado-usuario/{Id}")
    public String detalhesChamadousuario(@PathVariable("Id") int id, Model model, HttpSession session) {
        Chamado chamado = chamadoService.obterChamadoPorId(id);
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
        model.addAttribute("chamado", chamado);
        model.addAttribute("nome", usuarioLogado.getNome());
        return "detalhes-chamado-usuario";
    }

    @RequestMapping(value = "/detalhes-chamado-usuario", method = RequestMethod.POST)
    public String salvarSolicitacao(@RequestParam("prioridade") int prioridade,
                                    @RequestParam("titulo") String titulo,
                                    @RequestParam("descricao") String descricao,
                                    @RequestParam("setor") Setor setor,
                                    HttpSession session) {

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

        chamado = new Chamado();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dataAtual = LocalDateTime.now();
        String dataFormatada = dataAtual.format(formatter);
        LocalDateTime dataConvertida = LocalDateTime.parse(dataFormatada, formatter);

        statusInicial.setId(1);

        chamado.setDescricao(descricao);
        chamado.setTitulo(titulo);
        chamado.setSetor(setor);
        chamado.setPrioridade(prioridade);
        chamado.setDataInicio(dataConvertida);
        chamado.setUsuario(usuarioLogado);
        chamado.setStatus(statusInicial);
        chamadoRepository.save(chamado);

        return "redirect:/pagina-usuario?nome=" + usuarioLogado.getNome();
    }

    @GetMapping("/pagina-usuario")
    public String paginaUsuario( @RequestParam("nome") String nome, Model model) {

        List<Chamado> chamadosDisponiveis = chamadoRepository.findAll();

        model.addAttribute("chamadosDisponiveis", chamadosDisponiveis);
        model.addAttribute("nome", nome);
        return "pagina-usuario";
    }


}
