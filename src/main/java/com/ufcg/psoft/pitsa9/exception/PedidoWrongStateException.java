package com.ufcg.psoft.pitsa9.exception;

public class PedidoWrongStateException extends Exception {

    private static final long serialVersionUID = 1L;

    public PedidoWrongStateException(String message) {
        throw new RuntimeException("Não foi possível realizar a ação, pois o pedido está com o status: " + message + ".");
    }
    
}
