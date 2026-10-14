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

    public Carrinho obterCarrinho(HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    public void adicionarItem(Long cupcakeId, Integer quantidade, HttpSession session) {
        if (cupcakeId == null) return;

        Optional<Cupcake> cupcakeOpt = cupcakeRepository.findById(cupcakeId);

        if (cupcakeOpt.isPresent()) {
            Cupcake cupcake = cupcakeOpt.get();
            Carrinho carrinho = obterCarrinho(session);
            carrinho.adicionarItem(cupcake, quantidade);
            session.setAttribute("carrinho", carrinho);
        }
    }

    public void removerItem(Long cupcakeId, HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho != null) {
            carrinho.removerItem(cupcakeId);
            session.setAttribute("carrinho", carrinho);
        }
    }

    @Transactional
    public boolean finalizarPedido(HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.getItens() == null || carrinho.getItens().isEmpty()) {
            return false;
        }

        // Abate o estoque de cada produto no banco de dados
        for (ItemCarrinho item : carrinho.getItens()) {
            Cupcake cupcake = item.getCupcake();
            Cupcake cupcakeBanco = cupcakeRepository.findById(cupcake.getId())
                    .orElseThrow(() -> new RuntimeException("Cupcake não encontrado: " + cupcake.getId()));

            int novoEstoque = cupcakeBanco.getEstoque() - item.getQuantidade();
            cupcakeBanco.setEstoque(Math.max(novoEstoque, 0));

            cupcakeRepository.save(cupcakeBanco);
        }

        // Limpa o carrinho na sessão
        carrinho.limpar();
        session.setAttribute("carrinho", carrinho);
        return true;
    }
}