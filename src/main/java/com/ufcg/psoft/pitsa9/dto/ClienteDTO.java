package com.ufcg.psoft.pitsa9.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClienteDTO {

	@JsonIgnore
	private Long id = -1L;

	private String nome;

	private String endereco;
	
	private String codigoAcesso;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getCodigoAcesso() {
		return codigoAcesso;
	}

	@Override
	public String toString() {
		return "\nid: " + id + ";" + "\n" +
				"nome: " + nome + ";" + "\n" +
				"endereco: " + endereco;
	}

}
