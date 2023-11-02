package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/campana") //http://localhost:8080/campana
public class CampanaController {

    @Autowired
    private CampanaServicio campanaServicio;

    //Vista campaña registro
    @GetMapping("/registrar") //http://localhost:8080/campana/registrar
    public String campana() {
        return "campana_registro.html";
    }

    @GetMapping("/prueba")
    public String index(){return "index.html";}

    //Registrar campañas
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo,
                           @RequestParam String redaccion,
                           ModelMap modelo) {

        System.out.println("Título "+ titulo);
        System.out.println("Redaccion " + redaccion);


       return "campana_registro.html";

    }
}
