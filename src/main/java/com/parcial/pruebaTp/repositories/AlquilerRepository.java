package com.parcial.pruebaTp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcial.pruebaTp.models.Alquiler;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
}