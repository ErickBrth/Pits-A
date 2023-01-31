package com.ufcg.psoft.pitsa9.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.pitsa9.dto.PizzaDTO;
import com.ufcg.psoft.pitsa9.model.TipoPizza;

@Service
public class CardapioServiceImpl implements CardapioService {
    
    @Autowired
    private PizzaService pizzaService;
    
    public List<PizzaDTO> getAll() {
        List<PizzaDTO> ordenado = new ArrayList<PizzaDTO>();
        List<PizzaDTO> desordenado = pizzaService.getPizzas();

        for (PizzaDTO pizza : desordenado) {
            if (pizza.getDisponibilidade()) {
                ordenado.add(pizza);
            }
        }

        for (PizzaDTO pizza : desordenado) {
            if (!pizza.getDisponibilidade()) {
                ordenado.add(pizza);
            }
        }

        return ordenado;
    }

    public List<PizzaDTO> getAllByType(TipoPizza tipo) {
        List<PizzaDTO> listaPizzas = this.getAll();
        List<PizzaDTO> pizzas = new ArrayList<PizzaDTO>();
        for (PizzaDTO pizza : listaPizzas) {
            if (pizza.getTipo().toString().equalsIgnoreCase(tipo.toString())) {
                pizzas.add(pizza);
            }
        }

		return pizzas;
    }
}
