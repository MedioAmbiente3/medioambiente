package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.ComentarioServicio;
import com.equipotres.medioambiente.Servicios.EmpresaServicio;
import com.equipotres.medioambiente.Servicios.PublicacionServicio;
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
    private PublicacionServicio publicacionServicio;

    @Autowired
    private VotoServicio votoServicio;


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


    //Lista de publicaciones de empresa
    @GetMapping("lista/publicaciones/{campanaid}")
    public String lista(@PathVariable String campanaid, ModelMap modelo) {
        modelo.addAttribute("campanaid", campanaid);
        List<Publicacion> publicacionesDeCampana = publicacionServicio.
                          listarPublicacionPorCampana(campanaid);
        modelo.addAttribute("publicacionesDeCampana", publicacionesDeCampana);
        modelo.addAttribute("votoServicio", votoServicio);
        modelo.addAttribute("comentarioServicio", comentarioServicio);
        return "empresa_lista_publicaciones.html";
    }

    //Lista de publicaciones de empresa
    @GetMapping("lista/usuarios_publicacion/{publicacionid}")
    public String listausuarios(@PathVariable String publicacionid, ModelMap modelo) {
        Publicacion publicacion = publicacionServicio.getOne(publicacionid);
        String campanaid = publicacion.getSubscripcion().getCampana().getId();
        modelo.addAttribute("campanaid", campanaid);
        List<Usuario> usuariosQueVotaron = votoServicio.
                          obtenerUsuariosQueVotaron(publicacionid);
        modelo.addAttribute("usuariosQueVotaron", usuariosQueVotaron);
        modelo.addAttribute("votoServicio", votoServicio);
        return "empresa_lista_usuarios";
    }

}
