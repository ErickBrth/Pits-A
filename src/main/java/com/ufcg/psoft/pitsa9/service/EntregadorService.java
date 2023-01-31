package com.ufcg.psoft.pitsa9.service;

import java.util.List;

import com.ufcg.psoft.pitsa9.exception.EntregadorInvalidAvailabilityException;
import com.ufcg.psoft.pitsa9.dto.EntregadorDTO;
import com.ufcg.psoft.pitsa9.exception.EntregadorAlreadyCreatedException;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotApprovedException;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotFoundException;
import com.ufcg.psoft.pitsa9.model.Entregador;
import com.ufcg.psoft.pitsa9.model.TipoDisponibilidade;

public interface EntregadorService {

    public EntregadorDTO createEntregador(EntregadorDTO entregadorDTO) throws EntregadorAlreadyCreatedException;

    public String readDadosEntregador(Long id) throws EntregadorNotFoundException;

    public EntregadorDTO updateEntregador(Long id, String codigoAcesso, EntregadorDTO entregadorDTO)
            throws EntregadorNotFoundException;

    public void removeEntregador(Long id, String codigoAcesso) throws EntregadorNotFoundException;

    public String listEntregadores() throws EntregadorNotFoundException;

    public void setIsAprovado(Long id, Boolean aprovacao) throws EntregadorNotFoundException;

    public List<Entregador> listEntregadoresPendentes();

    public List<Entregador> listEntregadoresAprovados();
    
    public void setDescanso(Long id, TipoDisponibilidade disponibilidade) throws EntregadorNotFoundException;

	public void modificaDisponibilidade(Long idEntregador, String codigoAcesso, TipoDisponibilidade disponibilidade)
			throws EntregadorNotFoundException, EntregadorNotApprovedException, EntregadorInvalidAvailabilityException;

	public Entregador getEntregadorById(Long id) throws EntregadorNotFoundException;
}
