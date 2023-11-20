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
    public String registro(@RequestParam String idPublicacion,
                           @RequestParam String idUsuario,
                           ModelMap modelo) {
        try {
            Publicacion publicacion = publicacionServicio.getOne(idPublicacion);
            Usuario usuario = usuarioServicio.getOne(idUsuario);
            Voto voto = new Voto();
            voto.setPublicacion(publicacion);
            voto.setUsuario(usuario);
            voto.setFechaCreacion(LocalDate.now());
            votoServicio.crearVoto(voto);

            return "redirect:../publicacion/lista";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../publicacion/lista";
        }
    }
}
