package com.ufcg.psoft.pitsa9.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "4")
public class PedidoEntregueState extends State{
    
    private PedidoEntregueState() {}
    
    public PedidoEntregueState(Pedido pedido) {
        this.pedido = pedido;
    }

    public void nextStep() {}

    @Override
    public String toString() {
        return "Pedido entregue";
    }
}
