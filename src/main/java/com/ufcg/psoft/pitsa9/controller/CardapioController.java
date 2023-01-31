package com.ufcg.psoft.pitsa9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.pitsa9.dto.PizzaDTO;
import com.ufcg.psoft.pitsa9.exception.ClienteNotFoundException;
import com.ufcg.psoft.pitsa9.exception.PizzaAvailableException;
import com.ufcg.psoft.pitsa9.exception.PizzaNotFoundException;
import com.ufcg.psoft.pitsa9.model.TipoPizza;
import com.ufcg.psoft.pitsa9.service.CardapioService;
import com.ufcg.psoft.pitsa9.service.PizzaService;
import com.ufcg.psoft.pitsa9.util.ErroCliente;
import com.ufcg.psoft.pitsa9.util.ErroPizza;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CardapioController {
    
    @Autowired
	CardapioService cardapioService;

	@Autowired
	PizzaService pizzaService;

    @GetMapping(value = "/cardapio")
	public ResponseEntity<?> getPizzas() {
		List<PizzaDTO> pizzas = cardapioService.getAll();
		if (pizzas.size() == 0) {
			return ErroPizza.erroSemPizzasCadastradas();
		}	
		return new ResponseEntity<List<PizzaDTO>>(pizzas, HttpStatus.OK);
	}

    @GetMapping(value = "/cardapio/getByType")
	public ResponseEntity<?> getPizza(TipoPizza tipo) {
        List<PizzaDTO> pizzas = cardapioService.getAllByType(tipo);
        if (pizzas.size() == 0) {
            return ErroPizza.erroSemPizzasCadastradas();
        }
        return new ResponseEntity<List<PizzaDTO>>(pizzas, HttpStatus.OK);
	}

	@PostMapping(value = "/{idCliente}/pizza/{idPizza}")
	public ResponseEntity<?> addInteresse(@PathVariable("idCliente") long idCliente, @PathVariable("idPizza") long idPizza) {
		try {
			pizzaService.addInteresse(idCliente, idPizza);
			return new ResponseEntity<String>("Interesse salvo", HttpStatus.OK);
		} catch (PizzaNotFoundException e) {
			return ErroPizza.erroPizzaNaoEncontrada(idPizza);
		} catch (ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEncontrado(idCliente);
		} catch (PizzaAvailableException e) {
			return ErroPizza.erroPizzaDisponivel(idPizza);
		} 
	}
}
