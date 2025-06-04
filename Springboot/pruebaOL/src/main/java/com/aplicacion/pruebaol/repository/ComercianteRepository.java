package com.aplicacion.pruebaol.repository;

import com.aplicacion.pruebaol.entity.Comerciante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComercianteRepository extends JpaRepository<Comerciante, Long> {
    @Query("SELECT DISTINCT c.municipio FROM Comerciante c")
    List<String> findDistinctMunicipios();

    Page<Comerciante> findByRazonSocialContainingIgnoreCaseAndEstadoContainingIgnoreCase(
            String razonSocial, LocalDate fechaRegistro, String estado, Pageable pageable);

    @Query(value = "BEGIN ? := reto04.reporte_comerciantes(); END;", nativeQuery = true)
    List<Object[]> obtenerReporteComerciantes();
}
