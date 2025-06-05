package com.aplicacion.pruebaol.service;

import com.aplicacion.pruebaol.repository.ComercianteReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ComercianteReporteRepository comercianteReporteRepository;

    public ResponseEntity<Resource> generarReporteCSV() {
        final String SEPARATOR = "|";

        List<Object[]> datosComerciantes = comercianteReporteRepository.obtenerReporteComerciantes();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(baos);

        writer.println("RAZON_SOCIAL" + SEPARATOR +
                "MUNICIPIO" + SEPARATOR +
                "TELEFONO" + SEPARATOR +
                "EMAIL" + SEPARATOR +
                "FECHA_REGISTRO" + SEPARATOR +
                "ESTADO" + SEPARATOR +
                "CANTIDAD_ESTABLECIMIENTOS" + SEPARATOR +
                "TOTAL_INGRESOS" + SEPARATOR +
                "CANTIDAD_EMPLEADOS");

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        for (Object[] row : datosComerciantes) {
            String razonSocial = (String) row[0];
            String municipio = (String) row[1];
            String telefono = (String) row[2];
            String email = (String) row[3];
            LocalDate fechaRegistro = null;
            if (row[4] instanceof Date) {
                fechaRegistro = ((Date) row[4]).toLocalDate();
            }

            String estado = (String) row[5];
            Integer cantidadEstablecimientos = (Integer) row[6];
            BigDecimal totalIngresos = (BigDecimal) row[7];
            Integer cantidadEmpleados = (Integer) row[8];

            writer.printf("%s%s%s%s%s%s%s%s%s%s%s%s%d%s%s%s%d\n",
                    escapeCsv(razonSocial, SEPARATOR),
                    SEPARATOR,
                    escapeCsv(municipio, SEPARATOR),
                    SEPARATOR,
                    escapeCsv(telefono, SEPARATOR),
                    SEPARATOR,
                    escapeCsv(email, SEPARATOR),
                    SEPARATOR,
                    fechaRegistro != null ? fechaRegistro.format(formatter) : "",
                    SEPARATOR,
                    escapeCsv(estado, SEPARATOR),
                    SEPARATOR,
                    cantidadEstablecimientos,
                    SEPARATOR,
                    totalIngresos != null ? totalIngresos.toPlainString() : "",
                    SEPARATOR,
                    cantidadEmpleados
            );
        }

        writer.flush();
        writer.close();

        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"reporte_comerciantes.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

    private String escapeCsv(String value, String separator) {
        if (value == null) {
            return "";
        }

        if (value.contains(separator) || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
