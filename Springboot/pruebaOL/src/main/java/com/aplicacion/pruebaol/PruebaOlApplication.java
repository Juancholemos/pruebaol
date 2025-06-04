package com.aplicacion.pruebaol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PruebaOlApplication {

    public static void main(String[] args) {
        SpringApplication.run(PruebaOlApplication.class, args);
    }

}
