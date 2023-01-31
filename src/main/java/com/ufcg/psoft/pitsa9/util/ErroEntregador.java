package com.ufcg.psoft.pitsa9.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufcg.psoft.pitsa9.dto.EntregadorDTO;

public class ErroEntregador {
    static final String ENTREGADOR_JA_CADASTRADO = "O Entregador com nome %s já está cadastrado com o Código de Acesso: %s.";

    static final String ENTREGADORES_NAO_CASTRADOS = "Não há entregadores cadastrados.";

    static final String ENTREGADOR_NAO_CADASTRADO = "Entregador com código de acesso %s não está cadastrado.";

    static final String ENTREGADOR_NAO_CADASTRADO_ID = "Entregador com id %s não está cadastrado.";

    static final String SEM_ENTREGADORES_APROVADOS = "Não há entregadores aprovados.";
    
    static final String ENTREGADOR_NAO_APROVADO = "O Entregador com id: %s, não está aprovado.";
    
    static final String DISPONIBILIDADE_INVALIDA = "Não é possível alterar a disponibilidade do entregador com id: %s. Opção inválida!";
    
    public static ResponseEntity<?> erroDisponibilidadeInvalida(Long idEntregador){
    	return new ResponseEntity<CustomErrorType>(
    			new CustomErrorType(
                       String.format(ErroEntregador.DISPONIBILIDADE_INVALIDA, idEntregador)),
                HttpStatus.CONFLICT);
    }
    
    public static ResponseEntity<?> erroEntregadorNaoAprovado(Long idEntregador){
    	return new ResponseEntity<CustomErrorType>(
    			new CustomErrorType(
                       String.format(ErroEntregador.ENTREGADOR_NAO_APROVADO, idEntregador)),
                HttpStatus.CONFLICT);
    }
    
   
    public static ResponseEntity<?> erroEntregadorJaCadastrado(EntregadorDTO entregadorDto) {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(
                        String.format(ErroEntregador.ENTREGADOR_JA_CADASTRADO, entregadorDto.getNome(),
                                entregadorDto.getCodigoAcesso())),
                HttpStatus.CONFLICT);
    }

    public static ResponseEntity<CustomErrorType> erroSemEntregadoresCadastrados() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.ENTREGADORES_NAO_CASTRADOS),
                HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<CustomErrorType> erroEntregadorNaoEncontrado(String codigoAcesso) {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroEntregador.ENTREGADOR_NAO_CADASTRADO, codigoAcesso)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroEntregadorNaoEncontrado(Long idEntregador) {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroEntregador.ENTREGADOR_NAO_CADASTRADO_ID, idEntregador)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroSemEntregadoresAprovados() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroEntregador.SEM_ENTREGADORES_APROVADOS),
                HttpStatus.NO_CONTENT);
    }
}
