package com.ufcg.psoft.pitsa9.service;

import java.util.List;

import com.ufcg.psoft.pitsa9.dto.ClienteDTO;
import com.ufcg.psoft.pitsa9.exception.ClienteAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.ClienteNotFoundException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.model.Cliente;
import com.ufcg.psoft.pitsa9.model.Pedido;

public interface ClienteService {

	public Cliente getClienteId(Long id) throws ClienteNotFoundException;

	public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException;

	public void removerClienteCadastrado(Long id, String codigoAcesso)
			throws ClienteNotFoundException, InvalidAcessTokenException;

	public ClienteDTO getClienteByAccessCode(String codigoAcesso) throws ClienteNotFoundException;

	public List<ClienteDTO> listarClientes();

	public ClienteDTO criaCliente(ClienteDTO clienteDTO)
			throws ClienteAlreadyCreatedException, InvalidAcessTokenException;

	public ClienteDTO atualizaCliente(Long id, String codigoAcesso, ClienteDTO clienteDTO)
			throws ClienteNotFoundException, InvalidAcessTokenException;

	public String adicionarPedido(Long idCliente, String codigoAcesso, Pedido pedido)
			throws ClienteNotFoundException, InvalidAcessTokenException;

	public void salvarClienteCadastrado(Cliente cliente);

}
