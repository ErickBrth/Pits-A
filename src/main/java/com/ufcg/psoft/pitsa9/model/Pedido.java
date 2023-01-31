package com.ufcg.psoft.pitsa9.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Double valorTotal;

    private String endereco;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Item> pizzas;

    private MetodoPagamento metodoPagamento;

    @OneToOne(cascade = CascadeType.PERSIST)
    private State estado;

    @OneToMany
    private Set<Listener> listeners;

    public Pedido() {

    }

    public Pedido(String endereco, Cliente cliente, List<Item> pizzas) {
        this.endereco = endereco;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = setValorTotal(this.pizzas);
    }

    public void nextStep() {
        this.estado.nextStep();
        if (estado.toString().equals("Pedido em rota")) {
            this.notifica();
        }
    }

    public Double setValorTotal(List<Item> pizzas) {
        Double valorTotal = 0.0;

        for (Item item : pizzas) {
            valorTotal += item.getValor();
        }
        return valorTotal;
    }

    public void setValor(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return this.id;
    }

    public State getState() {
        return this.estado;
    }

    public void setState(State estado) {
        this.estado = estado;
    }

    public Double getValorTotal() {
        return this.valorTotal;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Item> getPizzas() {
        return this.pizzas;
    }

    public void setPizzas(List<Item> pizzas) {
        this.pizzas = pizzas;
    }

    public MetodoPagamento getMetodoPagamento() {
        return this.metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    private void notifica() {
        for (Listener cliente : this.listeners) {
            PedidoEmRotaEvent alerta = new PedidoEmRotaEvent(estado.toString());
            cliente.alertaPedidoSaiuParaEntrega(alerta, getId());
            this.listeners.remove(cliente);
        }
    }

    public String toString() {
        return "PEDIDO: \nCLIENTE: " + this.cliente.toString() + "\nENDEREÇO: " + endereco + "\nITENS: "
                + pizzas.toString() + "\nVALOR: R$ " + this.getValorTotal() + "\nMÉTODO DE PAGAMENTO: "
                + this.metodoPagamento.toString() + "\n" + this.estado.toString() + "\n";
    }
}
