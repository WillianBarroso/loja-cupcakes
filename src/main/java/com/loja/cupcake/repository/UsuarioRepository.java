package com.loja.cupcake.repository;

import com.loja.cupcake.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca usuário pelo e-mail (usado no cadastro para evitar e-mails duplicados)
    Optional<Usuario> findByEmail(String email);

    // Busca usuário pelo e-mail e senha (usado na autenticação/login)
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}