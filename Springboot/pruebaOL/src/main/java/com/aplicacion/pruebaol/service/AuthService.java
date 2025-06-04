package com.aplicacion.pruebaol.service;

import com.aplicacion.pruebaol.entity.Usuario;
import com.aplicacion.pruebaol.repository.UsuarioRepository;
import com.aplicacion.pruebaol.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwt;

    public String login(String email, String password) {
        Usuario user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(password, user.getPassword()))
            throw new RuntimeException("Contraseña incorrecta");

        return jwt.generarToken(user);
    }
}
