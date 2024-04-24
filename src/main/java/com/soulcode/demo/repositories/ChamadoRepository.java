package com.soulcode.demo.repositories;

import com.soulcode.demo.models.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
    List<Chamado> findByStatusId(int statusId);
}