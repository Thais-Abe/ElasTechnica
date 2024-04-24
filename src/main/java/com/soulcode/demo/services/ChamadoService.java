package com.soulcode.demo.services;

import com.soulcode.demo.models.*;
import com.soulcode.demo.repositories.ChamadoRepository;
import com.soulcode.demo.repositories.PessoaRepository;
import com.soulcode.demo.repositories.StatusRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private StatusRepository statusRepository;

    public Pessoa obterTecnicoLogado(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Pessoa) session.getAttribute("usuarioLogado");
    }

    public Chamado registrarChamado(String titulo, String descricao, int prioridade, LocalDateTime dataInicio,
                                 Setor setor, Status status, Pessoa tecnico, Pessoa usuario) {
        Chamado chamado = new Chamado();
        chamado.setTitulo(titulo);
        chamado.setDescricao(descricao);
        chamado.setPrioridade(prioridade);
        chamado.setDataInicio(dataInicio);
        chamado.setSetor(setor);
        chamado.setStatus(status);
        chamado.setTecnico(tecnico);
        chamado.setUsuario(usuario);

        return chamadoRepository.save(chamado);
    }

    public void registrarChamadosFicticios(HttpServletRequest request) {
        Pessoa tecnicoLogado = obterTecnicoLogado(request);

        Setor setorAdmin = new Setor();
        setorAdmin.setId(1);

        Setor setorTI = new Setor();
        setorTI.setId(6);

        Status statusAguardando = new Status();
        statusAguardando.setId(1);

        Status statusEmAtendimento = new Status();
        statusEmAtendimento.setId(2);

        Tipo tipoCliente = new Tipo();
        tipoCliente.setId(1);

        Pessoa usuario1 = pessoaRepository.findByEmail("mariaflowers@usuario.com");
        if (usuario1 == null) {
            usuario1 = new Pessoa();
            usuario1.setNome("Maria Das Flores");
            usuario1.setEmail("mariaflowers@usuario.com");
            usuario1.setSenha("mariazinhaflor321");
            usuario1.setTipo(tipoCliente);
            pessoaRepository.save(usuario1);

            registrarChamado("Problema no monitor", "O monitor não liga", 1, LocalDateTime.now(), setorAdmin, statusAguardando, tecnicoLogado, usuario1);
            registrarChamado("Problema na impressora", "A impressora não imprime", 2, LocalDateTime.now(), setorTI, statusAguardando, tecnicoLogado, usuario1);
        }

        Pessoa usuario2 = pessoaRepository.findByEmail("carlosrocha@usuario.com");
        if (usuario2 == null) {
            usuario2 = new Pessoa();
            usuario2.setNome("Carlos Rocha");
            usuario2.setEmail("carlosrocha@usuario.com");
            usuario2.setSenha("carlosrocha091413");
            usuario2.setTipo(tipoCliente);
            pessoaRepository.save(usuario2);

            registrarChamado("Problema no teclado", "Algumas teclas não funcionam", 3, LocalDateTime.now(), setorAdmin, statusAguardando, tecnicoLogado, usuario2);
            registrarChamado("Problema no mouse", "O mouse está travando", 1, LocalDateTime.now(), setorTI, statusEmAtendimento, tecnicoLogado, usuario2);
            registrarChamado("Problema na conexão de rede", "Não consigo me conectar à internet", 2, LocalDateTime.now(), setorTI, statusEmAtendimento, tecnicoLogado, usuario2);
        }
    }

    public Chamado obterChamadoPorId(int id) {
        Optional<Chamado> optionalChamado = chamadoRepository.findById(id);
        return optionalChamado.orElse(null);
    }

    public void salvarChamado(Chamado chamado) {
        chamadoRepository.save(chamado);
    }

}
