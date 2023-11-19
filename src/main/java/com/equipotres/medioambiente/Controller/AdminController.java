package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Empresa;
import com.equipotres.medioambiente.Entidades.Noticia;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Enumeraciones.RolEnum;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.EmpresaServicio;
import com.equipotres.medioambiente.Servicios.NoticiaServicio;
import com.equipotres.medioambiente.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private NoticiaServicio noticiaServicio;

    @Autowired
    private EmpresaServicio empresaServicio;


    //Vista dashboard inicio
    @GetMapping({"/", "", "/inicio"})
    public String index(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);

        return "admin/index";
    }

    //Listar usuarios
    @GetMapping("/usuarios")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios(RolEnum.USER);
        modelo.addAttribute("usuarios", usuarios);
        return "admin_lista_usuarios";
    }

    //Listar empresas
    @GetMapping("/empresas")
    public String listarEmpresas(ModelMap modelo) {
        List<Usuario> empresas = usuarioServicio.listarUsuarios(RolEnum.EMPRESA);
        modelo.addAttribute("empresas", empresas);
        return "admin_lista_empresas";
    }

    //Modificar Usuario de rol.User
    @GetMapping("/modificar/usuario/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("usuario", usuarioServicio.getOne(id));
        return "admin_usuario_modificar";
    }

    //Editar usuario con rol.User
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificar/usuario/{id}")
    public String modificarUsuario(@PathVariable String id,
                             @RequestParam String nombre,
                             @RequestParam String email,
                             @RequestParam String passwordA,
                             @RequestParam String passwordB,
                                   @RequestParam MultipartFile imagen,
                             ModelMap modelo ) {

        try {
            usuarioServicio.modificaUsuario(id,
                    nombre,
                    email,
                    passwordA,
                    passwordB,
                    imagen);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "redirect:/admin/usuarios";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "redirect:/admin_usuario_modificar" + id ;

        }
    }

    //Eliminar un usuario de rol.User
    @GetMapping("/eliminar/usuario/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo){
        modelo.put("usuario", usuarioServicio.getOne(id));
        return "admin_usuario_eliminar";
    }

    //Realizar la eliminacion del usuario previamente seleccionado
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/eliminar/usuario/{id}")
    public String eliminarUsuario(@PathVariable String id,
                                   ModelMap modelo ) {

        try {
            usuarioServicio.eliminarUsuarioPorId(id);
            modelo.put("exito", "Usuario eliminado correctamente!");
            return "redirect:/admin/usuarios";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/admin_usuario_eliminar" + id ;

        }
    }

    //Modificar un usuario con rol.Empresa
    @GetMapping("/modificar/empresa/{id}")
    public String modificarEmpresa(@PathVariable String id, ModelMap modelo){
        modelo.put("empresa", usuarioServicio.getOne(id));
        return "admin_empresa_modificar.html";
    }

    //Editar usuario con rol.Empresa
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificar/empresa/{id}")
    public String modificarRolEmpresa(@PathVariable String id,
                                   @RequestParam String nombre,
                                   @RequestParam String email,
                                   @RequestParam String passwordA,
                                   @RequestParam String passwordB,
                                   ModelMap modelo ) {

        try {
            empresaServicio.modificaEmpresa(id,
                    nombre,
                    email,
                    passwordA,
                    passwordB);
            modelo.put("exito", "Empresa actualizada correctamente!");
            return "redirect:/admin/empresas";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "redirect:/admin_empresa_modificar" + id ;

        }
    }











}
