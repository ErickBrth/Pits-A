package com.ufcg.psoft.pitsa9.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "2")
public class PedidoProntoState extends State{
    
    private PedidoProntoState() {}
    
    public PedidoProntoState(Pedido pedido) {
        this.pedido = pedido;
    }

    public void nextStep() {
        PedidoEmRotaState estado = new PedidoEmRotaState(this.pedido);
        this.pedido.setState(estado);
    }

    @Override
    public String toString() {
        return "Pedido pronto";
    }
}
