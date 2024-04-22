package com.soulcode.demo.controllers;

import com.soulcode.demo.models.Pessoa;
import com.soulcode.demo.models.Setor;
import com.soulcode.demo.models.Tipo;
import com.soulcode.demo.repositories.PessoaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AutenticacaoController {

    @Autowired
    PessoaRepository pessoaRepository;

    @RequestMapping(value = "/cadastro-usuario", method = RequestMethod.POST)
    public String save(@RequestParam String nome , @RequestParam String email, String senha, int tipoId, int setorId, Model model) {

        Pessoa userExistente = pessoaRepository.findByEmail(email);

        if (userExistente != null) {
            model.addAttribute("error", "Este e-mail já está em uso. Por favor, escolha outro.");
            return "cadastro-usuario";
        }

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

    @RequestMapping(value = "/login-usuario", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String senha, Model model, HttpServletRequest request) {
        Pessoa usuario = pessoaRepository.findByEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            int tipoUsuario = usuario.getTipo().getId();

            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);

            if (tipoUsuario == 1) {
                return "redirect:/pagina-usuario?nome=" + usuario.getNome();
            } else {
                return "redirect:/pagina-tecnico?nome=" + usuario.getNome();
            }

        } else {
            model.addAttribute("error", "E-mail ou senha incorretos");
            return "login-usuario";
        }
    }


}
