package com.ufcg.psoft.pitsa9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.pitsa9.dto.EstabelecimentoDTO;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotFoundException;
import com.ufcg.psoft.pitsa9.exception.EstabelecimentoHasBeenCreatedException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.service.EstabelecimentoService;
import com.ufcg.psoft.pitsa9.service.EntregadorService;
import com.ufcg.psoft.pitsa9.util.ErroEntregador;
import com.ufcg.psoft.pitsa9.util.ErroEstabelecimento;

@RestController
@RequestMapping("/api")
public class EstabelecimentoController {

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @Autowired
    EntregadorService entregadorService;

    @PostMapping(value = "/estabelecimento/")
    public ResponseEntity<?> criarEstabelecimento(@RequestBody EstabelecimentoDTO estabelecimentoDTO) {

        try {
            EstabelecimentoDTO estabelecimento = estabelecimentoService.criaEstabelecimento(estabelecimentoDTO);
            return new ResponseEntity<EstabelecimentoDTO>(estabelecimento, HttpStatus.CREATED);
        } catch (EstabelecimentoHasBeenCreatedException e) {
            return ErroEstabelecimento.erroEstabelecimentoJaCadastrado(estabelecimentoDTO);
        }
    }

    @PutMapping(value = "/estabelecimento/{codigoAcesso}")
    public ResponseEntity<?> atualizarEstabelecimento(@PathVariable("codigoAcesso") String codigoAcesso,
            @RequestBody EstabelecimentoDTO estabelecimentoDTO) {
        try {
            estabelecimentoService.verificaCodigoAcesso(codigoAcesso);
        } catch (InvalidAcessTokenException e) {
            return ErroEstabelecimento.erroCodigoAcessoInvalido();
        }

        EstabelecimentoDTO estabelecimento = estabelecimentoService.atualizaEstabelecimento(estabelecimentoDTO);

        return new ResponseEntity<EstabelecimentoDTO>(estabelecimento, HttpStatus.OK);
    }

    @PutMapping(value = "/estabelecimento/aprovarEntregador/{codigoAcesso}")
    public ResponseEntity<?> aprovarEntregador(@PathVariable("codigoAcesso") String codigoAcesso, Long idEntregador,
            Boolean aprovacao) throws EntregadorNotFoundException {
        try {
            estabelecimentoService.verificaCodigoAcesso(codigoAcesso);
        } catch (InvalidAcessTokenException e) {
            return ErroEstabelecimento.erroCodigoAcessoInvalido();
        }

        try {
            estabelecimentoService.aprovarEntregador(idEntregador, aprovacao);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(idEntregador);
        }

        return new ResponseEntity<String>(entregadorService.readDadosEntregador(idEntregador), HttpStatus.OK);
    }

    @GetMapping(value = "/estabelecimento/{codigoAcesso}/entregadoresPendentes")
    public ResponseEntity<?> listEntregadoresPendentes(@PathVariable("codigoAcesso") String codigoAcesso) {
        try {
            estabelecimentoService.verificaCodigoAcesso(codigoAcesso);
        } catch (InvalidAcessTokenException e) {
            return ErroEstabelecimento.erroCodigoAcessoInvalido();
        }
        if (entregadorService.listEntregadoresPendentes().isEmpty()) {
            return new ResponseEntity<String>("Nenhum entregador pendente", HttpStatus.OK);
        }
        return new ResponseEntity<String>(entregadorService.listEntregadoresPendentes().toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/estabelecimento/{codigoAcesso}/entregadoresAprovados")
    public ResponseEntity<?> listEntregadoresAprovados(@PathVariable("codigoAcesso") String codigoAcesso) {
        try {
            estabelecimentoService.verificaCodigoAcesso(codigoAcesso);
        } catch (InvalidAcessTokenException e) {
            return ErroEstabelecimento.erroCodigoAcessoInvalido();
        }
        if (entregadorService.listEntregadoresAprovados().isEmpty()) {
            return ErroEntregador.erroSemEntregadoresAprovados();
        } else {
            String entregadores = entregadorService.listEntregadoresAprovados().toString();
            return new ResponseEntity<String>(entregadores, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/estabelecimento/{codigoAcesso}/entregador/{idEntregador}/deleteEntregador")
    public ResponseEntity<?> deleteEntregador(@PathVariable("codigoAcesso") String codigoAcesso,
            @PathVariable("idEntregador") Long idEntregador) {
        try {
            estabelecimentoService.deleteEntregador(idEntregador, codigoAcesso);
            return new ResponseEntity<String>("entregador id: " + idEntregador + " deletado com sucesso",
                    HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(idEntregador);
        } catch (InvalidAcessTokenException e) {
            return ErroEstabelecimento.erroCodigoAcessoInvalido();
        }

    }
}