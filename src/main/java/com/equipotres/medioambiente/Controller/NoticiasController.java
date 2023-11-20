package com.equipotres.medioambiente.Controller;
import com.equipotres.medioambiente.Entidades.Noticia;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/noticia")
public class NoticiasController {

    //Vista registrar noticias
    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "noticia_registrar.html";
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
            return "noticia_registrar.html";
        }

    }

    //Modificar Noticia
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("noticia", noticiaServicio.getOne(id));
        return "noticia_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarNoticia(
            @RequestParam String id,
            @RequestParam String titulo,
            @RequestParam String contenido,
            @RequestParam MultipartFile archivo,
            ModelMap modelo,
            RedirectAttributes msg) {
        try
        {
            noticiaServicio.modificarNoticia(id, titulo, contenido, archivo);
            modelo.put("exito", "Se ha modificado la noticia");
            return "redirect:../lista";
        }
        catch (MyException ex)
        {
            msg.addFlashAttribute("error", ex.getMessage());

            return "redirect:/noticia/modificar/" + id;
        }
    }

    //Listar las noticias
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "noticia_lista.html";
    }


}