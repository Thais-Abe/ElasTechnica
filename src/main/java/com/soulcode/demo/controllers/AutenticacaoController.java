package com.soulcode.demo.controllers;

import com.soulcode.demo.models.Pessoa;
import com.soulcode.demo.repositories.PessoaRepository;
import com.soulcode.demo.services.AutenticacaoService;
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

    @Autowired
    AutenticacaoService autenticacaoService;

    @RequestMapping(value = "/cadastro-usuario", method = RequestMethod.POST)
    public String save(@RequestParam String nome , @RequestParam String email, String senha, int tipoId, Model model) {

        if (autenticacaoService.verificarSeOEmailJaExiste(email)) {
            model.addAttribute("error", "Este e-mail já está em uso. Por favor, escolha outro.");
            return "cadastro-usuario";
        }

        autenticacaoService.cadastrarNovoUsuario(nome, email, senha, tipoId);

        return "redirect:/login-usuario";
    }

    @RequestMapping(value = "/login-usuario", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String senha, Model model, HttpServletRequest request) {
        // procura no banco de dados se a pessoa já existe, através do e-mail
        Pessoa usuario = pessoaRepository.findByEmail(email);

        // verifica se o usuário existe e se a senha está correta
        if (usuario != null && usuario.getSenha().equals(senha)) {
            int tipoUsuario = usuario.getTipo().getId();

            // cria uma sessão e define o usuário como logado, armazenando as informações do usuário logado em cache
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);

            // redireciona para a página apropriada com base no tipo de usuário (técnico ou cliente)
            if (tipoUsuario == 1) {
                return "redirect:/pagina-usuario?nome=" + usuario.getNome();
            } else {
                return "redirect:/pagina-tecnico?nome=" + usuario.getNome();
            }
        } else if (usuario != null && usuario.getEmail().equals(email)) {
            model.addAttribute("error", "Senha incorreta");
        } else {
            model.addAttribute("error", "E-mail e senha incorretos");
        }

        return "login-usuario";
    }


}
