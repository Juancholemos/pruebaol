package com.aplicacion.pruebaol.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Comerciante {
    @Id
    @GeneratedValue
    private Long idComerciante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    private String razonSocial;
    private String municipio;
    private String telefono;
    private String email;
    private LocalDateTime  fechaRegistro;
    private String estado;
    private LocalDateTime fechaActualizacion;
}
