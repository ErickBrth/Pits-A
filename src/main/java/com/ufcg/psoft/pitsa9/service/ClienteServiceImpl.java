package com.ufcg.psoft.pitsa9.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.pitsa9.dto.ClienteDTO;
import com.ufcg.psoft.pitsa9.exception.ClienteAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.ClienteNotFoundException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.model.Cliente;
import com.ufcg.psoft.pitsa9.model.Pedido;
import com.ufcg.psoft.pitsa9.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	public ModelMapper modelMapper;

	public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	public Cliente getClienteId(Long id) throws ClienteNotFoundException {
		return clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());
	}

	public ClienteDTO getClienteByAccessCode(String codigoAcesso) throws ClienteNotFoundException {
		Cliente cliente = clienteRepository.findByCodigoAcesso(codigoAcesso)
				.orElseThrow(() -> new ClienteNotFoundException());
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	public void removerClienteCadastrado(Long id, String codigoAcesso)
			throws ClienteNotFoundException, InvalidAcessTokenException {
		Cliente cliente = getClienteId(id);
		if (cliente.getCodigoAcesso().equals(codigoAcesso) && hasSixCharacters(cliente.getCodigoAcesso())) {
			clienteRepository.delete(cliente);
		} else {
			throw new InvalidAcessTokenException();
		}
	}

	public void salvarClienteCadastrado(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	public List<ClienteDTO> listarClientes() {
		List<ClienteDTO> clientes = clienteRepository.findAll().stream()
				.map(cliente -> modelMapper.map(cliente, ClienteDTO.class)).collect(Collectors.toList());
		return clientes;
	}

	public ClienteDTO criaCliente(ClienteDTO clienteDTO)
			throws ClienteAlreadyCreatedException, InvalidAcessTokenException {

		if (isClienteCadastrado(clienteDTO.getId())) {
			throw new ClienteAlreadyCreatedException();
		}

		if (!hasSixCharacters(clienteDTO.getCodigoAcesso())) {
			throw new InvalidAcessTokenException();
		}

		Cliente cliente = new Cliente(clienteDTO.getNome(),
				clienteDTO.getEndereco(),
				clienteDTO.getCodigoAcesso());

		salvarClienteCadastrado(cliente);

		return modelMapper.map(cliente, ClienteDTO.class);
	}

	public static boolean hasSixCharacters(String str) {
		// Use the Pattern class to compile a regular expression
		// that matches strings with 6 characters
		Pattern p = Pattern.compile("^.{6}$");

		// Use the matcher method of the Pattern class to check if the
		// string matches the regular expression
		return p.matcher(str).matches();
	}

	public ClienteDTO atualizaCliente(Long id, String codigoAcesso, ClienteDTO clienteDTO)
			throws ClienteNotFoundException, InvalidAcessTokenException {

		Cliente cliente = getClienteId(id);
		if (cliente.getCodigoAcesso().equals(codigoAcesso) && hasSixCharacters(codigoAcesso)) {
			cliente.setNome(clienteDTO.getNome());
			cliente.setEndereco(clienteDTO.getEndereco());
			cliente.setCodigoAcesso(clienteDTO.getCodigoAcesso());

			salvarClienteCadastrado(cliente);
		} else {
			throw new InvalidAcessTokenException();
		}

		return modelMapper.map(cliente, ClienteDTO.class);
	}

	private boolean isClienteCadastrado(Long id) {
		try {
			getClienteById(id);
			return true;
		} catch (ClienteNotFoundException e) {
			return false;
		}
	}

	public String adicionarPedido(Long idCliente, String codigoAcesso, Pedido pedido)
			throws ClienteNotFoundException, InvalidAcessTokenException {
		Cliente cliente = getClienteId(idCliente);
		if (cliente.getCodigoAcesso().equals(codigoAcesso)) {
			cliente.addPedido(pedido);
			salvarClienteCadastrado(cliente);
		} else {
			throw new InvalidAcessTokenException();
		}

		return "adicionado";
	}
}