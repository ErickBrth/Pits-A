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

import com.ufcg.psoft.pitsa9.dto.PizzaDTO;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.exception.PizzaAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.PizzaNotFoundException;
import com.ufcg.psoft.pitsa9.service.PizzaService;
import com.ufcg.psoft.pitsa9.util.ErroEstabelecimento;
import com.ufcg.psoft.pitsa9.util.ErroPizza;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PizzaApiController {
    
    @Autowired
	PizzaService pizzaService;

    @GetMapping(value = "/pizza/{id}")
	public ResponseEntity<?> getPizza(@PathVariable("id") long id) {
		try {
			PizzaDTO pizza = pizzaService.getPizzaById(id);
			return new ResponseEntity<PizzaDTO>(pizza, HttpStatus.OK);
		} catch (PizzaNotFoundException e) {
			return ErroPizza.erroPizzaNaoEncontrada(id);
		}
	}
	
	@PostMapping(value = "/{codigoAcesso}/pizza/")
	public ResponseEntity<?> createPizza(@RequestBody PizzaDTO pizzaDTO, @PathVariable("codigoAcesso") String codigo) {
		try {
			PizzaDTO pizza = pizzaService.createPizza(pizzaDTO, codigo);
			return new ResponseEntity<PizzaDTO>(pizza, HttpStatus.CREATED);
		} catch (PizzaAlreadyCreatedException e) {
			return ErroPizza.erroPizzaJaCadastrada(pizzaDTO.getNome());
		} catch (InvalidAcessTokenException e) {
			return ErroEstabelecimento.erroCodigoAcessoInvalido();
		} 
	}

    @DeleteMapping(value = "/{codigoAcesso}/pizza/{id}")
	public ResponseEntity<?> removePizza(@PathVariable("id") long id, @PathVariable("codigoAcesso") String codigo) {
		try {
			pizzaService.removePizza(id, codigo);
			return new ResponseEntity<String>("Sabor removido", HttpStatus.OK);
		} catch (PizzaNotFoundException e) {
			return ErroPizza.erroPizzaNaoEncontrada(id);
		} catch (InvalidAcessTokenException e) {
			return ErroEstabelecimento.erroCodigoAcessoInvalido();
		} 
	}

    @PutMapping(value = "/{codigoAcesso}/pizza/{id}")
	public ResponseEntity<?> editPizza(@PathVariable("id") long id, @PathVariable("codigoAcesso") String codigo, @RequestBody PizzaDTO pizzaDTO) {
		try {
			PizzaDTO pizza = pizzaService.editPizza(id, pizzaDTO, codigo);
			return new ResponseEntity<PizzaDTO>(pizza, HttpStatus.OK);
		} catch (PizzaNotFoundException e) {
			return ErroPizza.erroPizzaNaoEncontrada(id);
		} catch (InvalidAcessTokenException e) {
			return ErroEstabelecimento.erroCodigoAcessoInvalido();
		}
	}

	@PutMapping(value = "/{AcessCode}/pizza/{idPizza}/{Availability}")
	public ResponseEntity<?> modifyAvailability(@PathVariable("idPizza") long id,
			@PathVariable("AcessCode") String codigo, @PathVariable("Availability") boolean availability) {

		try {
			pizzaService.ModifyAvailability(id, codigo, availability);
		} catch (InvalidAcessTokenException e) {
			return ErroEstabelecimento.erroCodigoAcessoInvalido();
		} catch (PizzaNotFoundException e) {
			return ErroPizza.erroPizzaNaoEncontrada(id);
		}

		return new ResponseEntity<String>("Pizza Availability modified: " + availability, HttpStatus.OK);
	}

}
