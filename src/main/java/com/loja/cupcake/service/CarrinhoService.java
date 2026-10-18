package com.loja.cupcake.service;

import com.loja.cupcake.model.Carrinho;
import com.loja.cupcake.model.Cupcake;
import com.loja.cupcake.model.ItemCarrinho;
import com.loja.cupcake.repository.CupcakeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CarrinhoService {

    private final CupcakeRepository cupcakeRepository;
    private final Carrinho carrinho;

    // Injeção via Construtor garante a criação correta dos Proxies de Sessão
    public CarrinhoService(CupcakeRepository cupcakeRepository, Carrinho carrinho) {
        this.cupcakeRepository = cupcakeRepository;
        this.carrinho = carrinho;
    }

    public Carrinho obterCarrinho() {
        return this.carrinho;
    }

    public void adicionarItem(Long cupcakeId, Integer quantidade) {
        if (cupcakeId == null || quantidade == null || quantidade <= 0) return;

        Optional<Cupcake> cupcakeOpt = cupcakeRepository.findById(cupcakeId);
        if (cupcakeOpt.isPresent()) {
            Cupcake cupcake = cupcakeOpt.get();
            this.carrinho.adicionarItem(cupcake, quantidade);
        }
    }

    public void removerItem(Long cupcakeId) {
        this.carrinho.removerItem(cupcakeId);
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