package com.soulcode.demo.controllers;

import com.soulcode.demo.models.Chamado;
import com.soulcode.demo.models.Pessoa;
import com.soulcode.demo.models.Setor;
import com.soulcode.demo.repositories.ChamadoRepository;
import com.soulcode.demo.repositories.PessoaRepository;
import com.soulcode.demo.services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public UsuarioController(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

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

    @GetMapping("/detalhes-chamado-usuario/{Id}")
    public String detalhesChamadousuario(@PathVariable("Id") int id, Model model) {
        Chamado chamado = chamadoService.obterChamadoPorId(id);

        model.addAttribute("chamado", chamado);


        return "detalhes-chamado-usuario";
    }

    @RequestMapping(value = "/detalhes-chamado-usuario", method = RequestMethod.POST)
    public void salvarSolicitacao( @RequestParam("prioridade") int prioridade, @RequestParam("titulo") String titulo,@RequestParam("descricao") String descricao, @RequestParam("setor") Setor setor){
         chamado = new Chamado();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dataAtual = LocalDateTime.now();
        String dataFormatada = dataAtual.format(formatter);
        LocalDateTime dataConvertida = LocalDateTime.parse(dataFormatada, formatter);

        chamado.setDescricao(descricao);
        chamado.setTitulo(titulo);
        chamado.setSetor(setor);
        chamado.setPrioridade(prioridade);
        chamado.setDataInicio(dataConvertida);
        chamadoRepository.save(chamado);

//        return "redirect:/pagina-usuario?nome=thais";
    }

}
