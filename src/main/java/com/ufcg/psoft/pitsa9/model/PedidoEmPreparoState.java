package com.ufcg.psoft.pitsa9.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufcg.psoft.pitsa9.repository.PedidoRecebidoRepository;

@Entity
@DiscriminatorValue(value = "1")
public class PedidoEmPreparoState extends State {
    
    private PedidoEmPreparoState() {}

    public PedidoEmPreparoState(Pedido pedido) {
        this.pedido = pedido;
    }

    public void nextStep() {
        PedidoProntoState estado = new PedidoProntoState(this.pedido);
        this.pedido.setState(estado);
    }  

    public void setPedidoNull() {
        this.pedido = null;
    }

    @Override
    public String toString() {
        return "Pedido em preparo";
    }
}
