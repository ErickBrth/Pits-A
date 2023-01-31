package com.ufcg.psoft.pitsa9.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufcg.psoft.pitsa9.dto.EstabelecimentoDTO;

public class ErroEstabelecimento {

    //static final String ESTABELECIMENTO_JA_CADASTRADO = "O estabelecimento %s já esta cadastrado.";

    public static ResponseEntity<?> erroEstabelecimentoJaCadastrado(EstabelecimentoDTO estabelecimentoDTO) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType("Já existe um estabelecimento criado."), HttpStatus.CONFLICT);
    }

    public static ResponseEntity<?> erroSemEstabelecimentosCadastrados() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType("Não há estabelecimentos cadastrados."), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> erroCodigoAcessoInvalido() {
        return new ResponseEntity<CustomErrorType> (new CustomErrorType("Código de acesso inválido."), HttpStatus.UNAUTHORIZED);
    }
}