package com.tecsup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de acceso publico.
 * Ruta base: /api
 * Ruta completa: /api/free (acceso sin autenticacion)
 */
@RestController
@RequestMapping("/api")
public class PublicController {

    @GetMapping("/free")
    public String free() {
        return "Endpoint público funcionando";
    }
}
