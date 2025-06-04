package com.aplicacion.pruebaol.dto;

import com.aplicacion.pruebaol.entity.Comerciante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ComercianteDTO {
    @NotBlank(message = "La razón social es obligatoria.")
    @Size(max = 150, message = "La razón social no puede exceder los 150 caracteres.")
    private String razonSocial;
    @NotBlank(message = "El municipio es obligatorio.")
    @Size(max = 100, message = "El municipio no puede exceder los 100 caracteres.")
    private String municipio;
    @Size(max = 30, message = "El teléfono no puede exceder los 30 caracteres.")
    private String telefono;
    @Email(message = "El formato del correo electrónico es inválido.")
    @Size(max = 70, message = "El correo electrónico no puede exceder los 70 caracteres.")
    private String email;
    @NotNull(message = "La fecha de registro es obligatoria.")
    private LocalDate fechaRegistro;
    @NotNull(message = "El estado es obligatorio.")
    private Comerciante.Estado estado;
}
