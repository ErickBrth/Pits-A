package com.ufcg.psoft.pitsa9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufcg.psoft.pitsa9.dto.EntregadorDTO;
import com.ufcg.psoft.pitsa9.exception.EntregadorAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.EntregadorInvalidAvailabilityException;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotApprovedException;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotFoundException;
import com.ufcg.psoft.pitsa9.model.TipoDisponibilidade;
import com.ufcg.psoft.pitsa9.service.EntregadorService;
import com.ufcg.psoft.pitsa9.util.ErroEntregador;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EntregadorApiController {

    @Autowired  
    EntregadorService entregadorService;

    @PostMapping(value = "/entregador/")
    public ResponseEntity<?> createEntregador(@RequestBody EntregadorDTO entregadorDto) {
        try {
            EntregadorDTO entregador = entregadorService.createEntregador(entregadorDto);
            return new ResponseEntity<String>(entregador.toString(), HttpStatus.CREATED);
        } catch (EntregadorAlreadyCreatedException e) {
            return ErroEntregador.erroEntregadorJaCadastrado(entregadorDto);
        }
    }

    @GetMapping(value = "/entregadores")
    public ResponseEntity<?> listEntregadores() {

        try {
            String entregadores = entregadorService.listEntregadores();
            return new ResponseEntity<String>(entregadores, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroSemEntregadoresCadastrados();
        }
    }

    @GetMapping(value = "/entregador/{id}")
    public ResponseEntity<?> listEntregador(@PathVariable("id") Long id) {
        try {
            String entregador = entregadorService.readDadosEntregador(id);
            return new ResponseEntity<String>(entregador, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroSemEntregadoresCadastrados();
        }
    }

    @PutMapping(value = "/entregador/{id}/{codigoAcesso}")
    public ResponseEntity<?> updateEntregador(@PathVariable("id") Long id,
            @PathVariable("codigoAcesso") String codigoAcesso,
            @RequestBody EntregadorDTO entregadorDTO) {

        try {
            EntregadorDTO entregador = entregadorService.updateEntregador(id, codigoAcesso, entregadorDTO);
            return new ResponseEntity<EntregadorDTO>(entregador, HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(codigoAcesso);
        }
    }

    @DeleteMapping(value = "/entregador/{id}/{codigoAcesso}")
    public ResponseEntity<?> removeEntregador(@PathVariable("id") Long id,
            @PathVariable("codigoAcesso") String codigoAcesso) {

        try {
            entregadorService.removeEntregador(id, codigoAcesso);
            return new ResponseEntity<String>("Entregador com id " + id + " removido.", HttpStatus.OK);
        } catch (EntregadorNotFoundException e) {
            return ErroEntregador.erroEntregadorNaoEncontrado(codigoAcesso);
        }
    }
    
    @PutMapping(value = "/entregador/{id}/{codigoAcesso}/{disponibilidade}")
    public ResponseEntity<?> modificaDisponibilidade(@PathVariable("id") Long id, @PathVariable("codigoAcesso") String codigoAcesso, @PathVariable("disponibilidade") TipoDisponibilidade disponibilidade){
    	try {
    		entregadorService.modificaDisponibilidade(id, codigoAcesso, disponibilidade);
    		return new ResponseEntity<String>("Entregador com id: " + id + " teve sua disponibilidade alterada.", HttpStatus.OK);
    	} catch(EntregadorNotFoundException e) {
    		return ErroEntregador.erroEntregadorNaoEncontrado(codigoAcesso);
    	} catch(EntregadorNotApprovedException e) {
    		return ErroEntregador.erroEntregadorNaoAprovado(id);
    	} catch(EntregadorInvalidAvailabilityException e) {
    		return ErroEntregador.erroDisponibilidadeInvalida(id);
    	}
    }
}