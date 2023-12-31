package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Enumeraciones.RolEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class AuthController {

    //Verificar si inicio session
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, HttpSession session,
                        ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos");
            session.removeAttribute("usuariosesion");
        }
        return "login"; //devolver la vista
    }


    //Inicio de session de un usuario, sea User o Admin
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPRESA')")
    @GetMapping("/inicio")
    public String inicio(HttpSession sesion) {
        Usuario logueado = (Usuario) sesion.getAttribute("usuariosesion");


        if (logueado.getRol().getNombre().equals(RolEnum.ADMIN)) {
            return "redirect:/admin/";
        }

        if (logueado.getRol().getNombre().equals(RolEnum.EMPRESA)) {
            return "empresa_lista.html";

        }
        return "usuario.html";
    }

    @GetMapping("/index")
    public String index (){
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }



}
