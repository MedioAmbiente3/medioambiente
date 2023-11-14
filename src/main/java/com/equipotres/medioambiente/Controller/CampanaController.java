package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import com.equipotres.medioambiente.Servicios.ImagenServicio;
import com.equipotres.medioambiente.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/campana") //http://localhost:8080/campana
public class CampanaController {

    @Autowired
    private CampanaServicio campanaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    //Vista campaña registro
    @GetMapping("/registrar") //http://localhost:8080/campana/registrar
    public String registrar() {
        return "campana_registro.html";
    }


    //Registrar campañas
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo,
                           @RequestParam String descripcion,
                           @RequestParam String desafio,
                           MultipartFile archivo,
                           ModelMap modelo) {

        try {
            // Convierte el String a Rol
            campanaServicio.crearCampana(titulo, descripcion, desafio , archivo);
            modelo.put("exito", "Se ha registrado la Campaña correctamente");
            return "admin/index";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("descripcion", descripcion);
            modelo.put("desafio", desafio);
            modelo.put("archivo", archivo);

            return "campana_registro.html";
        }

    }

    //Lista de las campañas
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Campana> campanas = campanaServicio.listarCampanas();
        modelo.addAttribute("campanas", campanas);
        return "campana_lista_user.html";
    }


    //Modificar campañas
    //Traer el id señalado a modificar
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("campana", campanaServicio.getOne(id));

        return "campana_modificar.html";
    }

    //Realizar la modificación al id previamente seleccionado
    @PostMapping("/modificar/{id}")
    public String modificar(
                            @PathVariable String id,
                            @RequestParam  String titulo,
                            @RequestParam String descripcion,
                            @RequestParam String desafio,
                            @RequestParam Boolean estado,
                            MultipartFile archivo,
                            ModelMap modelo) {
        try {
            campanaServicio.modificarCampana(id, titulo,descripcion, desafio, estado, archivo );
            modelo.put("exito", "Se ha modificado la campaña");
            return "redirect:../lista";
        } catch (MyException ex) {
            modelo.put("error", "No se ha realizado ningún cambio");
            return "campana_modificar.html";
        }

    }





}
