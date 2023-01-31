package com.ufcg.psoft.pitsa9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.pitsa9.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Long>{
    
}
