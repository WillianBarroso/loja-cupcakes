package com.loja.cupcake.controller;

import com.loja.cupcake.service.CupcakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LojaController {

    @Autowired
    private CupcakeService cupcakeService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cupcakes", cupcakeService.listarTodos());
        return "index";
    }
}