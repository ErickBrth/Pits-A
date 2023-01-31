package com.ufcg.psoft.pitsa9.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.pitsa9.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Optional<Cliente> findByCodigoAcesso(String codigoAcesso);
}
