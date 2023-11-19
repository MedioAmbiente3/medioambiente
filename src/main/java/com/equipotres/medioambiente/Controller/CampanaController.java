package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import com.equipotres.medioambiente.Servicios.ImagenServicio;
import com.equipotres.medioambiente.Servicios.SubscripcionServicio;
import com.equipotres.medioambiente.Servicios.PublicacionServicio;
import com.equipotres.medioambiente.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/campana") //http://localhost:8080/campana
public class CampanaController {

    @Autowired
    private CampanaServicio campanaServicio;

    @Autowired
    private SubscripcionServicio subscripcionServicio;

    @Autowired
    private PublicacionServicio publicacionServicio;

    //Vista campaña registro
    @GetMapping("/registrar") //http://localhost:8080/campana/registrar
    public String registrar() {
        return "campana_registrar.html";
    }

   //Registrar campañas
    @PostMapping("/registro")
    public String registro(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String desafio,
            @RequestParam MultipartFile archivo,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFinal,
            ModelMap modelo)
                       
    {
        try
        {
            campanaServicio.crearCampana(
                    titulo,
                    descripcion,
                    desafio,
                    archivo,
                    fechaFinal);
            modelo.put("exito", "Se ha registrado la Campaña correctamente");
            return "admin/index";
        }
        catch (MyException ex)
        {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("descripcion", descripcion);
            modelo.put("desafio", desafio);
            modelo.put("archivo", archivo);
            modelo.put("fechaFinal",fechaFinal);
            return "campana_registrar.html";
        }
    }

    //Lista de las campañas
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Campana> campanas = campanaServicio.listarCampanas();
        modelo.addAttribute("campanas", campanas);
        // Agrega al modelo la referencia al servicio
        modelo.addAttribute("subscripcionServicio", subscripcionServicio);
        return "campana_lista_user.html";
    }

    //Listar campañas en el admin
        @GetMapping("/lista/admin")
    public String listaAdmin(ModelMap modelo) {
        List<Campana> campanas = campanaServicio.listarCampanas();
        modelo.addAttribute("campanas", campanas);
        return "campana_lista_admin.html";
    }


    //Modificar campañas
    //Traer el id señalado a modificar
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombreImagenAnterior,
                            String extensionImagenAnterior, ModelMap modelo) {
        modelo.put("campana", campanaServicio.getOne(id));
        modelo.addAttribute("nombreImagen", nombreImagenAnterior);
        modelo.addAttribute("extensionImagen", extensionImagenAnterior);

        return "campana_modificar.html";
    }

    //Realizar la modificación al id previamente seleccionado
    @PostMapping("/modificar/{id}")
    public String modificar(
            @RequestParam String id,
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String desafio,
            @RequestParam Boolean estado,
            @RequestParam MultipartFile archivo,
            ModelMap modelo,
            RedirectAttributes msg) {
        try
        {
            campanaServicio.modificarCampana(id, titulo, descripcion,
                    desafio, estado, archivo);
            modelo.put("exito", "Se ha modificado la campaña");
            return "redirect:../lista/admin/";
        }
        catch (MyException ex)
        {
            msg.addFlashAttribute("error", ex.getMessage());
            //modelo.put("error", "No se ha realizado ningún cambio");

            return "redirect:/campana/modificar/" + id;
        }
    }
    
    //Detalle de campaña
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable String id, ModelMap modelo){
        modelo.addAttribute("campana", campanaServicio.getOne(id));
        modelo.addAttribute("subscripcionServicio", subscripcionServicio);
        modelo.addAttribute("publicacionServicio", publicacionServicio);
        return "campana_detalle.html";}

    //Traer el id señalado para eliminar campaña
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        modelo.put("campana", campanaServicio.getOne(id));
        return "campana_eliminar.html";
    }

    //Realizar la eliminacion de la campaña previamente seleccionado
    @PostMapping("/eliminar/{id}")
    public String eliminado(@PathVariable String id, ModelMap modelo) {
        try {
            campanaServicio.eliminarCampana(id);
            modelo.put("exito", "Se ha eliminado la campaña seleccionada");
            return "redirect:../lista/admin/";
        } catch (MyException ex) {
            modelo.put("error", "No se ha realizado ningún cambio");
            return "campana_eliminar.html";
        }

    }




}
