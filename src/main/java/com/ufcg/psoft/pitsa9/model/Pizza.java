package com.ufcg.psoft.pitsa9.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pizza {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double precoMedia;

    private Double precoGrande;

    private TipoPizza tipo;

    private Boolean disponibilidade;

    @OneToMany
    private Set<Listener> listeners;

    //SuppressWarnings("unused")
    private Pizza() {}

    public Pizza(String nome, Double valorMedio, Double valorGrande, TipoPizza tipo) {
        this.nome = nome;
        this.precoMedia = valorMedio;
        this.precoGrande = valorGrande;
        this.tipo = tipo;
        this.disponibilidade = true;
        this.listeners = new HashSet<Listener>();
    }

    public String getNome() {
        return this.nome;
    };

    public Double getPrecoMedia() {
        return this.precoMedia;
    };

    public Double getPrecoGrande() {
        return this.precoGrande;
    };

    public TipoPizza getTipo() {
        return this.tipo;
    };

    public Long getId() {
        return this.id;
    };

    public Boolean getDisponibilidade() {
        return this.disponibilidade;
    };

    public void setDisponibilidade(Boolean status) {
        this.disponibilidade = status;

        if (status) {
            this.notifica();
        }
    };

    public void setNome(String nome) {
        this.nome = nome;
    };

    public void setPrecoMedia(Double novoPreco) {
        this.precoMedia = novoPreco;
    };

    public void setPrecoGrande(Double novoPreco) {
        this.precoGrande = novoPreco;
    };

    public void setTipo(TipoPizza tipo) {
        this.tipo = tipo;
    };

    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return "Pizza:\nSabor: " + this.getNome() + "\nPreço da média: " + this.getPrecoMedia() 
            + "\nPreço da grande: " + this.getPrecoGrande() + "\nTipo: " + this.getTipo() 
            + "\nDisponibilidade:" + this.getDisponibilidade();            
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    private void notifica() {
        for (Listener cliente : this.listeners) {
            PizzaEvent alerta = new PizzaEvent(this.nome);
            cliente.alertaPizza(alerta);
            this.listeners.remove(cliente);
        }        
    }
}
