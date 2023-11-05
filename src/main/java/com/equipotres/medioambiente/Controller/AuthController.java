package com.equipotres.medioambiente.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

       @GetMapping(value = "/campana_registro")
    public String campana_registro() {
        return "campana_registro";
    }
    
       @GetMapping(value = "/generar_noticias")
    public String generar_noticias() {
        return "generar_noticias";
    }
    
       @GetMapping(value = "/generar_empresa")
    public String generar_empresa() {
        return "generar_empresa";
    }
}
