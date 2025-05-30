import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Asumo que usas BCrypt

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //Clave usuario Juan Perez
        String plainPassword = "clave123";
        String hashedPassword = encoder.encode(plainPassword);
        System.out.println(plainPassword + "   Hashed Password: " + hashedPassword);

        //Clave usuario Jose Ospina
        plainPassword = "4dm1ncl4v3";
        hashedPassword = encoder.encode(plainPassword);
        System.out.println(plainPassword + " Hashed Password: " + hashedPassword);

        //clave segura JwtUtil
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[64]; // 64 bytes = 512 bits, perfecto para HS512
        secureRandom.nextBytes(keyBytes);
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println(base64Key);
    }
}