package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Entidades.Comentario;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired
    private PublicacionServicio publicacionServicio;

    @Autowired
    private CampanaServicio campanaServicio;

    @Autowired
    private VotoServicio votoServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar/{campanaid}")
    public String registrar(@PathVariable String campanaid, ModelMap modelo){
        Campana campana = campanaServicio.getOne(campanaid);
        modelo.addAttribute("campana", campana);
        return "publicacion_registrar.html";
    }

    //Post de registro de la publicación
    @PostMapping("/registro")
    public String registro(
            @RequestParam String titulo,
            @RequestParam String contenido,
            MultipartFile archivo,
            @RequestParam String campanaid,
            @RequestParam String usuarioid,
            ModelMap modelo){
        try
        {
            publicacionServicio.crearPublicacion(
                    titulo,
                    contenido,
                    archivo,
                    usuarioid,
                    campanaid);
            modelo.put("exito", "Se ha publicado el desafio correctamente");
            return String.format("redirect:/publicacion/lista/%s", campanaid);
        }
        catch (MyException ex)
        {
            modelo.put(       "error",ex.getMessage());
            modelo.put(      "titulo",titulo);
            modelo.put(   "contenido",contenido);
            modelo.put(     "archivo", archivo);
            modelo.put("idcampana", campanaid);
            modelo.put("idusuario", usuarioid);
            return "publicacion_registrar.html";
        }

    }

    //Lista de publicaciones
    @GetMapping("/lista/{campanaid}")
    public String lista(@PathVariable String campanaid, ModelMap modelo) {
        modelo.addAttribute("campanaid", campanaid);
        List<Publicacion> publicacionesDeCampana = publicacionServicio.
                          listarPublicacionPorCampana(campanaid);
        modelo.addAttribute("publicacionesDeCampana", publicacionesDeCampana);
        modelo.addAttribute("votoServicio", votoServicio);
        modelo.addAttribute("comentarioServicio", comentarioServicio);
        return "publicacion_lista.html";
    }

} 
