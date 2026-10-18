package com.loja.cupcake.controller;

import com.loja.cupcake.service.CarrinhoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    // Exibe a tela do carrinho -> passa a session para obter o carrinho
    @GetMapping
    public String exibirCarrinho(Model model, HttpSession session) {
        if (!model.containsAttribute("sucesso")) {
            model.addAttribute("sucesso", false);
        }
        model.addAttribute("carrinho", carrinhoService.obterCarrinho());
        return "carrinho";
    }

    // Adiciona ao carrinho a partir da Home e permanece na Home (redirect:/)
    @PostMapping("/adicionar")
    public String adicionarAoCarrinho(@RequestParam("id") Long id,
                                      @RequestParam(value = "quantidade", defaultValue = "1") Integer quantidade,
                                      HttpSession session) {
        carrinhoService.adicionarItem(id, quantidade);
        return "redirect:/";
    }

    // Remove um item e recarrega o carrinho (redirect:/carrinho) -> passa a session
    @PostMapping("/remover")
    public String removerDoCarrinho(@RequestParam("id") Long id, HttpSession session) {
        carrinhoService.removerItem(id);
        return "redirect:/carrinho";
    }

    // Finaliza o pedido
    @PostMapping("/finalizar")
    public String finalizarPedido(HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Você precisa estar logado para finalizar o pedido.");
            return "redirect:/login";
        }

        boolean sucesso = carrinhoService.finalizarPedido(session);

        redirectAttributes.addFlashAttribute("sucesso", sucesso);

        if (!sucesso) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não foi possível finalizar o pedido. Verifique os itens no carrinho.");
        }

        return "redirect:/carrinho";
    }
}