 package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import com.equipotres.medioambiente.Entidades.Campana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/campana") //http://localhost:8080/campana
public class  CampanaController {

    @Autowired
    private CampanaServicio campanaServicio;

    //Vista campaña registro
    @GetMapping("/registrar") //http://localhost:8080/campana/registrar
    public String campana() {
        return "campana_registro.html";
    }

    @GetMapping("/prueba")
    public String index() {
        return "index.html";
    }

    //Registrar campañas
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo,
                           @RequestParam String descripcion,
                           ModelMap modelo,
                           MultipartFile archivo) {
        try {
            campanaServicio.crearCampana(titulo, descripcion, archivo);
            modelo.put("exito", "Se ha registrado correctamente la campaña");
            return "redirect:/campana/listar";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("descripcion", descripcion);
            modelo.put("archivo", archivo);
            return "campana_registro.html";
        }
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Campana> listado = campanaServicio.listarCampanas();
        modelo.put("listado", listado);
        return "campana_lista.html";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable String id, ModelMap modelo) {
        try {
            Campana campana = campanaServicio.findCampanaPorId(id);
            modelo.put("campana", campana);
            return "campana_detalle.html";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "index.html";
        }
    }
}
