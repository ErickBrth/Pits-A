package com.ufcg.psoft.pitsa9.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.pitsa9.exception.EntregadorInvalidAvailabilityException;
import com.ufcg.psoft.pitsa9.dto.EntregadorDTO;
import com.ufcg.psoft.pitsa9.exception.EntregadorAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotApprovedException;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotFoundException;
import com.ufcg.psoft.pitsa9.model.Entregador;
import com.ufcg.psoft.pitsa9.model.TipoDisponibilidade;
import com.ufcg.psoft.pitsa9.repository.EntregadorRepository;

@Service
public class EntregadorServiceImpl implements EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public EntregadorDTO createEntregador(EntregadorDTO entregadorDTO) throws EntregadorAlreadyCreatedException {
        if (isEntregadorCadastrado(entregadorDTO.getId())) {
            throw new EntregadorAlreadyCreatedException();
        }

        Entregador entregador = new Entregador(entregadorDTO.getNome(),
                entregadorDTO.getPlacaVeiculo(),
                entregadorDTO.getTipoVeiculo(),
                entregadorDTO.getCorVeiculo(),
                entregadorDTO.getCodigoAcesso(),
                entregadorDTO.getDisponibilidade());

        saveEntregador(entregador);

        return modelMapper.map(entregador, EntregadorDTO.class);
    }

    private void saveEntregador(Entregador entregador) {
        entregadorRepository.save(entregador);
    }

    private Entregador getEntregadorId(Long id) throws EntregadorNotFoundException {
        return entregadorRepository.findById(id).orElseThrow(() -> new EntregadorNotFoundException());
    }

    private boolean isEntregadorCadastrado(Long id) {
        try {
            getEntregadorId(id);
            return true;
        } catch (EntregadorNotFoundException e) {
            return false;
        }
    }

    @Override
    public String readDadosEntregador(Long id) throws EntregadorNotFoundException {
        Entregador entregador = entregadorRepository.findById(id).get();
        if (isEntregadorCadastrado(id)) {
            EntregadorDTO entregadorDto = modelMapper.map(entregador, EntregadorDTO.class);
            return entregadorDto.toString();
        } else {
            throw new EntregadorNotFoundException();
        }
    }

    public String listEntregadores() throws EntregadorNotFoundException {

        List<EntregadorDTO> entregadores = entregadorRepository.findAll().stream()
                .map(entregador -> modelMapper.map(entregador, EntregadorDTO.class)).collect(Collectors.toList());
        if (entregadores.isEmpty()) {
            throw new EntregadorNotFoundException();
        }
        return entregadores.toString();
    }

    @Override
    public EntregadorDTO updateEntregador(Long id, String codigoAcesso, EntregadorDTO entregadorDTO)
            throws EntregadorNotFoundException {
        Entregador entregador = getEntregadorId(id);
        if (entregador.getCodigoAcesso().equals(codigoAcesso)) {
            entregador.setNome(entregadorDTO.getNome());
            entregador.setCorVeiculo(entregadorDTO.getCorVeiculo());
            entregador.setPlacaVeiculo(entregadorDTO.getPlacaVeiculo());
            entregador.setTipoVeiculo(entregadorDTO.getTipoVeiculo());
            entregador.setCodigoAcesso(entregadorDTO.getCodigoAcesso());

            saveEntregador(entregador);
        }

        return modelMapper.map(entregador, EntregadorDTO.class);

    }

    @Override
    public void removeEntregador(Long id, String codigoAcesso) throws EntregadorNotFoundException {
        Entregador cliente = getEntregadorId(id);
        if (cliente.getCodigoAcesso().equals(codigoAcesso)) {
            entregadorRepository.delete(cliente);
        }
    }

    @Override
    public void setIsAprovado(Long id, Boolean isAprovado) throws EntregadorNotFoundException {
        Entregador entregador = getEntregadorId(id);
        entregador.setIsAprovado(isAprovado);
        saveEntregador(entregador);
    }

    @Override
    public List<Entregador> listEntregadoresPendentes() {
        return entregadorRepository.findAll().stream().filter(entregador -> !entregador.getIsAprovado())
                .collect(Collectors.toList());
    }

    @Override 
    public List<Entregador> listEntregadoresAprovados() {
        return entregadorRepository.findAll().stream().filter(entregador -> entregador.getIsAprovado())
                .collect(Collectors.toList());
    }
    
    @Override
    public Entregador getEntregadorById(Long id) throws EntregadorNotFoundException {
        Entregador entregador = entregadorRepository.findById(id).get();
        if (isEntregadorCadastrado(id)) {
            return entregador;
        } else {
            throw new EntregadorNotFoundException();
        }
    }
    
    @Override
    public void setDescanso(Long id, TipoDisponibilidade disponibilidade) throws EntregadorNotFoundException{
    	Entregador entregador = getEntregadorId(id);
    	entregador.setDisponibilidade(TipoDisponibilidade.Descanso);
    	saveEntregador(entregador);
    }
    
    @Override
    public void modificaDisponibilidade(Long id, String codigoAcesso, TipoDisponibilidade disponibilidade) throws EntregadorNotFoundException, EntregadorNotApprovedException, EntregadorInvalidAvailabilityException{
    	Entregador entregador = getEntregadorId(id);
    	if (entregador.getCodigoAcesso().equals(codigoAcesso)){
    		if(entregador.getIsAprovado() == true) {
    			if(disponibilidade == TipoDisponibilidade.Ativo || disponibilidade == TipoDisponibilidade.Descanso) {
    				entregador.setDisponibilidade(disponibilidade);
    	    		saveEntregador(entregador);
    			}
    			else {
    				throw new EntregadorInvalidAvailabilityException();
    			}
    		}
    		else {
    			throw new EntregadorNotApprovedException();
    		}
    	}
    	else {
    		throw new EntregadorNotFoundException();
    	}
    	}
}