package com.aplicacion.pruebaol.controller;

import com.aplicacion.pruebaol.dto.ComercianteDTO;
import com.aplicacion.pruebaol.entity.Comerciante;
import com.aplicacion.pruebaol.entity.Usuario;
import com.aplicacion.pruebaol.service.ComercianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comerciantes")
public class ComercianteController {
    @Autowired
    private ComercianteService comercianteService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUXILIAR')")
    public ResponseEntity<Page<Comerciante>> listarComerciantes(
            @RequestParam(required = false, defaultValue = "") String razonSocial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaRegistro,
            @RequestParam(required = false, defaultValue = "") String estado,
            @PageableDefault(size = 5) Pageable pageable) {

        Page<Comerciante> comerciantes = comercianteService.listarComerciantes(
                razonSocial, fechaRegistro, estado, pageable);
        return ResponseEntity.ok(comerciantes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUXILIAR')")
    public ResponseEntity<Comerciante> obtenerComerciante(@PathVariable Long id) {
        Comerciante comerciante = comercianteService.obtenerPorId(id);
        return ResponseEntity.ok(comerciante);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUXILIAR')")
    public ResponseEntity<Comerciante> crearComerciante(@RequestBody ComercianteDTO dto,
                                                        @AuthenticationPrincipal Usuario usuarioAutenticado) {
        String emailUsuario = usuarioAutenticado.getEmail();
        Comerciante nuevoComerciante = comercianteService.crearComerciante(dto, emailUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoComerciante);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUXILIAR')")
    public ResponseEntity<Comerciante> actualizarComerciante(@PathVariable Long id,
                                                             @RequestBody ComercianteDTO dto,
                                                             @AuthenticationPrincipal Usuario usuarioAutenticado) {
        String emailUsuario = usuarioAutenticado.getEmail();
        Comerciante actualizado =  comercianteService.actualizarComerciante(id, dto, emailUsuario);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminarComerciante(@PathVariable Long id) {
        comercianteService.eliminarComerciante(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUXILIAR')")
    public ResponseEntity<Comerciante> modificarEstado(@PathVariable Long id,
                                                       @RequestParam String estado,
                                                       @AuthenticationPrincipal Usuario usuarioAutenticado) {
        Comerciante actualizado = comercianteService.modificarEstado(id, estado, usuarioAutenticado.getEmail());
        return ResponseEntity.ok(actualizado);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUXILIAR')")
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
