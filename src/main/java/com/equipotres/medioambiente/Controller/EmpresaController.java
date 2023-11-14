package com.equipotres.medioambiente.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {


    //Vista empresa registrar
    @GetMapping(value = "/registrar")

    public String generar_empresa() {
        return "empresa_registrar";
    }


}
