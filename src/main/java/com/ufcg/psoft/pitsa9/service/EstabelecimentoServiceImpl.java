package com.ufcg.psoft.pitsa9.service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.pitsa9.dto.EstabelecimentoDTO;
import com.ufcg.psoft.pitsa9.exception.EntregadorNotFoundException;
import com.ufcg.psoft.pitsa9.exception.EstabelecimentoHasBeenCreatedException;
import com.ufcg.psoft.pitsa9.exception.EstabelecimentoNotFoundException;
import com.ufcg.psoft.pitsa9.exception.InvalidAcessTokenException;
import com.ufcg.psoft.pitsa9.model.Entregador;
import com.ufcg.psoft.pitsa9.model.Estabelecimento;
import com.ufcg.psoft.pitsa9.model.TipoDisponibilidade;
import com.ufcg.psoft.pitsa9.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoServiceImpl implements EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EntregadorService entregadorService;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Entregador> entregadores;

    @Autowired
    public ModelMapper modelMapper;

    public EstabelecimentoDTO findEstabelecimentoById(Long id) throws EstabelecimentoNotFoundException {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new EstabelecimentoNotFoundException());

        return modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
    }

    public EstabelecimentoDTO criaEstabelecimento(EstabelecimentoDTO estabelecimentoDTO)
            throws EstabelecimentoHasBeenCreatedException {

        if (isEstabelecimentoCriado(estabelecimentoDTO.getId())) {
            throw new EstabelecimentoHasBeenCreatedException();
        }

        Estabelecimento estabelecimento = new Estabelecimento(estabelecimentoDTO.getNome(),
                estabelecimentoDTO.getCodigoAcesso());

        salvarEstabelecimento(estabelecimento);

        return modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
    }

    @Override
    public EstabelecimentoDTO atualizaEstabelecimento(EstabelecimentoDTO estabelecimentoDTO) {
        Estabelecimento pizzaria = null;

        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();

        for (Estabelecimento estabelecimento : estabelecimentos) {
            pizzaria = estabelecimento;
        }

        if (pizzaria != null) {
            pizzaria.setNome(estabelecimentoDTO.getNome());
            pizzaria.setCodigoAcesso(estabelecimentoDTO.getCodigoAcesso());
            salvarEstabelecimento(pizzaria);
        }

        return modelMapper.map(pizzaria, EstabelecimentoDTO.class);
    }

    @Override
    public List<EstabelecimentoDTO> listarEstabelecimentos() {
        List<EstabelecimentoDTO> estabelecimentos = estabelecimentoRepository.findAll()
                .stream()
                .map(estabelecimento -> modelMapper.map(estabelecimento, EstabelecimentoDTO.class))
                .collect(Collectors.toList());

        return estabelecimentos;
    }

    private boolean isEstabelecimentoCriado(Long id) {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();

        if (estabelecimentos.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void salvarEstabelecimento(Estabelecimento estabelecimento) {
        estabelecimentoRepository.save(estabelecimento);
    }

    @Override
    public boolean verificaCodigoAcesso(String codigoAcesso) throws InvalidAcessTokenException {

        Estabelecimento pizzaria = new Estabelecimento();

        if (!hasSixCharacters(codigoAcesso))
            throw new InvalidAcessTokenException();

        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();

        for (Estabelecimento estabelecimento : estabelecimentos) {
            pizzaria = estabelecimento;
        }

        if (pizzaria != null) {
            if (pizzaria.getCodigoAcesso().equals(codigoAcesso) && hasSixCharacters(codigoAcesso)) {
                return true;
            } else {
                throw new InvalidAcessTokenException();
            }
        } else {
            throw new InvalidAcessTokenException();
        }
    }

    @Override
    public void aprovarEntregador(Long idEntregador, Boolean aprovacao) throws EntregadorNotFoundException {
        Estabelecimento pizzaria = null;

        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();

        for (Estabelecimento estabelecimento : estabelecimentos) {
            pizzaria = estabelecimento;
        }

        if (pizzaria != null) {
            entregadorService.setIsAprovado(idEntregador, aprovacao);
            salvarEstabelecimento(pizzaria);
            entregadorService.setDescanso(idEntregador, TipoDisponibilidade.Descanso);
        }
    }
    // TODO
    // public Entregador getEntregador(){

    // }

    public static boolean hasSixCharacters(String str) {
        // Use the Pattern class to compile a regular expression
        // that matches strings with 6 characters
        Pattern p = Pattern.compile("^.{6}$");

        // Use the matcher method of the Pattern class to check if the
        // string matches the regular expression
        return p.matcher(str).matches();
    }

    @Override
    public void deleteEntregador(Long idEntregador, String codigoAcessoEstabelecimento)
            throws EntregadorNotFoundException, InvalidAcessTokenException {
        String codigoAcessoentregador = entregadorService.getEntregadorById(idEntregador).getCodigoAcesso();
        if (verificaCodigoAcesso(codigoAcessoEstabelecimento)) {
            entregadorService.removeEntregador(idEntregador, codigoAcessoentregador);
        }
    }
}