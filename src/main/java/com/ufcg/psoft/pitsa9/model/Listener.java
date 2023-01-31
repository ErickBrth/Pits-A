package com.ufcg.psoft.pitsa9.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Listener {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public void alertaPizza(PizzaEvent alerta) {
    }

    public void alertaPedidoSaiuParaEntrega(PedidoEmRotaEvent alerta, Long idPedido) {
    }
}
