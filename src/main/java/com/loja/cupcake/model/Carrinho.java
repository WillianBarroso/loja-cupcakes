package com.loja.cupcake.model;

import lombok.Getter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Carrinho {

    private final List<ItemCarrinho> itens = new ArrayList<>();

    // Adiciona 1 unidade por padrão (Vitrine)
    public void adicionarItem(Cupcake cupcake) {
        adicionarItem(cupcake, 1);
    }

    // Sobrecarga para adicionar N unidades de uma vez (Detalhes do produto)
    public void adicionarItem(Cupcake cupcake, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getCupcake().getId().equals(cupcake.getId())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        itens.add(new ItemCarrinho(cupcake, quantidade));
    }

    public void removerItem(Long cupcakeId) {
        itens.removeIf(item -> item.getCupcake().getId().equals(cupcakeId));
    }

    public void limpar() {
        itens.clear();
    }

    public Double getTotal() {
        return itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
    }

    public int getQuantidadeTotal() {
        return itens.stream().mapToInt(ItemCarrinho::getQuantidade).sum();
    }

}