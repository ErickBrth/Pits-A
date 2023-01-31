package com.ufcg.psoft.pitsa9.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sabor1;

    private String sabor2;

    private TamanhoPizza tamanho;

    @JsonIgnore
    private double valor;

    public Item() {

    }

    public Item(String sabor1, String sabor2, TamanhoPizza tamanho, double valor) {
        this.sabor1 = sabor1;
        this.sabor2 = sabor2;
        this.tamanho = TamanhoPizza.Grande;
    }

    public Item(String sabor, TamanhoPizza tamanho) {
        this.sabor1 = sabor;
        this.tamanho = tamanho;
    }

    public String getSabor1() {
        return this.sabor1;
    }

    public void setSabor1(String sabor1) {
        this.sabor1 = sabor1;
    }

    public String getSabor2() {
        return this.sabor2;
    }

    public void setSabor2(String sabor2) {
        this.sabor2 = sabor2;
    }

    public TamanhoPizza getTamanho() {
        return this.tamanho;
    }

    public void setTamanho(TamanhoPizza tamanho) {
        this.tamanho = tamanho;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        if (tamanho == TamanhoPizza.Media) {
            return "\n     TAMANHO: " + tamanho + "\n     SABOR:" + sabor1 + "\n     VALOR: R$ " + valor;
        } else {
            return "\n     TAMANHO: " + tamanho + "\n     SABORES: " + sabor1 + ", " + sabor2 + "\n     VALOR: R$ "
                    + valor;
        }
    }

}
