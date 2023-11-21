package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Entidades.Comentario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.PublicacionServicio;
import com.equipotres.medioambiente.Servicios.UsuarioServicio;
import com.equipotres.medioambiente.Servicios.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private PublicacionServicio publicacionServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/registrar/{publicacionid}")
    public String registrar (ModelMap modelo,
                             @PathVariable String idPublicacion)
    {
        Publicacion publicacion = publicacionServicio.getOne(idPublicacion);
        modelo.addAttribute("publicacion", publicacion);
        return "comentario_registro";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String contenido,
                           @RequestParam String idPublicacion,
                           @RequestParam String idUsuario,
                           @RequestParam String campanaid,
                           ModelMap modelo) {
        try 
        {
          Comentario comentario = new Comentario();
          comentario.setContenido(contenido);
          Publicacion publicacion = publicacionServicio.getOne(idPublicacion);
          comentario.setPublicacion(publicacion);
          Usuario usuario = usuarioServicio.getOne(idUsuario);
          comentario.setUsuario(usuario);
          comentario.setFechaCreacion(LocalDate.now());
          comentarioServicio.crearComentario(comentario);

          return String.format("redirect:/publicacion/lista/%s", campanaid);

        } 
        catch (MyException ex) 
        {
          modelo.put("error", ex.getMessage());
          return String.format("redirect:/publicacion/lista/%s", campanaid);
        }
    }
}
