package com.loja.cupcake.repository;

import com.loja.cupcake.model.Pedido;
import com.loja.cupcake.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Busca o histórico de pedidos de um usuário específico
    List<Pedido> findByUsuario(Usuario usuario);
}