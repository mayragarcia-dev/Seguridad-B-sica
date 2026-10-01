package com.tecsup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador del gerente.
 * Ruta base: /manager
 * Ruta completa: /manager/reportes (requiere rol MANAGER)
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @GetMapping("/reportes")
    public String reportes() {
        return "Bienvenido MANAGER";
    }
}
