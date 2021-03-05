package com.br.desafio.apolices.seguros.api.service.impl;

import com.br.desafio.apolices.seguros.api.dto.ApoliceDTO;
import com.br.desafio.apolices.seguros.api.model.Apolice;
import com.br.desafio.apolices.seguros.api.model.Cliente;
import com.br.desafio.apolices.seguros.api.parse.ParseApolice;
import com.br.desafio.apolices.seguros.api.repository.ApoliceRepository;
import com.br.desafio.apolices.seguros.api.repository.ClienteRepository;
import com.br.desafio.apolices.seguros.api.service.ApoliceService;
import com.br.desafio.apolices.seguros.api.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service("ApoliceService")
public class ApoliceServiceImpl implements ApoliceService {

    protected static final String MSG_NOT_FOUND_SEARCH = "Nao foi encontrado apolice  para a consulta";
    protected static final String MSG_NOT_FOUND_UPDATE = "Nao h� apolice para alterar";
    protected static final String MSG_NOT_FOUND_DELETE = "Nao h� apolice para excluir ";
    protected static final String MSG_INVALID_APOLICE_DADOS = "Apolice informados incorretamente";
    protected static final String MSG_INVALID_CLIENTE_DADOS = "Nao existe cliente para criar nova apolice";
    protected static final String MSG_INVALID_APOLICE_NUMERO ="Apolice nao existe";
    protected static final String MSG_DELETE_SUCCESS = "Cadastro Removido com Sucesso";

    @Autowired
    ApoliceRepository apoliceRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    @ResponseStatus(HttpStatus.OK)
    public List<Apolice> findAll() {
        return apoliceRepository.findAll();
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public Apolice save(Apolice apolice) {
        if(validarCamposApolice(apolice)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_INVALID_APOLICE_DADOS);
        }

        if(!clienteRepository.existsById(apolice.getClienteApolice().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_INVALID_CLIENTE_DADOS);
        }

        apolice.setNumero(Long.toString(new Date().getTime()));
        Apolice apoliceSave = apoliceRepository.save(apolice);
        return apoliceRepository.findById(apoliceSave.getId()).get();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public Apolice getApolice(String id) {
        return apoliceRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_NOT_FOUND_SEARCH)
        );
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public Apolice updateApolice(Apolice apolice) {

        if(validarCamposApolice(apolice)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_INVALID_APOLICE_DADOS);
        }

        if(!clienteRepository.existsById(apolice.getClienteApolice().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_INVALID_CLIENTE_DADOS);
        }

        if (!apoliceRepository.existsById(apolice.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_NOT_FOUND_UPDATE);
        }

        return apoliceRepository.save(apolice);
    }

    @Override
    public String deleteById(String id) {
        if (!apoliceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MSG_NOT_FOUND_DELETE);
        }
        apoliceRepository.deleteById(id);
        return MSG_DELETE_SUCCESS;
    }

    @Override
    public ApoliceDTO buscaApolicePorNumero(String numero) {

        Apolice apolice = apoliceRepository.findByNumero(numero);

        if(apolice == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_INVALID_APOLICE_NUMERO);
        }

        ApoliceDTO apoliceDTO = ParseApolice.parseDto(apolice);
        long dias = apoliceDTO.getVigenciaInicial().until(apoliceDTO.getVigenciaFinal(),  ChronoUnit.DAYS);

        //verifica se apolice est� vencida
        if (apoliceDTO.getVigenciaFinal().isEqual(LocalDate.now()) || !apoliceDTO.getVigenciaFinal().isAfter(LocalDate.now())){
            apoliceDTO.setStatusApolice("Apolice vencida");
            apoliceDTO.setPeriodoValido("Apolice vencida a:  " + dias +" dias");
        } else { // caso contrario est� ok 
            apoliceDTO.setStatusApolice("Apolice est� OK");
            apoliceDTO.setPeriodoValido("Apolice vtem validade por mais "+ dias+ " dias");

        }

        return apoliceDTO;
    }

    private boolean validarCamposApolice(Apolice apolice){

        return StringUtils.isBlank(apolice.getPlacaVeiculo()) ||
               StringUtils.isBlank(apolice.getValor().toString()) ||
               StringUtils.isBlank(apolice.getVigenciaFinal().toString()) ||
               StringUtils.isBlank(apolice.getVigenciaInicial().toString()) ||
               StringUtils.isBlank(apolice.getClienteApolice().getId());
    }
}
