package com.soulcode.demo.services;

import com.soulcode.demo.models.Pessoa;
import com.soulcode.demo.models.Tipo;
import com.soulcode.demo.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public void cadastrarNovoUsuario(String nome, String email, String senha, int tipoId) {
        Pessoa usuario = new Pessoa();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        Tipo tipo = new Tipo();
        tipo.setId(tipoId);
        usuario.setTipo(tipo);

        pessoaRepository.save(usuario);
    }

    public boolean verificarSeOEmailJaExiste(String email) {
        Pessoa usuarioExistente = pessoaRepository.findByEmail(email);
        return usuarioExistente != null;
    }

    public boolean confirmarSenha(String senha, String confirmSenha) {
        return senha.equals(confirmSenha);
    }


}
