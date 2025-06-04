package com.aplicacion.pruebaol.controller;

import com.aplicacion.pruebaol.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/comerciantes")
    public ResponseEntity<Resource> descargarReporteComerciantes() {
        return reporteService.generarReporteCSV();
    }
}
