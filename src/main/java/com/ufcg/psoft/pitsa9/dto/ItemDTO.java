package com.ufcg.psoft.pitsa9.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.pitsa9.model.TamanhoPizza;

public class ItemDTO {
    
    @JsonIgnore
    private Long id;

    private String sabor1;

    private String sabor2;
    
    private TamanhoPizza tamanho;

    @JsonIgnore
    private double valor;

    public String getSabor1() {
        return this.sabor1;
    }

    public String getSabor2() {
        return this.sabor2;
    }

    public TamanhoPizza getTamanho() {
        return this.tamanho;
    }

    public double getValor() {
        return this.valor;
    }
}
