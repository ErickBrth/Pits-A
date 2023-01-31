package com.ufcg.psoft.pitsa9.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Entregador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String placaVeiculo;

    private String tipoVeiculo;

    private String corVeiculo;

    private String codigoAcesso;

    private boolean isAprovado;
 
    private TipoDisponibilidade disponibilidade;

    public Entregador() {
    }

    public Entregador(String nome, String placaVeiculo, String tipoVeiculo, String corVeiculo, String codigoAcesso, TipoDisponibilidade disponibilidade) {
        this.nome = nome;
        this.placaVeiculo = placaVeiculo;
        this.tipoVeiculo = tipoVeiculo;
        this.corVeiculo = corVeiculo;
        this.codigoAcesso = codigoAcesso;
        this.disponibilidade = disponibilidade;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public String getCorVeiculo() {
        return corVeiculo;
    }

    public void setCorVeiculo(String corVeiculo) {
        this.corVeiculo = corVeiculo;
    }

    public String getCodigoAcesso() {
        return codigoAcesso;
    }

    public void setCodigoAcesso(String codigoAceso) {
        this.codigoAcesso = codigoAceso;
    }

    public boolean getIsAprovado() {
        return isAprovado;
    }

    public void setIsAprovado(boolean isAprovado) {
        this.isAprovado = isAprovado;
    }

    // @Override
    // public String toString() {
    //     return "Entregador: " + this.nome + "\n" + this.tipoVeiculo + " - " + this.placaVeiculo + " - "
    //             + this.corVeiculo;
    // }
    
    public void setDisponibilidade(TipoDisponibilidade disponibilidade) {
    	this.disponibilidade = disponibilidade;
    }
    
    public TipoDisponibilidade getDisponibilidade() {
    	return disponibilidade;
    }

    public String toString() {
        return '\n' + "Nome: " + this.nome + "\n" +
                "Placa Veículo: " + this.placaVeiculo + '\n' +
                "Tipo Veículo: " + this.tipoVeiculo + '\n' +
                "Cor Veículo: " + this.corVeiculo + '\n' +
                "Situação: " + (this.isAprovado ? " Aprovado" : " Não Aprovado") + '\n' +
                "Disponibilidade: " + this.disponibilidade;
    }
}
