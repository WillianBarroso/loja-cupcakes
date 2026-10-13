package com.loja.cupcake.controller;

import com.loja.cupcake.model.Usuario;
import com.loja.cupcake.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor // Cria o construtor automaticamente para os campos final
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String formCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.cadastrarUsuario(usuario);
            return "redirect:/login?cadastrado=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro";
        }
    }

    @GetMapping("/login")
    public String formLogin(@RequestParam(required = false) String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        @RequestParam(required = false) String redirect,
                        HttpSession session,
                        Model model) {

        Optional<Usuario> usuarioOpt = usuarioService.autenticar(email, senha);

        if (usuarioOpt.isPresent()) {
            session.setAttribute("usuarioLogado", usuarioOpt.get());
            if ("checkout".equals(redirect)) {
                return "redirect:/checkout";
            }
            return "redirect:/";
        }

        model.addAttribute("erro", "Credenciais inválidas!");
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}