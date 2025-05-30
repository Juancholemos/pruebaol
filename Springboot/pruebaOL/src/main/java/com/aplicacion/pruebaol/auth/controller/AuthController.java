package com.aplicacion.pruebaol.auth.controller;

import com.aplicacion.pruebaol.auth.dto.AuthRequest;
import com.aplicacion.pruebaol.auth.dto.AuthResponse;
import com.aplicacion.pruebaol.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin // Política de CORS
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.email, request.password);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/login") // O @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> showLoginPage() {
        // Lógica para mostrar una página de login o un mensaje
        return ResponseEntity.ok("Esta es la página de login. Por favor, usa POST para enviar credenciales.");
    }
}
