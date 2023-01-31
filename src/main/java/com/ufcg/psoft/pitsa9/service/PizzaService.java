package com.ufcg.psoft.pitsa9.service;

import java.util.List;

import com.ufcg.psoft.pitsa9.dto.PizzaDTO;
import com.ufcg.psoft.pitsa9.exception.ClienteNotFoundException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.exception.PizzaAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.PizzaAvailableException;
import com.ufcg.psoft.pitsa9.exception.PizzaNotFoundException;

public interface PizzaService {
    public List<PizzaDTO> getPizzas();

    public PizzaDTO getPizzaById(Long id) throws PizzaNotFoundException;

    public void removePizza(Long id, String codigoAcesso) throws PizzaNotFoundException, InvalidAcessTokenException;

    public PizzaDTO createPizza(PizzaDTO pizzaDTO, String codigoAcesso)
            throws PizzaAlreadyCreatedException, InvalidAcessTokenException;

    public PizzaDTO editPizza(Long id, PizzaDTO pizzaDTO, String codigoAcesso)
            throws PizzaNotFoundException, InvalidAcessTokenException;

    public boolean isRegisteredPizza(String nome);

    public PizzaDTO findByNome(String nome);

    public PizzaDTO addInteresse(Long idCliente, Long idPizza) throws ClienteNotFoundException, PizzaNotFoundException, PizzaAvailableException;

    public PizzaDTO removeInteresse(Long idCliente, Long idPizza) throws ClienteNotFoundException, PizzaNotFoundException;

    public void ModifyAvailability(long idPizza, String codigoAcesso, boolean availability)
            throws InvalidAcessTokenException, PizzaNotFoundException;
}
