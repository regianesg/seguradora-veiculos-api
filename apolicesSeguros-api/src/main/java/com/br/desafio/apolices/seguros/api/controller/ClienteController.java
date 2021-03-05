package com.br.desafio.apolices.seguros.api.controller;

import com.br.desafio.apolices.seguros.api.model.Cliente;
import com.br.desafio.apolices.seguros.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String hello() {
        return "Dasafio Java : API para uma seguradora de veiculos";
    }

    @PostMapping("/cliente/new")
    public Cliente insereCliente(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @GetMapping("/cliente/list-all")
    public List<Cliente> getAll() {
        return clienteService.findAll();
    }

    @GetMapping("/cliente/{id}")
    public Cliente getCliente(@PathVariable String id) {
        return clienteService.getCliente(id);
    }

    @PutMapping("/cliente/edit")
    public Cliente editCliente(@RequestBody Cliente cliente) {
        return clienteService.update(cliente);
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable String id) {
        return new ResponseEntity<String>(clienteService.deleteById(id), HttpStatus.OK);
    }
}
