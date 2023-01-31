package com.ufcg.psoft.pitsa9.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Estabelecimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String codigoAcesso;

    @OneToMany
    private List<Cliente> clientes;

    public Estabelecimento() {}

    public Estabelecimento(String nome, String codigoAcesso) {
        this.nome = nome;
        this.codigoAcesso = codigoAcesso;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public String getCodigoAcesso() {
        return codigoAcesso;
    }

    public void setCodigoAcesso(String codigoAcesso) {
        this.codigoAcesso = codigoAcesso;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // public void setId(Long id) {
    //     this.id = id;
    // }    
}