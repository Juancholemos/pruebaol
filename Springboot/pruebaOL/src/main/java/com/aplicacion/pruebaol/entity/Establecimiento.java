package com.aplicacion.pruebaol.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Establecimiento {

    @Id
    @GeneratedValue
    private int idEstab;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COMERCIANTE")
    private Comerciante comerciante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    private String nombreEstab;

    @Column(name = "INGRESOS", precision = 15, scale = 2)
    private BigDecimal ingresos;

    private Long numEmpleados;
    private LocalDateTime fechaActualizacion;
}
