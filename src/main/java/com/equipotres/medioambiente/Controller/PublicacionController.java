package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Entidades.Subscripcion;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.PublicacionServicio;
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
@RequestMapping("/publicacion") //http://localhost:8080/publicacion
public class PublicacionController {

    @Autowired
    private PublicacionServicio publicacionServicio;

    //Vista publicar desafio
    @GetMapping("/publicar") //http://localhost:8080/publicacion/publicar
    public String publicar()
    {
        return "publicar_desafio.html";
    }

    //nueva publicacion
    @PostMapping("/desafio")
    public String desafio(
            @RequestParam String titulo,
            @RequestParam String contenido,
            MultipartFile archivo,
            @RequestParam Subscripcion subscripcion,
            ModelMap modelo)
    {
        try
        {
            publicacionServicio.crearPublicacion(titulo, contenido , archivo, subscripcion);
            modelo.put("exito", "Se ha publicado el desafio correctamente");
            return "index";
        }
        catch (MyException ex)
           {
            modelo.put(       "error",ex.getMessage());
            modelo.put(      "titulo",titulo);
            modelo.put(   "contenido",contenido);
            modelo.put(     "archivo",archivo);
            modelo.put("subscripcion",subscripcion);
            return "publicar_desafio.html";
           }
    }

    // Lista de las publicaciones
    @GetMapping(value = "/lista")
    public String publicacion_lista_campana( String id_campana, ModelMap modelo )
    {
        List<Publicacion> publicaciones = publicacionServicio.publicacionesDeCampana( id_campana );
        modelo.addAttribute(publicaciones);
        return "publicacion_lista_campana.html";
    }
}
