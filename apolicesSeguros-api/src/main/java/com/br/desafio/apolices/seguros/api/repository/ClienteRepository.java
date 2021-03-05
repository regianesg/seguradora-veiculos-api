package com.br.desafio.apolices.seguros.api.repository;


import com.br.desafio.apolices.seguros.api.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {

    Cliente findByCpfEquals(String cpf);

}