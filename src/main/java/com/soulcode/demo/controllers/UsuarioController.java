package com.soulcode.demo.controllers;

import com.soulcode.demo.models.Pessoa;
import com.soulcode.demo.models.Tipo;
import com.soulcode.demo.models.Setor;
import com.soulcode.demo.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    PessoaRepository pessoaRepository;

    @GetMapping("/login-usuario")
    public String paginaLoginUsuario() {
        return "login-usuario";
    }

    @RequestMapping(value = "/login-usuario", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String senha, Model model) {
        Pessoa usuario = pessoaRepository.findByEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            int tipoUsuario = usuario.getTipo().getId();

            if (tipoUsuario == 1) {
                return "redirect:/pagina-cliente";
            } else {
                return "redirect:/pagina-tecnico";
            }

        } else {
            model.addAttribute("error", "E-mail ou senha incorretos");
            return "login-usuario";
        }
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

    @GetMapping("/detalhes-chamado")
    public String teste5(@RequestParam("nome") String nome, Model model, @RequestParam("setor") String setor, @RequestParam("prioridade") String prioridade, @RequestParam("solicitacao") String solicitacao) {

        model.addAttribute("nome", nome);
        model.addAttribute("setor", setor);
//      model.addAttribute("data", LocalDate.now());
        model.addAttribute("prioridade", prioridade);
        model.addAttribute("solicitacao", solicitacao);
        return "detalhes-chamado";
    }

    @GetMapping("/cadastro-usuario")
    public String criarUsuario() {
        return "cadastro-usuario";
    }

    @RequestMapping(value = "/cadastro-usuario", method = RequestMethod.POST)
    public String save(@RequestParam String nome , @RequestParam String email, String senha, int tipoId, int setorId, Model model) {
        Pessoa usuario = new Pessoa();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        Tipo tipo = new Tipo();
        tipo.setId(tipoId);
        usuario.setTipo(tipo);

        Setor setor = new Setor();
        setor.setId(setorId);
        usuario.setSetor(setor);

        pessoaRepository.save(usuario);
        return "redirect:/login-usuario";
    }
}
