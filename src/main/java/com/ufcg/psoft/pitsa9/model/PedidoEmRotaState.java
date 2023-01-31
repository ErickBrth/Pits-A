package com.ufcg.psoft.pitsa9.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "3")
public class PedidoEmRotaState extends State{
    
    private PedidoEmRotaState() {}
    
    public PedidoEmRotaState(Pedido pedido) {
        this.pedido = pedido;
    }

    public void nextStep() {
        PedidoEntregueState estado = new PedidoEntregueState(this.pedido);        
        this.pedido.setState(estado);
    }

    @Override
    public String toString() {
        return "Pedido em rota";
    }
}
