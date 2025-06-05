package com.aplicacion.pruebaol.service;

import com.aplicacion.pruebaol.dto.ComercianteDTO;
import com.aplicacion.pruebaol.entity.Comerciante;
import com.aplicacion.pruebaol.entity.Usuario;
import com.aplicacion.pruebaol.repository.ComercianteRepository;
import com.aplicacion.pruebaol.repository.UsuarioRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComercianteService {
    @Autowired
    private ComercianteRepository comercianteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    @Transactional(readOnly = true)
    public Page<Comerciante> listarComerciantes(String razonSocial, LocalDate fechaRegistro, String estado, Pageable pageable) {

        Specification<Comerciante> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (razonSocial != null && !razonSocial.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("razonSocial")),
                        "%" + razonSocial.toLowerCase().trim() + "%"
                ));
            }

            if (fechaRegistro != null) {
                predicates.add(criteriaBuilder.equal(root.get("fechaRegistro"), fechaRegistro));
            }

            if (estado != null && !estado.trim().isEmpty()) {
                try {
                    Comerciante.Estado estadoEnum = Comerciante.Estado.valueOf(estado.trim());
                    predicates.add(criteriaBuilder.equal(root.get("estado"), estadoEnum));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("El valor de estado '" + estado + "' no es válido. Los valores permitidos son: " +
                            java.util.Arrays.toString(Comerciante.Estado.values()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return comercianteRepository.findAll(spec, pageable);
    }
}
