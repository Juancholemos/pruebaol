package com.aplicacion.pruebaol.service;

import com.aplicacion.pruebaol.repository.ComercianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ComercianteRepository comercianteRepository;

    public ResponseEntity<Resource> generarReporteCSV() {
        List<Object[]> comerciantes = comercianteRepository.obtenerReporteComerciantes();

        StringBuilder csvContent = new StringBuilder("Razón Social|Municipio|Teléfono|Correo Electrónico|Fecha Registro|Estado|Cantidad Establecimientos|Total Ingresos|Cantidad Empleados\n");

        for (Object[] comerciante : comerciantes) {
            csvContent.append(
                            comerciante[0]).append("|") // Razon Social
                    .append(comerciante[1]).append("|") // Municipio
                    .append(comerciante[2] != null ? comerciante[2] : "").append("|") // Teléfono
                    .append(comerciante[3] != null ? comerciante[3] : "").append("|") // Email
                    .append(comerciante[4]).append("|") // Fecha Registro
                    .append(comerciante[5]).append("|") // Estado
                    .append(comerciante[6]).append("|") // Cantidad Establecimientos
                    .append(comerciante[7]).append("|") // Total Ingresos
                    .append(comerciante[8]).append("\n"); // Cantidad Empleados
        }

        ByteArrayResource resource = new ByteArrayResource(csvContent.toString().getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_comerciantes.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
