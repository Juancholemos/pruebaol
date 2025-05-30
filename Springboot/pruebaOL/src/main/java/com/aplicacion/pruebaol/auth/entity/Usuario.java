package com.aplicacion.pruebaol.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue
    private Long idUsuario;
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "PASSWORD", nullable = false)
    private String password; // hash

    @Enumerated(EnumType.STRING)
    @Column(name = "ROL", nullable = false)
    private Rol rol;

    public enum Rol {
        Administrador,
        Auxiliar
    }
}
