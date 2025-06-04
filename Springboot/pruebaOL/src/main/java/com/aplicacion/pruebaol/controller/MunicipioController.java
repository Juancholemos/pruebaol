package com.aplicacion.pruebaol.controller;

import com.aplicacion.pruebaol.service.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/municipios")
public class MunicipioController {
    @Autowired
    private MunicipioService municipioService;

    @GetMapping
    public ResponseEntity<List<String>> listarMunicipios() {
        List<String> municipios = municipioService.obtenerMunicipios();
        return ResponseEntity.ok(municipios);
    }
}
