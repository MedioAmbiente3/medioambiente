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
@RequestMapping("/campana")
public class CampanaController {

    @Autowired
    private CampanaServicio campanaServicio;

    //Vista campaña registro
    @GetMapping("/registrar")
    public String campana() {
        return "campana_registro.html";
    }

    //Registrar campañas
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo,
                           @RequestParam String redaccion,
                           ModelMap modelo) {
        try {
            campanaServicio.crearCampana(titulo, redaccion);
            modelo.put("exito", "La campaña fue guardada en "
                    + "la Base de Datos");
        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());
            return "campana_registro.html";
        }
        return "index.html";

    }
}
