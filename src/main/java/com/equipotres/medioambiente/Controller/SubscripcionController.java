package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Subscripcion;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import com.equipotres.medioambiente.Servicios.SubscripcionServicio;
import com.equipotres.medioambiente.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/subscripcion")
public class SubscripcionController {

    @Autowired
    private CampanaServicio campanaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private SubscripcionServicio subscripcionServicio;


    @GetMapping("/registrar/{campanaid}")
    public String registrar(ModelMap modelo,
                            @PathVariable String campanaid) {
        Campana campana = campanaServicio.getOne(campanaid);
        modelo.addAttribute("campana", campana);


        return "subscripcion_registro.html";
    }


    @PostMapping("/registro")
    public String registro(
            @RequestParam String campanaid,
            @RequestParam String usuarioid,
            ModelMap modelo)
    {
        try
        {
            Campana campana = campanaServicio.getOne(campanaid);
            Usuario usuario = usuarioServicio.getOne(usuarioid);

            Subscripcion subscripcion = new Subscripcion();
            subscripcion.setCampana(campana);
            subscripcion.setUsuario(usuario);
            subscripcion.setFechaCreacion(LocalDate.now());
            subscripcionServicio.crearSubscripcion(subscripcion);

            modelo.put("exito", "Se ha registrado la subscripción correctamente");
            return String.format("redirect:/campana/detalle/%s", campanaid);
        }
        catch (MyException ex)
        {
            modelo.put("error", ex.getMessage());
            return "redirect:../campana/lista";
        }
    }
}
