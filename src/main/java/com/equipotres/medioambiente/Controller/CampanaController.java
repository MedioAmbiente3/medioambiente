package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import com.equipotres.medioambiente.Servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/campana") //http://localhost:8080/campana
public class CampanaController
{
  @Autowired
  private CampanaServicio campanaServicio;

  @Autowired
  private ImagenServicio imagenServicio;

  //Vista campaña registro
  @GetMapping("/registrar") //http://localhost:8080/campana/registrar
  public String campana() { return "campana_registro";}

  //Registrar campañas
  @PostMapping("/registro")
  public String registro
  (
    @RequestParam String titulo,
    @RequestParam String descripcion,
    @RequestParam String desafio,
           MultipartFile archivo,
                ModelMap modelo
  )
  {
    try
    {
      campanaServicio.crearCampana(titulo, descripcion, desafio , archivo);
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
      return "campana_registro";
    }
  }

  //Lista de las campañas
  @GetMapping(value = "/lista")
  public String campana_lista_user(ModelMap modelo)
  {
    List<Campana> campanas = campanaServicio.listarCampanas(" ");
    modelo.addAttribute("campanas", campanas);
    return "campana_lista_user";
  }

}
