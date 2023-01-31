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

import com.ufcg.psoft.pitsa9.model.Item;
import com.ufcg.psoft.pitsa9.model.MetodoPagamento;
import com.ufcg.psoft.pitsa9.dto.PedidoDTO;
import com.ufcg.psoft.pitsa9.exception.ClienteAcessoNegadoException;
import com.ufcg.psoft.pitsa9.exception.ClienteNotFoundException;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotFoundException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.exception.PedidoAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.PedidoNotFoundException;
import com.ufcg.psoft.pitsa9.exception.PedidoWrongStateException;
import com.ufcg.psoft.pitsa9.exception.PedidoPagoException;
import com.ufcg.psoft.pitsa9.exception.SaborNotCreatedException;

import com.ufcg.psoft.pitsa9.service.*;
import com.ufcg.psoft.pitsa9.util.ErroCliente;
import com.ufcg.psoft.pitsa9.util.ErroEntregador;
import com.ufcg.psoft.pitsa9.util.ErroPedido;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PedidoApiController {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    ClienteService clienteService;

    @PostMapping(value = "/createPedido/")
    public ResponseEntity<?> createPedido(@RequestBody List<Item> itens,
            String endereco, Long idCliente, String codigoCliente) {
        try {
            String pedido = pedidoService.criarPedido(endereco, idCliente, itens, codigoCliente);
            return new ResponseEntity<String>(pedido, HttpStatus.CREATED);
        } catch (PedidoAlreadyCreatedException e) {
            return ErroPedido.erroPedidoJaCadastrado();
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado(idCliente);
        } catch (SaborNotCreatedException e) {
            return ErroPedido.erroSaborNotCadastred();
        } catch (ClienteAcessoNegadoException e) {
            return ErroCliente.erroClienteAcessoNegado();
        } catch (InvalidAcessTokenException e) {
            return ErroCliente.erroInvalidAcessToken(codigoCliente);
        }
    }

    @PostMapping(value = "/pedido/pagar")
    public ResponseEntity<?> pagar(MetodoPagamento metodo, Long idCliente, Long idPedido, String codigoAcesso) {
        try {
            String pedido = pedidoService.pagar(metodo, idCliente, idPedido, codigoAcesso);
            return new ResponseEntity<String>(pedido, HttpStatus.CREATED);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado(idCliente);
        } catch (ClienteAcessoNegadoException e) {
            return ErroCliente.erroClienteAcessoNegado();
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(idPedido);
        } catch (PedidoPagoException e) {
            return ErroPedido.erroPedidoPago();
        } catch (PedidoWrongStateException e) {
            return ErroPedido.erroPedidoWrongState();
        }
    }

    @GetMapping(value = "/getPedidos/")
    public ResponseEntity<?> getPedidosCliente(Long idCliente, String codigoAcesso) {

        try {
            List<PedidoDTO> pedidos = pedidoService.getPedidosCliente(idCliente, codigoAcesso);
            String result = pedidos.toString();
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado(idCliente);
        } catch (InvalidAcessTokenException e) {
            return ErroPedido.erroInvalidAcessToken(codigoAcesso);
        }
    }

    @GetMapping(value = "/getPedido/{idPedido}")
    public ResponseEntity<?> getPedido(@PathVariable Long idPedido, Long idCliente, String codigoAcesso) {
        try {
            String pedido = pedidoService.getPedido(idPedido, idCliente, codigoAcesso);
            return new ResponseEntity<String>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(idPedido);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado(idCliente);
        } catch (ClienteAcessoNegadoException e) {
            return ErroCliente.erroClienteAcessoNegado();
        }
    }

    @DeleteMapping(value = "{idCliente}/deletePedido/{idPedido}")
    public ResponseEntity<?> deletePedido(@PathVariable Long idPedido, @PathVariable("idCliente") long id,
            String codigoAcesso) {
        try {
            pedidoService.removerPedido(idPedido, id, codigoAcesso);
            return new ResponseEntity<String>("Pedido removido com sucesso!", HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(idPedido);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado(id);
        } catch (ClienteAcessoNegadoException e) {
            return ErroCliente.erroClienteAcessoNegado();
        }
    }

    @PutMapping(value = "/updatePedido/{idPedido}")
    public ResponseEntity<?> updatePedido(@PathVariable Long idPedido, @RequestBody PedidoDTO pedidoDTO,
            Long idCliente, String codigoAcesso) {
        try {
            PedidoDTO pedido = pedidoService.setPedido(idPedido, pedidoDTO, idCliente, codigoAcesso);
            return new ResponseEntity<PedidoDTO>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(idPedido);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado(idCliente);
        } catch (SaborNotCreatedException e) {
            return ErroPedido.erroSaborNotCadastred();
        } catch (ClienteAcessoNegadoException e) {
            return ErroCliente.erroClienteAcessoNegado();
        }
    }

    @PutMapping(value = "/updatePedido/{idPedido}/pedidoEntregue")
    public ResponseEntity<?> pedidoEntregue(@PathVariable Long idPedido, Long idCliente, String codigoAcesso) {
        try {
            String pedido = pedidoService.pedidoEntregue(idPedido, idCliente, codigoAcesso);
            return new ResponseEntity<String>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(idPedido);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado(idCliente);
        } catch (ClienteAcessoNegadoException e) {
            return ErroCliente.erroClienteAcessoNegado();
        } catch (PedidoWrongStateException e) {
            return ErroPedido.erroPedidoWrongState();
        }
    }

    @PutMapping(value = "/updatePedido/{idPedido}/saiuParaEntrega")
    public ResponseEntity<?> saiuParaEntrega(@PathVariable Long idPedido, Long idCliente, String codigoAcesso,
            Long idEntregador)
            throws InvalidAcessTokenException, ClienteNotFoundException {
        try {
            String pedido = pedidoService.saiuParaEntrega(idPedido, idCliente, codigoAcesso, idEntregador);
            return new ResponseEntity<String>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(idPedido);
        } catch (PedidoWrongStateException e) {
            return ErroPedido.erroPedidoWrongState();
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(codigoAcesso);
        }
    }

    @PutMapping(value = "/updatePedido/{idPedido}/pedidoPronto")
    public ResponseEntity<?> pedidoPronto(@PathVariable Long idPedido, String codigoAcesso)
            throws InvalidAcessTokenException {
        try {
            String pedido = pedidoService.pedidoPronto(idPedido, codigoAcesso);
            return new ResponseEntity<String>(pedido, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(idPedido);
        } catch (PedidoWrongStateException e) {
            return ErroPedido.erroPedidoWrongState();
        }
    }

    @DeleteMapping(value = "/cancelarPedido/{idPedido}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long idPedido, Long idCliente, String codigoAcesso)
            throws PedidoWrongStateException, InvalidAcessTokenException {
        try {
            String res = pedidoService.cancelarPedido(idPedido, idCliente, codigoAcesso);
            return new ResponseEntity<String>(res, HttpStatus.OK);
        } catch (PedidoNotFoundException e) {
            return ErroPedido.erroPedidoNaoEncontrado(idPedido);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado();
        } catch (ClienteAcessoNegadoException e) {
            return ErroCliente.erroClienteAcessoNegado();
        } catch (PedidoWrongStateException e) {
            return ErroPedido.erroPedidoWrongState();
        }
    }

    @GetMapping(value = "/getPedidosFiltro/{idCliente}")
    public ResponseEntity<?> getPedidosFiltro(@PathVariable Long idCliente, String codigoAcesso, String filtro) {
        try {
            List<PedidoDTO> pedidos = pedidoService.getPedidosClienteFiltro(idCliente, codigoAcesso, filtro);
            return new ResponseEntity<List<PedidoDTO>>(pedidos, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return ErroCliente.erroClienteNaoEncontrado(idCliente);
        } catch (InvalidAcessTokenException e) {
            return ErroCliente.erroClienteAcessoNegado();
        }
    }
}
