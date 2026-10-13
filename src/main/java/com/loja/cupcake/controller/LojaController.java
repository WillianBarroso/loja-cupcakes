package com.loja.cupcake.controller;

import com.loja.cupcake.model.*;
import com.loja.cupcake.repository.PedidoRepository;
import com.loja.cupcake.service.CupcakeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LojaController {

    @Autowired
    private CupcakeService cupcakeService;

    @Autowired
    private Carrinho carrinho;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cupcakes", cupcakeService.listarTodos());
        model.addAttribute("carrinhoQtd", carrinho.getQuantidadeTotal());
        return "index";
    }

    @PostMapping("/carrinho/adicionar/{id}")
    public String adicionarAoCarrinho(@PathVariable Long id, @RequestParam(defaultValue = "1") int quantidade) {
        Cupcake cupcake = cupcakeService.buscarPorId(id);
        carrinho.adicionarItem(cupcake, quantidade);
        return "redirect:/";
    }

    @GetMapping("/carrinho")
    public String exibirCarrinho(Model model) {
        model.addAttribute("carrinho", carrinho);
        return "carrinho";
    }

    @PostMapping("/carrinho/remover/{id}")
    public String removerDoCarrinho(@PathVariable Long id) {
        carrinho.removerItem(id);
        return "redirect:/carrinho";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login?redirect=checkout";
        }

        if (carrinho.getItens().isEmpty()) {
            return "redirect:/";
        }

        // 1. Instancia o Pedido vinculado ao Usuário Logado
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuarioLogado);
        pedido.setValorTotal(carrinho.getTotal());

        // 2. Converte os itens do carrinho em itens do pedido e atualiza o estoque
        for (ItemCarrinho itemCarrinho : carrinho.getItens()) {
            Cupcake cupcake = itemCarrinho.getCupcake();

            // Abate o estoque do produto
            int novoEstoque = cupcake.getEstoque() - itemCarrinho.getQuantidade();
            cupcake.setEstoque(Math.max(0, novoEstoque));
            cupcakeService.salvar(cupcake);

            // Cria o registro do item do pedido
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setCupcake(cupcake);
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setPrecoUnitario(cupcake.getPreco());

            pedido.getItens().add(itemPedido);
        }

        // 3. Salva o pedido no banco (salva os itens em cascata)
        pedidoRepository.save(pedido);

        model.addAttribute("usuario", usuarioLogado);
        model.addAttribute("pedido", pedido);

        // 4. Limpa o carrinho da sessão
        carrinho.limpar();

        return "sucesso";
    }
}