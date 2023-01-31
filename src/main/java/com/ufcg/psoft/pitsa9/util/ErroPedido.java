package com.ufcg.psoft.pitsa9.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPedido {

    static final String PEDIDO_NAO_ENCONTRADO = "Pedido id: %s não encontrado.";
    static final String PEDIDO_JA_CADASTRADO = "Pedido %s ja cadastrado.";
    static final String PEDIDO_PAGO = "Pedido pago anteriormente.";
    static final String CODIGO_INVALIDO = "Código de Acesso: %s inválido. Deve Conter somente 6 dígitos";
    static final String SABOR_NOT_CADASTERED = "Sabor não cadastrado.";
    static final String ERRO_PEDIDO_PRONTO = "O pedido ainda não passou pelo estado anterior(Não foi pago pelo cliente).";

    public static ResponseEntity<?> erroPedidoJaCadastrado() {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroPedido.PEDIDO_JA_CADASTRADO)),
                HttpStatus.CONFLICT);
    }

    public static ResponseEntity<?> erroPedidoPago() {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroPedido.PEDIDO_PAGO)),
                HttpStatus.CONFLICT);
    }

    public static ResponseEntity<CustomErrorType> erroPedidoNaoEncontrado(Long idPedido) {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroPedido.PEDIDO_NAO_ENCONTRADO, idPedido)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroInvalidAcessToken(String codigoAcesso) {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroPedido.CODIGO_INVALIDO, codigoAcesso)),
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<CustomErrorType> erroSaborNotCadastred() {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroPedido.SABOR_NOT_CADASTERED)),
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<CustomErrorType> erroPedidoWrongState() {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format("Pedido em estado errado.")),
                HttpStatus.UNAUTHORIZED);
    }

}
