package com.tecsup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador del area del usuario.
 * Ruta base: /client
 * Ruta completa: /client/home (requiere rol USER o ADMIN)
 */
@RestController
@RequestMapping("/client")
public class UserController {

    @GetMapping("/home")
    public String home() {
        return "Bienvenido USER";
    }
}
