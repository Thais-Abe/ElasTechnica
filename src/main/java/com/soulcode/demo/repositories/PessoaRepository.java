package com.soulcode.demo.repositories;

import com.soulcode.demo.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

        @Query("SELECT p FROM Pessoa p WHERE p.Email = :email")
        Pessoa findByEmail(String email);

        @Query("SELECT p FROM Pessoa p WHERE p.Nome = :nome")
        Pessoa findByNome(String nome);
}
