package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.ComentarioServicio;
import com.equipotres.medioambiente.Servicios.EmpresaServicio;
import com.equipotres.medioambiente.Servicios.GanadorServicio;
import com.equipotres.medioambiente.Servicios.VotoServicio;
import com.equipotres.medioambiente.Entidades.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {


    @Autowired
    private EmpresaServicio empresaServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @Autowired
    private VotoServicio votoServicio;

    @Autowired
    private GanadorServicio ganadorServicio;

    //Vista empresa registrar
    @GetMapping("/registrar")
    public String registrar() {
        return "empresa_registrar";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,
                           @RequestParam String email,
                           @RequestParam String passwordA,
                           @RequestParam String passwordB,
                           ModelMap modelo) {
        try {
            // Convierte el String a Rol
            empresaServicio.crearEmpresa(
                    nombre,
                    email,
                    passwordA,
                    passwordB
                    );
            modelo.put("exito", "Se ha registrado la empresa correctamente");
            return "/admin/index";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("passwordA", passwordA);
            modelo.put("passwordB", passwordB);
            return "empresa_registrar";
        }
    }

    //Lista de publicaciones de campaña auspiciada
    @GetMapping("lista/publicaciones/{campanaid}")
    public String lista(@PathVariable String campanaid, ModelMap modelo) {
        modelo.addAttribute("campanaid", campanaid);
        List<Publicacion> publicacionesTop = votoServicio.
                          publicacionesMasVotadasDeCampana(campanaid);
        publicacionesTop = publicacionesTop.
                                 subList(0,Math.min(publicacionesTop.size(),10));
        modelo.addAttribute("publicacionesTop", publicacionesTop);
        modelo.addAttribute("votoServicio", votoServicio);
        modelo.addAttribute("comentarioServicio", comentarioServicio);
        return "empresa_lista_publicaciones.html";
    }

    //Lista de usuarios de campaña auspiciada
    @GetMapping("lista/usuarios_campana/{campanaid}")
    public String listausuarios(@PathVariable String campanaid, ModelMap modelo) {
        List<Usuario> usuariosTop = votoServicio.usuariosMasVotadosDeCampana(campanaid);
        usuariosTop = usuariosTop.subList(0,Math.min(usuariosTop.size(), 10));
        modelo.addAttribute("campanaid", campanaid);
        modelo.addAttribute("usuariosTop", usuariosTop);
        modelo.addAttribute("votoServicio", votoServicio);
        modelo.addAttribute("ganadorServicio", ganadorServicio);
        return "empresa_lista_usuarios.html";
    }
}
