package com.ufcg.psoft.pitsa9.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.pitsa9.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}