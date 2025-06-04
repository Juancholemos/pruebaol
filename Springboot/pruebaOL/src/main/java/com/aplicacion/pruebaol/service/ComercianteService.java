package com.aplicacion.pruebaol.service;

import com.aplicacion.pruebaol.dto.ComercianteDTO;
import com.aplicacion.pruebaol.entity.Comerciante;
import com.aplicacion.pruebaol.entity.Usuario;
import com.aplicacion.pruebaol.repository.ComercianteRepository;
import com.aplicacion.pruebaol.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ComercianteService {
    @Autowired
    private ComercianteRepository comercianteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<Comerciante> listarComerciantes(String razonSocial, LocalDate fechaRegistro, String estado, Pageable pageable) {
        return comercianteRepository.findByRazonSocialContainingIgnoreCaseAndEstadoContainingIgnoreCase(
                razonSocial, fechaRegistro, estado, pageable);
    }

    /*public Page<Comerciante> listarComerciantes(String nombre, LocalDate fechaInicio, LocalDate fechaFin,
                                                String estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return comercianteRepository.findByNombreRazonSocialContainingIgnoreCaseAndFechaRegistroBetweenAndEstado(
                nombre, fechaInicio, fechaFin, estado, pageable);
    }*/

    public Comerciante obtenerPorId(Long id) {
        return comercianteRepository.findById(id).orElseThrow(() -> new RuntimeException("Comerciante no encontrado con ID: " + id));
    }

    public Comerciante crearComerciante(ComercianteDTO dto, String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comerciante comerciante = new Comerciante();
        comerciante.setRazonSocial(dto.getRazonSocial());
        comerciante.setMunicipio(dto.getMunicipio());
        comerciante.setTelefono(dto.getTelefono());
        comerciante.setEmail(dto.getEmail());
        comerciante.setEstado(dto.getEstado());
        comerciante.setFechaRegistro(LocalDate.now());
        comerciante.setUsuario(usuario);
        comerciante.setFechaActualizacion(LocalDate.now());

        return comercianteRepository.save(comerciante);
    }

    public Comerciante actualizarComerciante(Long id, ComercianteDTO dto, String emailUsuario) {
        Comerciante comerciante = obtenerPorId(id);
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        comerciante.setRazonSocial(dto.getRazonSocial());
        comerciante.setMunicipio(dto.getMunicipio());
        comerciante.setTelefono(dto.getTelefono());
        comerciante.setEmail(dto.getEmail());
        comerciante.setEstado(dto.getEstado());
        comerciante.setUsuario(usuario);
        comerciante.setFechaActualizacion(LocalDate.now());

        return comercianteRepository.save(comerciante);
    }

    public void eliminarComerciante(Long id) {
        comercianteRepository.deleteById(id);
    }

    public Comerciante modificarEstado(Long id, String nuevoEstado, String emailUsuario) {
        Comerciante comerciante = obtenerPorId(id);
        try {
            Comerciante.Estado estadoEnum = Comerciante.Estado.valueOf(nuevoEstado);
            comerciante.setEstado(estadoEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado inválido: " + nuevoEstado + ". Los estados permitidos son: " +
                    java.util.Arrays.toString(Comerciante.Estado.values()));
        }
        comerciante.setFechaActualizacion(LocalDate.now());
        Usuario usuarioAuditoria = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado para auditoría con email: " + emailUsuario));
        comerciante.setUsuario(usuarioAuditoria);
        return comercianteRepository.save(comerciante);
    }
}
