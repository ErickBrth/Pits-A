package com.ufcg.psoft.pitsa9.service;

import java.util.List;

import com.ufcg.psoft.pitsa9.model.Item;

public interface ItemService {
    public boolean validacao(List<Item> pizzas);

    public void addItem(List<Item> pizzas);
}
