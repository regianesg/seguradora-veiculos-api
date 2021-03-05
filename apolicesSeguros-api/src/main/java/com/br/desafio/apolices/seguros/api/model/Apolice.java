package com.br.desafio.apolices.seguros.api.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
public class Apolice {

    @Id
    private String id;

    @NonNull
    private String numero;
    
    @NonNull
    private LocalDate vigenciaInicial;

    @NonNull
    private LocalDate vigenciaFinal;
    
    @NonNull
    private String placaVeiculo;

    @NonNull
    private Float valor;

    @NonNull
    @DBRef
    private Cliente clienteApolice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public LocalDate getVigenciaInicial() {
        return vigenciaInicial;
    }

    public void setVigenciaInicial(LocalDate vigenciaInicial) {
        this.vigenciaInicial = vigenciaInicial;
    }

    public LocalDate getVigenciaFinal() {
        return vigenciaFinal;
    }

    public void setVigenciaFinal(LocalDate vigenciaFinal) {
        this.vigenciaFinal = vigenciaFinal;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Cliente getClienteApolice() {
        return clienteApolice;
    }

    public void setClienteApolice(Cliente clienteApolice) {
        this.clienteApolice = clienteApolice;
    }
}
