package com.ufcg.psoft.pitsa9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.pitsa9.dto.ClienteDTO;
import com.ufcg.psoft.pitsa9.exception.ClienteAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.ClienteNotFoundException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.service.ClienteService;
import com.ufcg.psoft.pitsa9.service.PizzaService;
import com.ufcg.psoft.pitsa9.util.ErroCliente;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClienteApiController {

	@Autowired
	ClienteService clienteService;

	@GetMapping(value = "/clientes")
	public ResponseEntity<?> listarClientes() {

		List<ClienteDTO> clientes = clienteService.listarClientes();
		String novaLista = clientes.toString();
		if (clientes.isEmpty()) {
			return ErroCliente.erroSemClientesCadastrados();
		}

		return new ResponseEntity<String>(novaLista, HttpStatus.OK);
	}

	@PostMapping(value = "/cliente/")
	public ResponseEntity<?> criarCliente(@RequestBody ClienteDTO clienteDTO) {

		try {
			ClienteDTO cliente = clienteService.criaCliente(clienteDTO);
			return new ResponseEntity<String>(cliente.toString(), HttpStatus.CREATED);
		} catch (ClienteAlreadyCreatedException e) {
			return ErroCliente.erroClienteJaCadastrado(clienteDTO);
		} catch (InvalidAcessTokenException e) {
			return ErroCliente.erroClienteAcessoNegado();
		}
	}

	@GetMapping(value = "/cliente/{id}")
	public ResponseEntity<?> consultarCliente(@PathVariable("id") long id) {

		try {
			ClienteDTO cliente = clienteService.getClienteById(id);
			return new ResponseEntity<String>(cliente.toString(), HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEncontrado(id);
		}
	}

	@PutMapping(value = "/cliente/{id}/{codigoAcesso}")
	public ResponseEntity<?> atualizarCliente(@PathVariable("id") long id,
			@PathVariable("codigoAcesso") String codigoAcesso, @RequestBody ClienteDTO clienteDTO) {

		try {
			ClienteDTO cliente = clienteService.atualizaCliente(id, codigoAcesso, clienteDTO);
			return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEncontrado(id);
		} catch (InvalidAcessTokenException e) {
			return ErroCliente.erroInvalidAcessToken(codigoAcesso);
		}
	}

	@DeleteMapping(value = "/cliente/{id}/{codigoAcesso}")
	public ResponseEntity<?> removerCliente(@PathVariable("id") long id,
			@PathVariable("codigoAcesso") String codigoAcesso) {

		try {
			clienteService.removerClienteCadastrado(id, codigoAcesso);
			return new ResponseEntity<String>("Cliente id: " + id + " Removido com sucesso!", HttpStatus.OK);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEncontrado(id);
		} catch (InvalidAcessTokenException e) {
			return ErroCliente.erroInvalidAcessToken(codigoAcesso);
		}

	}
}