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

    //Listar las noticias
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "noticia_lista.html";
    }


}