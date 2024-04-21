package com.soulcode.demo.repositories;

import com.soulcode.demo.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
