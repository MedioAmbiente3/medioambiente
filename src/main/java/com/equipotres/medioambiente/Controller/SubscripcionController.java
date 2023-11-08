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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
  
@Controller
@RequestMapping("/subscripcion")
public class SubscripcionController {

 @Autowired
 private CampanaServicio campanaServicio;

 @Autowired
 private UsuarioServicio usuarioServicio;

 @Autowired
 private SubscripcionServicio subscripcionServicio;
 @GetMapping("/registrar")
 public String registrar(ModelMap modelo)
 {
   List<Campana> campanas = campanaServicio.listarCampanas("a");//Campañas activas
   modelo.addAttribute("campanas", campanas);
   return "prueba";
   //return "subscripcion_registro";
 }

 //Registrar subscripcion
 @PostMapping("/registro")
 public String registro
 (
   @RequestParam String id_campana,
   @RequestParam String id_usuario,
               ModelMap modelo
 )
 {
  try
  {
    Campana campana = campanaServicio.getOne(id_campana);
    Usuario usuario = usuarioServicio.getOne(id_usuario);
    Subscripcion subscripcion = new Subscripcion();
    subscripcion.setCampana(campana);
    subscripcion.setUsuario(usuario);
    subscripcion.setFechaCreacion(LocalDate.now());
    subscripcionServicio.crearSubscripcion(subscripcion);
    modelo.put("exito", "Se ha registrado la subscripción correctamente");
    return "admin/index";
  }
  catch (MyException ex)
  { modelo.put("error", ex.getMessage());return "subscripcion_registro"; }

 }//registro

 //Listar las subscripciones
 @GetMapping("/lista")
 public String listar(ModelMap modelo)
 {
    List<Subscripcion> subscripciones = subscripcionServicio.listarSubscripciones();
    List<Campana> campanas = new ArrayList<>();
    for (Subscripcion sub: subscripciones) { campanas.add(sub.getCampana()); }
    modelo.addAttribute("campanas", campanas);
    return "subscripcion_lista";
 }

}//SubscripcionController
