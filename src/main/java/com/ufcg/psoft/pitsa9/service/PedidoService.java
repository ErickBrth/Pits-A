package com.ufcg.psoft.pitsa9.service;

import java.util.List;

import com.ufcg.psoft.pitsa9.dto.PedidoDTO;
import com.ufcg.psoft.pitsa9.exception.*;

// ! Criar DTO, e Exceptions de pedido

import com.ufcg.psoft.pitsa9.model.*;
// import com.ufcg.psoft.pitsa9.dto.PizzaDTO;
// import com.ufcg.psoft.pitsa9.exception.*;
// import com.ufcg.psoft.pitsa9.exception.*;

public interface PedidoService {
        public String criarPedido(String endereco, Long idCliente, List<Item> pizzas, String codigoAcesso)
                        throws PedidoAlreadyCreatedException, ClienteNotFoundException, SaborNotCreatedException,
                        ClienteAcessoNegadoException, InvalidAcessTokenException;

        public PedidoDTO setPedido(Long idPedido, PedidoDTO pedido, Long idCliente, String codigoAcesso)
                        throws SaborNotCreatedException, PedidoNotFoundException, ClienteNotFoundException,
                        ClienteAcessoNegadoException;

        public void removerPedido(Long idPedido, Long idCliente, String codigoAcesso)
                        throws ClienteNotFoundException, PedidoNotFoundException, ClienteAcessoNegadoException;

        public List<PedidoDTO> getPedidosCliente(Long idCliente, String codigoAcesso)
                        throws ClienteNotFoundException, InvalidAcessTokenException;

        public String pagar(MetodoPagamento metodo, Long idCliente, Long idPedido, String codigoAcesso)
                        throws ClienteAcessoNegadoException, ClienteNotFoundException, PedidoNotFoundException,
                        PedidoPagoException, PedidoWrongStateException;

        public String pedidoEntregue(Long idPedido, Long idCliente, String codigoAcesso)
                        throws PedidoNotFoundException, ClienteNotFoundException, ClienteAcessoNegadoException,
                        PedidoWrongStateException;

        public String saiuParaEntrega(Long idPedido, Long idCliente, String codigoAcesso, Long idEntregador)
                        throws PedidoNotFoundException, PedidoWrongStateException, InvalidAcessTokenException,
                        EntregadorNotFoundException, ClienteNotFoundException;

        public String pedidoPronto(Long idPedido, String codigoAcesso)
                        throws PedidoNotFoundException, PedidoWrongStateException, InvalidAcessTokenException;

        public String cancelarPedido(Long idPedido, Long idCliente, String codigoAcesso) throws PedidoNotFoundException,
                        ClienteAcessoNegadoException, ClienteNotFoundException, PedidoWrongStateException;

        public String getPedido(Long idPedido, Long idCliente, String codigoAcesso)
                        throws PedidoNotFoundException, ClienteAcessoNegadoException, ClienteNotFoundException;

        public List<PedidoDTO> getPedidosClienteFiltro(Long idCliente, String codigoAcesso, String filtro)
                        throws ClienteNotFoundException, InvalidAcessTokenException;

}
