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

    Chamado chamado;
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

        model.addAttribute("chamado", chamado);

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

        return "redirect:/pagina-usuario?nome=" + usuarioLogado.getNome();
    }

    @RequestMapping(value = "/detalhes-chamado-usuario", method = RequestMethod.POST)
    public String salvarSolicitacao(@RequestParam("titulo") String titulo,
                                    @RequestParam("setor") Setor setor,
                                    @RequestParam("prioridade") int prioridade,
                                    @RequestParam("descricao") String descricao,
                                    HttpSession session) {

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

        chamado = new Chamado();
        LocalDateTime dataDoChamado = chamadoService.retornarDataAberturaChamado();

        statusInicial.setId(1);

        chamado.setDescricao(descricao);
        chamado.setTitulo(titulo);
        chamado.setSetor(setor);
        chamado.setPrioridade(prioridade);
        chamado.setDataInicio(dataDoChamado);
        chamado.setUsuario(usuarioLogado);
        chamado.setStatus(statusInicial);
        chamadoRepository.save(chamado);

        return "redirect:/pagina-usuario?nome=" + usuarioLogado.getNome();
    }

    @GetMapping("/pagina-usuario")
    public String paginaUsuario(Model model, HttpServletRequest request, @RequestParam(required = false) String status, @RequestParam("nome") String nome) {

        List<Chamado> todosChamados = chamadoRepository.findAll();
        List<Chamado> chamadosEncerrados = new ArrayList<>();
        List<Chamado> chamadosEmAndamento = new ArrayList<>();

        for (Chamado chamado : todosChamados) {
            if (chamado.getStatus().getId() == 1) {
                chamadosEmAndamento.add(chamado);
            } else if (chamado.getStatus().getId() == 2 || chamado.getStatus().getId() == 3 || chamado.getStatus().getId() == 4) {
                chamadosEncerrados.add(chamado);
            }

        }
        model.addAttribute("chamadosEmAndamento", chamadosEmAndamento);
        model.addAttribute("chamadosEncerrados", chamadosEncerrados);
        model.addAttribute("nome", nome);
        return "redirect:/pagina-usuario?nome=" + nome;
    }

}
