package com.ufcg.psoft.pitsa9.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.pitsa9.model.TipoDisponibilidade;

public class EntregadorDTO {
    
	@JsonIgnore
    private Long id = 0L;

    private String nome;

    private String placaVeiculo;

    private String tipoVeiculo;

    private String corVeiculo;

    private String codigoAcesso;
    
    @JsonIgnore
    public TipoDisponibilidade disponibilidade = TipoDisponibilidade.Indisponível;
    
    @JsonIgnore
    private Boolean aprovado = false;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public String getCorVeiculo() {
        return corVeiculo;
    }

    public String getCodigoAcesso() {
        return codigoAcesso;
    }

    public Boolean getAprovado() {
        return aprovado;
    }
    
    public TipoDisponibilidade getDisponibilidade(){
    	return disponibilidade;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public String toString() {
        return '\n' + "id: " + this.id + '\n' +
                "Nome: " + this.nome + "\n" +
                "Placa Veículo: " + this.placaVeiculo + '\n' +
                "Tipo Veículo: " + this.tipoVeiculo + '\n' +
                "Cor Veículo: " + this.corVeiculo + '\n' +
                "Situação: " + (this.aprovado ? "Aprovado" : "Não Aprovado") + '\n' +
                "Disponibilidade: " + (this.disponibilidade);
    }
}