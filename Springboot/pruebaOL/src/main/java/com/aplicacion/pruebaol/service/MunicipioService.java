package com.aplicacion.pruebaol.service;

import com.aplicacion.pruebaol.repository.ComercianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioService {
    @Autowired
    private ComercianteRepository comercianteRepository;

    @Cacheable("municipios")
    public List<String> obtenerMunicipios() {
        return comercianteRepository.findDistinctMunicipios();
    }
}
