package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Rol;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Enumeraciones.RolEnum;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    //Ruta registrar usuario
    @GetMapping("/registrar")
    public String registrar() {
        return "registro_usuarios.html"; //devolver la vista
    }

    //Verificación comprobación de los datos recibidos en el formulario registro
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,
                           @RequestParam String email,
                           @RequestParam String passwordA,
                           @RequestParam String passwordB,

                           ModelMap modelo,
                           MultipartFile archivo) {
        try {
            // Convierte el String a Rol
            usuarioServicio.crearUsuario(nombre, email, passwordA, passwordB , archivo);
            modelo.put("exito", "Se ha registrado el usuario correctamente");
            return "/inicio";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("password", passwordA);
            modelo.put("passwordB", passwordB);

            modelo.put("archivo", archivo);
            return "/register";
        }
    }

@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPRESA')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPRESA')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre,
                             @RequestParam String email,
                             @RequestParam String passwordA, @RequestParam String passwordB,
                             ModelMap modelo, MultipartFile imagen) {


        try {
            usuarioServicio.modificaUsuario(id, nombre, email, passwordA, passwordB, imagen);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "inicio.html";

        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario_modificar.html";

        }
    }



}
