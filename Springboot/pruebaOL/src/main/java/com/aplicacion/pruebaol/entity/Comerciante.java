package com.aplicacion.pruebaol.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "COMERCIANTE")
@Data
public class Comerciante {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comerciante_seq")
    @SequenceGenerator(name = "comerciante_seq", sequenceName = "SEQ_COMERCIANTE", allocationSize = 1)
    private Long idComerciante;

    @Column(name = "RAZON_SOCIAL", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "MUNICIPIO", nullable = false, length = 100)
    private String municipio;

    @Column(name = "TELEFONO", length = 30)
    private String telefono;

    @Column(name = "EMAIL", length = 70)
    private String email;

    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "ESTADO", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDate fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    public enum Estado {
        Activo, Inactivo
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_establecimiento")
    private Establecimiento establecimiento;
}
