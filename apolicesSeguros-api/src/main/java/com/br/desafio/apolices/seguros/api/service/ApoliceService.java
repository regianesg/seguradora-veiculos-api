package com.br.desafio.apolices.seguros.api.service;

import com.br.desafio.apolices.seguros.api.dto.ApoliceDTO;
import com.br.desafio.apolices.seguros.api.model.Apolice;

import java.util.List;

public interface
ApoliceService {

    Apolice save(Apolice apolice);
    List<Apolice> findAll();
    Apolice getApolice(String id);
    Apolice updateApolice(Apolice apolice);
    String deleteById(String id);
    ApoliceDTO buscaApolicePorNumero(String numero);
}
