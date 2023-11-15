package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.EmpresaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {


    @Autowired
    private EmpresaServicio empresaServicio;


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



}
