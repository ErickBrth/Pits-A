package com.ufcg.psoft.pitsa9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.pitsa9.dto.PizzaDTO;
import com.ufcg.psoft.pitsa9.exception.SaborNotCreatedException;
import com.ufcg.psoft.pitsa9.model.Item;
import com.ufcg.psoft.pitsa9.model.TamanhoPizza;
import com.ufcg.psoft.pitsa9.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private ItemRepository itemRepository;

    public boolean validacao(List<Item> pizzas) {
        for (Item pizza : pizzas) {
            if (!pizzaService.isRegisteredPizza(pizza.getSabor1())) {
                 return false;   
            } else if (pizza.getSabor2() != null && !pizzaService.isRegisteredPizza(pizza.getSabor2())) {
                return false;
            }
        }
        return true;
    }

    public void addItem(List<Item> pizzas) {
        for (Item pizza : pizzas) {
            setValor(pizza);
        }
    }

    private void setValor(Item pizza) {
        double valorPizza = 0;
        if (pizza.getTamanho() == TamanhoPizza.Media) {
            PizzaDTO pizza1 = pizzaService.findByNome(pizza.getSabor1());
            valorPizza = pizza1.getPrecoMedia();
        } else if (pizza.getTamanho() == TamanhoPizza.Grande) {
            PizzaDTO pizza1 = pizzaService.findByNome(pizza.getSabor1());
            if(pizza.getSabor2() != null) {
                PizzaDTO pizza2 = pizzaService.findByNome(pizza.getSabor2());
                valorPizza = (pizza1.getPrecoGrande() + pizza2.getPrecoGrande()) / 2;
            } else {
                valorPizza = (pizza1.getPrecoGrande());
            }
        }
        pizza.setValor(valorPizza);
        itemRepository.save(pizza);
    }
}
