package com.soulcode.demo.repositories;

import com.soulcode.demo.models.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoRepository extends JpaRepository<Tipo, Integer> {
}
