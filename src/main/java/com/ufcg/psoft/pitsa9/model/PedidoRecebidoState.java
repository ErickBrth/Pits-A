package com.ufcg.psoft.pitsa9.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "5")
public class PedidoRecebidoState extends State {
    
    private PedidoRecebidoState() {}
    
    public PedidoRecebidoState(Pedido pedido) {
        this.pedido = pedido;
    }

    public void nextStep() {
        PedidoEmPreparoState estado = new PedidoEmPreparoState(this.pedido);
        this.pedido.setState(estado);
    }

    public void setPedidoNull() {
        this.pedido = null;
    }

    @Override
    public String toString() {
        return "Pedido recebido";
    }   
    
}
