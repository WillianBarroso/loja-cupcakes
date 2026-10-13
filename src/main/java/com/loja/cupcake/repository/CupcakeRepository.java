package com.loja.cupcake.repository;

import com.loja.cupcake.model.Cupcake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupcakeRepository extends JpaRepository<Cupcake, Long> {
}