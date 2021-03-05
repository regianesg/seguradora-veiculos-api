package com.br.desafio.apolices.seguros.api.repository;

import com.br.desafio.apolices.seguros.api.model.Apolice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApoliceRepository extends MongoRepository<Apolice, String> {

    void deleteAllByClienteApolice(String id);
    Apolice findByNumero(String id);

}
