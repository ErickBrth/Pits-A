package com.ufcg.psoft.pitsa9.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.pitsa9.model.Entregador;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {

    Optional<Entregador> findByCodigoAcesso(String codigoAcesso);
}
