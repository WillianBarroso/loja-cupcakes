package com.loja.cupcake.service;

import com.loja.cupcake.model.Cupcake;
import com.loja.cupcake.repository.CupcakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CupcakeService {

    @Autowired
    private CupcakeRepository cupcakeRepository;

    public List<Cupcake> listarTodos() {
        return cupcakeRepository.findAll();
    }

    public Cupcake buscarPorId(Long id) {
        return cupcakeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cupcake não encontrado com o ID: " + id));
    }

    // Retorna a soma do estoque de todos os sabores (usando getEstoque())
    public Integer calcularEstoqueTotal() {
        return cupcakeRepository.findAll()
                .stream()
                .mapToInt(Cupcake::getEstoque)
                .sum();
    }

    public Cupcake salvar(Cupcake cupcake) {
        return cupcakeRepository.save(cupcake);
    }
}