package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Entidades.Voto;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.PublicacionServicio;
import com.equipotres.medioambiente.Servicios.UsuarioServicio;
import com.equipotres.medioambiente.Servicios.VotoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/voto")
public class VotoController {

    @Autowired
    private PublicacionServicio publicacionServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private VotoServicio votoServicio;

    @GetMapping("/registrar/{publicacionid}")
    public String registrar (ModelMap modelo,
                             @PathVariable String idPublicacion) {
        Publicacion publicacion = publicacionServicio.getOne(idPublicacion);
        modelo.addAttribute("publicacion", publicacion);
        return "voto_registro";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String idUsuario,
                           @RequestParam String idPublicacion,
                           ModelMap modelo) {
    try
    {
       if(votoServicio.obtenerIdVoto(idUsuario,idPublicacion).isEmpty())
       {
         Usuario usuario = usuarioServicio.getOne(idUsuario);
         Publicacion publicacion = publicacionServicio.getOne(idPublicacion);
         Voto voto = new Voto();
         voto.setUsuario(usuario);
         voto.setPublicacion(publicacion);
         voto.setFechaCreacion(LocalDate.now());
         votoServicio.crearVoto(voto);
       }
       else
       {
         throw new MyException("ya el voto se había efectuado");
       }
       return "publicacion_lista";
    }
    catch (MyException ex)
    {
       modelo.put("error", ex.getMessage());
       return "publicacion_lista";
    }
  }
}
