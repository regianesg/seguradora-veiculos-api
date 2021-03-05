package com.br.desafio.apolices.seguros.api.service;

import com.br.desafio.apolices.seguros.api.model.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();
    Cliente save(Cliente cliente);
    Cliente getCliente(String id);
    Cliente update(Cliente cliente);
    String deleteById(String id);

}
