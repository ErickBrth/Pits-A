package com.ufcg.psoft.pitsa9.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.pitsa9.dto.PizzaDTO;
import com.ufcg.psoft.pitsa9.exception.ClienteNotFoundException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.exception.PizzaAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.PizzaAvailableException;
import com.ufcg.psoft.pitsa9.exception.PizzaNotFoundException;
import com.ufcg.psoft.pitsa9.model.Cliente;
import com.ufcg.psoft.pitsa9.model.Pizza;
import com.ufcg.psoft.pitsa9.repository.PizzaRepository;

import org.modelmapper.ModelMapper;

@Service
public class PizzaServiceImpl implements PizzaService {
    
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
	public ModelMapper modelMapper;

    public List<PizzaDTO> getPizzas() {
        List<PizzaDTO> pizzas = pizzaRepository.findAll()
				.stream()
				.map(pizza -> modelMapper.map(pizza, PizzaDTO.class))
				.collect(Collectors.toList());

		return pizzas;
    }

    public PizzaDTO getPizzaById(Long id) throws PizzaNotFoundException{
        Pizza pizza = getPizzaId(id);
		return modelMapper.map(pizza, PizzaDTO.class);
    }

    private Pizza getPizzaId(Long id) throws PizzaNotFoundException{
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException());
        return pizza;
    }

    public void removePizza(Long id, String codigoAcesso) throws PizzaNotFoundException, InvalidAcessTokenException {
        estabelecimentoService.verificaCodigoAcesso(codigoAcesso);
        Pizza pizza = getPizzaId(id);
        pizzaRepository.delete(pizza);
    }

    public PizzaDTO createPizza(PizzaDTO pizzaDTO, String codigoAcesso)
            throws PizzaAlreadyCreatedException, InvalidAcessTokenException {
        estabelecimentoService.verificaCodigoAcesso(codigoAcesso);
        if (!isRegisteredPizza(pizzaDTO.getNome())) {
            Pizza novaPizza = new Pizza(pizzaDTO.getNome(), pizzaDTO.getPrecoMedia(),
                    pizzaDTO.getPrecoGrande(), pizzaDTO.getTipo());
            savePizza(novaPizza);
            return modelMapper.map(novaPizza, PizzaDTO.class);
        } else {
            throw new PizzaAlreadyCreatedException();
        }
    }

    @Override
    public void ModifyAvailability(long idPizza, String codigoAcesso, boolean availability)
            throws InvalidAcessTokenException, PizzaNotFoundException {

        estabelecimentoService.verificaCodigoAcesso(codigoAcesso);
        Pizza pizza = this.getPizzaId(idPizza);
        pizza.setDisponibilidade(availability);
        savePizza(pizza);
    }

    public PizzaDTO editPizza(Long id, PizzaDTO pizzaDTO, String codigoAcesso)
            throws PizzaNotFoundException, InvalidAcessTokenException {
        estabelecimentoService.verificaCodigoAcesso(codigoAcesso);
        Pizza pizza = getPizzaId(id);

        pizza.setNome(pizzaDTO.getNome());
        pizza.setPrecoMedia(pizzaDTO.getPrecoMedia());
        pizza.setPrecoGrande(pizzaDTO.getPrecoGrande());
        pizza.setTipo(pizzaDTO.getTipo());
        pizza.setDisponibilidade(pizzaDTO.getDisponibilidade());
        savePizza(pizza);

        return modelMapper.map(pizza, PizzaDTO.class);
    }

    private void savePizza(Pizza pizza) {
		pizzaRepository.save(pizza);		
	}

    public boolean isRegisteredPizza(String nome) {
        PizzaDTO pizza = findByNome(nome);
        if (pizza == null || nome == null) {
            return false;
        } else {
            return true;
        }
	}

    public PizzaDTO findByNome(String nome) {
        List<PizzaDTO> pizzas = getPizzas();
        for (PizzaDTO pizza : pizzas) {
            if (pizza.getNome().equalsIgnoreCase(nome)) {
                return pizza;
            }
        } 
        return null;
    }

    public PizzaDTO addInteresse(Long idCliente, Long idPizza) throws ClienteNotFoundException, PizzaNotFoundException, PizzaAvailableException {
        Cliente cliente = clienteService.getClienteId(idCliente);
        Pizza pizza = getPizzaId(idPizza);

        if (pizza.getDisponibilidade()) {
            throw new PizzaAvailableException();
        }
        pizza.addListener(cliente);
        savePizza(pizza);

        return modelMapper.map(pizza, PizzaDTO.class);
    }

    public PizzaDTO removeInteresse(Long idCliente, Long idPizza)
            throws ClienteNotFoundException, PizzaNotFoundException {
        Cliente cliente = clienteService.getClienteId(idCliente);
        Pizza pizza = getPizzaId(idPizza);

        pizza.removeListener(cliente);
        savePizza(pizza);

        return modelMapper.map(pizza, PizzaDTO.class);
    }
}
