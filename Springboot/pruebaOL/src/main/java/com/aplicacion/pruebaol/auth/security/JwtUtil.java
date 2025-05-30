package com.aplicacion.pruebaol.auth.security;

import com.aplicacion.pruebaol.auth.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "s89zIqAokdZZWbcorUpGq0xhjJ2c9Z+/PBjrMFAwQiPhNbwl02/3Trmwafn6R/BXU1ELpHJb0D8o+xPmj3XSkg==";
    private final long EXPIRACION = 3600000; // 1 hora

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("rol", usuario.getRol().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String getCorreo(String token) {
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
