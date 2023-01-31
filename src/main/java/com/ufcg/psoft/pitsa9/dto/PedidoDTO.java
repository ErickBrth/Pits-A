package com.ufcg.psoft.pitsa9.dto;

import java.util.List;

import com.ufcg.psoft.pitsa9.model.*;

public class PedidoDTO {

    private Long id;

    private String nomeCliente;

    private String endereco;

    private MetodoPagamento metodoPagamento;

    private State estado;

    private List<Item> pizzas;

    private Double valorTotal;

    public Long getId() {
        return this.id;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public MetodoPagamento getPagamentos() {
        return this.metodoPagamento;
    }

    public State getState() {
        return this.estado;
    }

    public void setState(State estado) {
        this.estado = estado;
    }

    public List<Item> getPizzas() {
        return this.pizzas;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public String toString() {
        return "\n[ ID: " + id + "\n Endereço: " + endereco + "\n Metodo Pagamento: " + metodoPagamento
                + "\n  Itens: "
                + pizzas
                + "\n Nome Cliente: " + nomeCliente + "\n Status: " + getState() + "\n Valor Total: "
                + valorTotal
                + "\n]";

    }
}
