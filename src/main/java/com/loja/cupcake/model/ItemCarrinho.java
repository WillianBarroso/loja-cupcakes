package com.loja.cupcake.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemCarrinho {
    private Cupcake cupcake;
    private int quantidade;

    public ItemCarrinho(Cupcake cupcake, int quantidade) {
        this.cupcake = cupcake;
        this.quantidade = quantidade;
    }

    public Double getSubtotal() {
        return cupcake.getPreco() * quantidade;
    }

}