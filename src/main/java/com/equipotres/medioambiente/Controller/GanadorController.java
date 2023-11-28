package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Empresa;
import com.equipotres.medioambiente.Entidades.Subscripcion;
import com.equipotres.medioambiente.Entidades.Ganador;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.EmpresaRepositorio;
import com.equipotres.medioambiente.Servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/ganador")
public class GanadorController {

    @Autowired
    private CampanaServicio campanaServicio;

    @Autowired
    private EmpresaServicio empresaServicio;

    @Autowired
    private SubscripcionServicio subscripcionServicio;

    @Autowired
    private GanadorServicio ganadorServicio;

    @Autowired
    private EmpresaRepositorio empresaRepositorio;


    @GetMapping("/registrar/{campanaid}")
    public String registrar(@PathVariable String campanaid, ModelMap modelo){
        Campana campana = campanaServicio.getOne(campanaid);
        modelo.addAttribute("campana", campana);
        return "ganador_registrar.html";
    }

    //Post de registro de la publicación
    @PostMapping("/registro")
    public String registro(
            @RequestParam String empresaid,
            @RequestParam String campanaid,
            @RequestParam String usuarioid,
            ModelMap modelo){
        try
        {   
            Ganador ganador = new Ganador();

            empresaid = empresaRepositorio.selectEmpresaIdByUsuarioId(empresaid);
            Empresa empresa = empresaServicio.getOne(empresaid);
            ganador.setEmpresa(empresa);

            String subscripcionid = subscripcionServicio.
                                    obtenerIdSubscripcion(usuarioid,campanaid);
            Subscripcion subscripcion = subscripcionServicio.getOne(subscripcionid);
            ganador.setSubscripcion(subscripcion);
            
            ganador.setFechaCreacion(LocalDate.now());
            
            ganadorServicio.crearGanador(ganador);
            modelo.put("exito", "Se ha asignado el ganador correctamente");
            return String.format("redirect:/empresa/lista/usuarios_campana/{campanaid}%s", campanaid);
        }
        catch (MyException ex)
        {
            modelo.put(    "error",ex.getMessage());
            modelo.put("empresaid", empresaid);
            modelo.put("campanaid", campanaid);
            modelo.put("usuarioid", usuarioid);
            return String.format("redirect:/empresa/lista/usuarios_campana/{campanaid}%s", campanaid);
        }

    }

} 
