package com.ufcg.psoft.pitsa9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.pitsa9.model.PedidoProntoState;

public interface PedidoProntoRepository extends JpaRepository<PedidoProntoState, Long>{
    
}
