package com.aplicacion.pruebaol.controller;

import com.aplicacion.pruebaol.dto.AuthRequest;
import com.aplicacion.pruebaol.dto.AuthResponse;
import com.aplicacion.pruebaol.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin // Política de CORS
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.email, request.password);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
