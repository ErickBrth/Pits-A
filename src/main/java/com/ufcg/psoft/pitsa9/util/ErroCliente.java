package com.ufcg.psoft.pitsa9.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufcg.psoft.pitsa9.dto.ClienteDTO;

public class ErroCliente {

	static final String CLIENTE_NAO_CASTRADO = "O Cliente com ID %s não está cadastrado.";

	static final String CLIENTES_NAO_CASTRADOS = "Não há clienters cadastrados";

	static final String ACESSO_NEGADO = "Acesso negado";

	static final String NAO_FOI_POSSIVEL_ATUALIZAR = "Não foi possível atualizar a situação do cliente %s " + "nome %s";

	static final String CLIENTE_JA_CADASTRADO = "O Cliente com nome %s já está cadastrado com o ID: %s";
	
	static final String CODIGO_INVALIDO = "Código de Acesso: %s inválido. Deve Conter somente 6 dígitos";

	public static ResponseEntity<CustomErrorType> erroClienteNaoEncontrado(long id) {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(String.format(ErroCliente.CLIENTE_NAO_CASTRADO, id)), HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroClienteNaoEncontrado() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(String.format(ErroCliente.CLIENTE_NAO_CASTRADO)), HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroSemClientesCadastrados() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCliente.CLIENTES_NAO_CASTRADOS),
				HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<?> erroClienteJaCadastrado(ClienteDTO clienteDTO) {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(
						String.format(ErroCliente.CLIENTE_JA_CADASTRADO, clienteDTO.getNome(), clienteDTO.getId())),
				HttpStatus.CONFLICT);
	}
	
	public static ResponseEntity<CustomErrorType> erroInvalidAcessToken(String codigoAcesso) {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType(String.format(ErroPedido.CODIGO_INVALIDO, codigoAcesso)),
				HttpStatus.UNAUTHORIZED);
	}

	public static ResponseEntity<?> erroClienteAcessoNegado() {
		return new ResponseEntity<CustomErrorType>(
				new CustomErrorType( String.format(ErroCliente.ACESSO_NEGADO)), HttpStatus.UNAUTHORIZED);
	}
}
