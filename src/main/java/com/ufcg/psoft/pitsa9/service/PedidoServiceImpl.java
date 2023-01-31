package com.ufcg.psoft.pitsa9.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.pitsa9.dto.PedidoDTO;
import com.ufcg.psoft.pitsa9.exception.ClienteAcessoNegadoException;
import com.ufcg.psoft.pitsa9.exception.ClienteNotFoundException;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotFoundException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.exception.PedidoAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.PedidoNotFoundException;
import com.ufcg.psoft.pitsa9.exception.PedidoPagoException;
import com.ufcg.psoft.pitsa9.exception.PedidoWrongStateException;
import com.ufcg.psoft.pitsa9.exception.SaborNotCreatedException;
import com.ufcg.psoft.pitsa9.model.Cliente;
import com.ufcg.psoft.pitsa9.model.Entregador;
import com.ufcg.psoft.pitsa9.model.Item;
import com.ufcg.psoft.pitsa9.model.MetodoPagamento;
import com.ufcg.psoft.pitsa9.model.Pedido;
import com.ufcg.psoft.pitsa9.model.PedidoEmPreparoState;
import com.ufcg.psoft.pitsa9.model.PedidoEmRotaState;
import com.ufcg.psoft.pitsa9.model.PedidoEntregueState;
import com.ufcg.psoft.pitsa9.model.PedidoProntoState;
import com.ufcg.psoft.pitsa9.model.PedidoRecebidoState;
import com.ufcg.psoft.pitsa9.model.State;
import com.ufcg.psoft.pitsa9.repository.PedidoEmPreparoRepository;
import com.ufcg.psoft.pitsa9.repository.PedidoEmRotaRepository;
import com.ufcg.psoft.pitsa9.repository.PedidoEntregueRepository;
import com.ufcg.psoft.pitsa9.repository.PedidoProntoRepository;
import com.ufcg.psoft.pitsa9.repository.PedidoRecebidoRepository;
import com.ufcg.psoft.pitsa9.repository.PedidoRepository;
import com.ufcg.psoft.pitsa9.repository.StateRepository;

