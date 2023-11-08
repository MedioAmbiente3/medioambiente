package com.equipotres.medioambiente.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/noticia")
public class NoticiasController {

    @GetMapping(value = "/registrar")
    public String generar_noticias() {
        return "generar_noticias";
    }


}
