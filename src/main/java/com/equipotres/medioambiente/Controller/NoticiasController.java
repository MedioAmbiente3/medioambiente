package com.equipotres.medioambiente.Controller;
import com.equipotres.medioambiente.Entidades.Noticia;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import java.util.List;

@Controller
@RequestMapping("/noticia")
public class NoticiasController {
    @Autowired
    private NoticiaServicio noticiaServicio;

    //Vista registrar noticias
    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "noticias_registrar.html";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam String titulo,
            @RequestParam String contenido,
            MultipartFile archivo,
            ModelMap modelo){
        try {
            noticiaServicio.crearNoticia(titulo, contenido, archivo);
            modelo.put("exito", "La Noticia fue guardada en la Base de Datos");
            return "admin/index";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "noticias_registrar.html";
        }

    }

    //Listar las noticias
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "noticias_lista.html";
    }

    //Vista registro noticias
    @PostMapping("/registro")
    public String registrar(@RequestParam String titulo,
                            @RequestParam String contenido,
                            MultipartFile archivo,
                            ModelMap modelo
                            ) {
        try {
            // Convierte el String a Rol
            noticiaServicio.crearNoticia(titulo, contenido, archivo);
            modelo.put("exito", "Se ha creado la noticia correctamente");
            return "noticias_registrar.html";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("contenido", contenido);
            modelo.put("archivo", archivo);
            return "noticias_registrar.html";
        }
        //noticiaServicio.crearNoticia();
        //return "redirect:/noticia/lista";
    }
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "noticia_lista.html";
    }
}