import org.modelmapper.ModelMapper;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    public ItemService itemService;

    @Autowired
    public PedidoService pedidoService;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private PedidoEmPreparoRepository pedidoEmPreparoRepository;

    @Autowired
    private PedidoRecebidoRepository pedidoRecebidoRepository;

    @Autowired
    private PedidoEntregueRepository pedidoEntregueRepository;

    @Autowired
    private PedidoEmRotaRepository pedidoEmRotaRepository;

    @Autowired
    private PedidoProntoRepository pedidoProntoRepository;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    private EntregadorService entregadorService;

    @Override
    public String criarPedido(String endereco, Long idCliente, List<Item> pizzas, String codigoAcesso)
            throws PedidoAlreadyCreatedException, ClienteNotFoundException, SaborNotCreatedException,
            ClienteAcessoNegadoException, InvalidAcessTokenException {

        Cliente cliente = clienteService.getClienteId(idCliente);

        if (cliente.getCodigoAcesso().equals(codigoAcesso) && hasSixCharacters(codigoAcesso)) {
            if (itemService.validacao(pizzas)) {
                itemService.addItem(pizzas);

                if (cliente.getPedidos() == null) {
                    cliente.setPedidos(new ArrayList<Pedido>());
                }

                Pedido novoPedido;

                if (endereco == null) {
                    novoPedido = new Pedido(clienteService.getClienteId(idCliente).getEndereco(),
                            clienteService.getClienteId(idCliente), pizzas);
                } else {
                    novoPedido = new Pedido(endereco, clienteService.getClienteId(idCliente), pizzas);
                }

                pedidoRepository.save(novoPedido);

                State estado = new PedidoRecebidoState(novoPedido);
                stateRepository.save(estado);

                novoPedido.setState(estado);
                pedidoRepository.save(novoPedido);

                clienteService.adicionarPedido(idCliente, codigoAcesso, novoPedido);

                clienteService.salvarClienteCadastrado(clienteService.getClienteId(idCliente));
                System.out.println(novoPedido.getState().toString());
                PedidoDTO result = modelMapper.map(novoPedido, PedidoDTO.class);

                return result.toString();
            } else {
                throw new SaborNotCreatedException();
            }
        } else {
            throw new ClienteAcessoNegadoException();
        }
    }

    public static boolean hasSixCharacters(String str) {
        // Use the Pattern class to compile a regular expression
        // that matches strings with 6 characters
        Pattern p = Pattern.compile("^.{6}$");

        // Use the matcher method of the Pattern class to check if the
        // string matches the regular expression
        return p.matcher(str).matches();
    }

    public String pagar(MetodoPagamento metodo, Long idCliente, Long idPedido, String codigoAcesso)
            throws ClienteAcessoNegadoException, ClienteNotFoundException, PedidoNotFoundException,
            PedidoPagoException, PedidoWrongStateException {

        Cliente cliente = clienteService.getClienteId(idCliente);

        Pedido pedido = null;

        if (pedidoRepository.findById(idPedido).isPresent()) {
            pedido = pedidoRepository.findById(idPedido).get();
        } else {
            throw new PedidoNotFoundException();
        }

        if (cliente.getCodigoAcesso().equals(codigoAcesso)
                && pedido.getCliente().getCodigoAcesso().equals(codigoAcesso) && hasSixCharacters(codigoAcesso)) {
            if (pedido.getState().toString().equals("Pedido recebido")) {
                PedidoRecebidoState estado = (PedidoRecebidoState) pedido.getState();
                calculaValorTotal(pedido, metodo);
                pedido.nextStep();
                pedidoEmPreparoRepository.save((PedidoEmPreparoState) pedido.getState());
                pedido.setMetodoPagamento(metodo);
                pedidoRepository.save(pedido);
                pedidoRecebidoRepository.delete(estado);
                System.out.println(pedido.getState().toString());
                PedidoDTO result = modelMapper.map(pedido, PedidoDTO.class);
                return result.toString();
            } else {
                throw new PedidoPagoException();
            }

        } else {
            throw new ClienteAcessoNegadoException();
        }
    }

    @Override
    public PedidoDTO setPedido(Long idPedido, PedidoDTO pedido, Long idCliente, String codigoAcesso)
            throws SaborNotCreatedException, PedidoNotFoundException, ClienteNotFoundException,
            ClienteAcessoNegadoException {

        Pedido pedidoAntigo = null;

        if (pedidoRepository.findById(idPedido).isPresent()) {
            pedidoAntigo = pedidoRepository.findById(idPedido).get();
        } else {
            throw new PedidoNotFoundException();
        }

        Cliente cliente = clienteService.getClienteId(idCliente);
        if (cliente.getCodigoAcesso().equals(codigoAcesso)
                && pedidoAntigo.getCliente().getCodigoAcesso().equals(codigoAcesso) && hasSixCharacters(codigoAcesso)) {
            if (itemService.validacao(pedido.getPizzas())) {
                pedidoAntigo.setEndereco(pedido.getEndereco());
                pedidoAntigo.setMetodoPagamento(pedido.getPagamentos());
                pedidoAntigo.setPizzas(pedido.getPizzas());

                salvarPedido(pedidoAntigo);
            }
            return modelMapper.map(pedidoAntigo, PedidoDTO.class);
        } else {
            throw new ClienteAcessoNegadoException();
        }
    }

    @Override
    public void removerPedido(Long idPedido, Long idCliente, String codigoAcesso)
            throws ClienteNotFoundException, PedidoNotFoundException, ClienteAcessoNegadoException {

        Pedido pedido = null;

        if (pedidoRepository.findById(idPedido).isPresent()) {
            pedido = pedidoRepository.findById(idPedido).get();
        } else {
            throw new PedidoNotFoundException();
        }

        Cliente cliente = clienteService.getClienteId(idCliente);

        if (cliente.getCodigoAcesso().equals(codigoAcesso)
                && pedido.getCliente().getCodigoAcesso().equals(codigoAcesso) && hasSixCharacters(codigoAcesso)) {
            cliente.removePedido(pedido);
            clienteService.salvarClienteCadastrado(cliente);
            pedidoRepository.delete(pedido);

        } else {
            throw new ClienteAcessoNegadoException();
        }

    }

    private void calculaValorTotal(Pedido pedido, MetodoPagamento metodoPagamento) {
        Double valorTotal = 0.0;
        List<Item> itens = pedido.getPizzas();

        for (Item item : itens) {
            valorTotal += item.getValor();
        }

        if (metodoPagamento.equals(MetodoPagamento.CARTAO_DEBITO)) {
            Double desconto = valorTotal * 0.025;
            valorTotal = valorTotal - desconto;
        } else if (metodoPagamento.equals(MetodoPagamento.PIX)) {
            Double desconto = valorTotal * 0.05;
            valorTotal = valorTotal - desconto;
        }

        pedido.setValor(valorTotal);
    }

    public List<PedidoDTO> getPedidosCliente(Long idCliente, String codigoAcesso)
            throws ClienteNotFoundException, InvalidAcessTokenException {
        Cliente cliente;
        try {
            cliente = clienteService.getClienteId(idCliente);
        } catch (ClienteNotFoundException e) {
            throw new ClienteNotFoundException();
        }

        if (!cliente.getCodigoAcesso().equals(codigoAcesso) && !hasSixCharacters(codigoAcesso)) {
            throw new InvalidAcessTokenException();
        }

        List<Pedido> pedidos = cliente.getPedidos();

        if (pedidos == null) {
            return null;
        }

        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    public void salvarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    public String pedidoEntregue(Long idPedido, Long idCliente, String codigoAcesso) throws PedidoNotFoundException,
            ClienteNotFoundException, ClienteAcessoNegadoException, PedidoWrongStateException {

        Pedido pedido = null;

        if (pedidoRepository.findById(idPedido).isPresent()) {
            pedido = pedidoRepository.findById(idPedido).get();
        } else {
            throw new PedidoNotFoundException();
        }

        Cliente cliente = clienteService.getClienteId(idCliente);

        if (cliente.getCodigoAcesso().equals(codigoAcesso)
                && pedido.getCliente().getCodigoAcesso().equals(codigoAcesso) && hasSixCharacters(codigoAcesso)) {

            if (pedido.getState().toString().equals("Pedido em rota")) {
                PedidoEmRotaState estadoEmRota = (PedidoEmRotaState) pedido.getState();
                removeInteresse(idCliente, idPedido);
                pedido.nextStep();
                pedidoEntregueRepository.save((PedidoEntregueState) pedido.getState());
                pedidoRepository.save(pedido);
                pedidoEmRotaRepository.delete(estadoEmRota);
                System.out.println("ESTABELECIMENTO NOTIFICADO:\n" + "  Pedido " + pedido.getId() + " entregue");
                PedidoDTO result = modelMapper.map(pedido, PedidoDTO.class);
                return result.toString();

            } else {
                throw new PedidoWrongStateException(pedido.getState().toString());
            }

        } else {
            throw new ClienteAcessoNegadoException();
        }

    }

    @Override
    public String saiuParaEntrega(Long idPedido, Long idCliente, String codigoAcesso, Long idEntregador)
            throws PedidoNotFoundException, PedidoWrongStateException, InvalidAcessTokenException,
            EntregadorNotFoundException, ClienteNotFoundException {

        Pedido pedido = null;

        // Entregador Entregador = estabelecimentoService.;

        if (pedidoRepository.findById(idPedido).isPresent()) {
            pedido = pedidoRepository.findById(idPedido).get();
        } else {
            throw new PedidoNotFoundException();
        }

        if (estabelecimentoService.verificaCodigoAcesso(codigoAcesso)) {

            if (pedido.getState().toString().equals("Pedido pronto")) {

                PedidoProntoState estadoPronto = (PedidoProntoState) pedido.getState();
                addInteresse(idCliente, idPedido);
                pedido.nextStep();
                pedidoEmRotaRepository.save((PedidoEmRotaState) pedido.getState());
                pedidoRepository.save(pedido);
                pedidoProntoRepository.delete(estadoPronto);

                System.out.println(pedido.getState().toString());

                PedidoDTO result = modelMapper.map(pedido, PedidoDTO.class);
                return result.toString();

            } else {
                throw new PedidoWrongStateException(pedido.getState().toString());
            }

        } else {
            throw new InvalidAcessTokenException();
        }
    }

    private Pedido getPedidoCliente(Long idPedido, Long idCliente)
            throws ClienteNotFoundException, PedidoNotFoundException {
        Cliente cliente = clienteService.getClienteId(idCliente);
        List<Pedido> pedidosCliente = cliente.getPedidos();
        Pedido pedidoCliente = null;
        for (Pedido pedido : pedidosCliente) {
            if (pedido.getCliente().equals(cliente)) {
                pedidoCliente = pedido;
            } else {
                throw new PedidoNotFoundException();
            }
        }

        return pedidoCliente;
    }

    public void addInteresse(Long idCliente, Long idPedido)
            throws ClienteNotFoundException, PedidoWrongStateException, PedidoNotFoundException {
        Cliente cliente = clienteService.getClienteId(idCliente);

        Pedido pedido = getPedidoCliente(idPedido, idCliente);

        pedido.addListener(cliente);
        this.salvarPedido(pedido);
        System.out.println("Interesse adicionado com sucesso! pedido: " +
                pedido.getId() + ", saiu para entrega!");

    }

    public void removeInteresse(Long idCliente, Long idPedido)
            throws ClienteNotFoundException, PedidoNotFoundException {
        Cliente cliente = clienteService.getClienteId(idCliente);
        Pedido pedido = getPedidoCliente(idPedido, idCliente);

        pedido.removeListener(cliente);
        this.salvarPedido(pedido);

        System.out.println("Interesse removido com sucesso! pedido: " + pedido.getId() + " entregue !");
    }

    @Override
    public String pedidoPronto(Long idPedido, String codigoAcesso)
            throws PedidoNotFoundException, PedidoWrongStateException, InvalidAcessTokenException {

        Pedido pedido = null;

        if (pedidoRepository.findById(idPedido).isPresent()) {
            pedido = pedidoRepository.findById(idPedido).get();
        } else {
            throw new PedidoNotFoundException();
        }

        if (estabelecimentoService.verificaCodigoAcesso(codigoAcesso)) {

            if (pedido.getState().toString().equals("Pedido em preparo")) {

                PedidoEmPreparoState estadoEmPreparo = (PedidoEmPreparoState) pedido.getState();
                pedido.nextStep();
                pedidoProntoRepository.save((PedidoProntoState) pedido.getState());
                pedidoRepository.save(pedido);
                pedidoEmPreparoRepository.delete(estadoEmPreparo);
                System.out.println(pedido.getState().toString());
                PedidoDTO result = modelMapper.map(pedido, PedidoDTO.class);
                return result.toString();

            } else {
                throw new PedidoWrongStateException(pedido.getState().toString());
            }

        } else {
            throw new InvalidAcessTokenException();
        }
    }

    @Override
    @Transactional
    public String cancelarPedido(Long idPedido, Long idCliente, String codigoAcesso) throws PedidoNotFoundException,
            ClienteAcessoNegadoException, ClienteNotFoundException, PedidoWrongStateException {

        Pedido pedido = null;

        if (pedidoRepository.findById(idPedido).isPresent()) {
            pedido = pedidoRepository.findById(idPedido).get();
        } else {
            throw new PedidoNotFoundException();
        }
        Cliente cliente = clienteService.getClienteId(idCliente);

        if (cliente.getCodigoAcesso().equals(codigoAcesso)
                && pedido.getCliente().getCodigoAcesso().equals(codigoAcesso)) {
            if (pedido.getState().toString().equals("Pedido pronto")
                    || pedido.getState().toString().equals("Pedido em rota")
                    || pedido.getState().toString().equals("Pedido entregue")) {
                throw new PedidoWrongStateException(pedido.getState().toString());
            } else {
                pedidoRepository.delete(pedido);
                cliente.getPedidos().remove(pedido);

                return "Pedido cancelado com sucesso";
            }
        } else {
            throw new ClienteAcessoNegadoException();
        }
    }

    @Override
    public String getPedido(Long idPedido, Long idCliente, String codigoAcesso)
            throws PedidoNotFoundException, ClienteAcessoNegadoException, ClienteNotFoundException {

        List<Pedido> pedidos = pedidoRepository.findAll();

        if (pedidos == null || pedidos.isEmpty()) {
            throw new PedidoNotFoundException();
        }

        Cliente cliente = clienteService.getClienteId(idCliente);

        PedidoDTO result = null;

        if (cliente.getCodigoAcesso().equals(codigoAcesso)) {
            for (Pedido pedido : pedidos) {
                if (pedido.getId().equals(idPedido)) {
                    result = modelMapper.map(pedido, PedidoDTO.class);
                }
            }
        } else {
            throw new ClienteAcessoNegadoException();
        }

        if (result == null) {
            throw new PedidoNotFoundException();
        } else {
            return result.toString();
        }
    }

    public List<PedidoDTO> getPedidosClienteFiltro(Long idCliente, String codigoAcesso, String filtro)
            throws ClienteNotFoundException, InvalidAcessTokenException {
        Cliente cliente;
        try {
            cliente = clienteService.getClienteId(idCliente);
        } catch (ClienteNotFoundException e) {
            throw new ClienteNotFoundException();
        }

        if (!cliente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAcessTokenException();
        }

        List<Pedido> pedidosFiltradosList = new ArrayList<>();
        List<Pedido> pedidosList = cliente.getPedidos();
        for (Pedido pedido : pedidosList) {
            if (pedido.getState().toString().equals(filtro)) {
                pedidosFiltradosList.add(pedido);
            }
        }

        if (pedidosFiltradosList == null || pedidosFiltradosList.isEmpty()) {
            return null;
        }

        return pedidosFiltradosList.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .collect(Collectors.toList());
    }

}
