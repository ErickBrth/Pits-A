package com.ufcg.psoft.pitsa9.service;

import java.util.List;

import com.ufcg.psoft.pitsa9.dto.PizzaDTO;
import com.ufcg.psoft.pitsa9.model.TipoPizza;

public interface CardapioService {
    
    public List<PizzaDTO> getAll();

    public List<PizzaDTO> getAllByType(TipoPizza tipo);
}
