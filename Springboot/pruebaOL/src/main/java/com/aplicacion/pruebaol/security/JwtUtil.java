package com.aplicacion.pruebaol.security;

import com.aplicacion.pruebaol.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_BASE64 = "s89zIqAokdZZWbcorUpGq0xhjJ2c9Z+/PBjrMFAwQiPhNbwl02/3Trmwafn6R/BXU1ELpHJb0D8o+xPmj3XSkg==";
    private final Key SECRET;
    private final long EXPIRACION = 3600000; // 1 hora

    public JwtUtil() {
        this.SECRET = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_BASE64));
    }

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("rol", usuario.getRol().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACION))
                .signWith(SECRET, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, Usuario usuario) {
        String email = extractUsername(token);
        return email.equals(usuario.getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
