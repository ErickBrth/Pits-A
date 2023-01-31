package com.ufcg.psoft.pitsa9.dto;

import com.ufcg.psoft.pitsa9.model.TipoPizza;

public class PizzaDTO {
    
    private Long id;

    private String nome;

    private Double precoMedia;

    private Double precoGrande;

    private TipoPizza tipo;

    private Boolean disponibilidade;

    public Long getId() {
        return this.id;
    };

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

    public Boolean getDisponibilidade() {
        return this.disponibilidade;
    };
}
