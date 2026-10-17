package com.loja.cupcake.service;

import com.loja.cupcake.model.Carrinho;
import com.loja.cupcake.model.Cupcake;
import com.loja.cupcake.model.ItemCarrinho;
import com.loja.cupcake.repository.CupcakeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private CupcakeRepository cupcakeRepository;

    @Autowired
    private Carrinho carrinho; // Injeta o bean @SessionScope do Spring diretamente

    public Carrinho obterCarrinho() {
        return carrinho;
    }

    public void adicionarItem(Long cupcakeId, Integer quantidade) {
        if (cupcakeId == null || quantidade == null || quantidade <= 0) return;

        cupcakeRepository.findById(cupcakeId).ifPresent(cupcake -> {
            carrinho.adicionarItem(cupcake, quantidade);
        });
    }

    public void removerItem(Long cupcakeId) {
        carrinho.removerItem(cupcakeId);
    }

    @Transactional
    public boolean finalizarPedido(HttpSession session) {
        if (carrinho.getItens() == null || carrinho.getItens().isEmpty()) {
            return false;
        }

        for (ItemCarrinho item : carrinho.getItens()) {
            Cupcake cupcakeBanco = cupcakeRepository.findById(item.getCupcake().getId())
                    .orElseThrow(() -> new RuntimeException("Cupcake não encontrado: " + item.getCupcake().getId()));

            int novoEstoque = cupcakeBanco.getEstoque() - item.getQuantidade();
            cupcakeBanco.setEstoque(Math.max(novoEstoque, 0));
            cupcakeRepository.save(cupcakeBanco);
        }

        carrinho.limpar();
        return true;
    }
}