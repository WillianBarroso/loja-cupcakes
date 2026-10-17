package com.loja.cupcake.controller;

import com.loja.cupcake.model.Carrinho;
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

    @GetMapping
    public String exibirCarrinho(Model model) {
        if (!model.containsAttribute("sucesso")) {
            model.addAttribute("sucesso", false);
        }
        model.addAttribute("carrinho", carrinhoService.obterCarrinho());
        return "carrinho";
    }

    @PostMapping("/adicionar")
    public String adicionarAoCarrinho(@RequestParam("id") Long id,
                                      @RequestParam(value = "quantidade", defaultValue = "1") Integer quantidade) {
        carrinhoService.adicionarItem(id, quantidade);
        return "redirect:/";
    }

    @PostMapping("/remover")
    public String removerDoCarrinho(@RequestParam("id") Long id) {
        carrinhoService.removerItem(id);
        return "redirect:/carrinho";
    }
}