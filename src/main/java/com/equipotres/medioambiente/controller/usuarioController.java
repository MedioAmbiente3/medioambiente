package com.equipotres.medioambiente.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class usuarioController {
    @GetMapping("/")
    public String saludar() {
        return "¡Hola, mundo!";
    }
}
