package com.ufcg.psoft.pitsa9.service;

import java.util.List;

import com.ufcg.psoft.pitsa9.dto.EstabelecimentoDTO;
import com.ufcg.psoft.pitsa9.exception.EstabelecimentoHasBeenCreatedException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.exception.*;

public interface EstabelecimentoService {

    public EstabelecimentoDTO criaEstabelecimento(EstabelecimentoDTO estabelecimentoDTO)
            throws EstabelecimentoHasBeenCreatedException;

    public List<EstabelecimentoDTO> listarEstabelecimentos();

    public boolean verificaCodigoAcesso(String codigoAcesso) throws InvalidAcessTokenException;

    public EstabelecimentoDTO atualizaEstabelecimento(EstabelecimentoDTO estabelecimentoDTO);

    public void aprovarEntregador(Long idEntregador, Boolean aprovacao) throws EntregadorNotFoundException;

    public void deleteEntregador(Long idEntregador, String codigoAcesso)
            throws EntregadorNotFoundException, InvalidAcessTokenException;
}