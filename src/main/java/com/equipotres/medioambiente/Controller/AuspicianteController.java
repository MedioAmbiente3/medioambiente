package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Empresa;
import com.equipotres.medioambiente.Entidades.Auspiciante;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.EmpresaRepositorio;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import com.equipotres.medioambiente.Servicios.AuspicianteServicio;
import com.equipotres.medioambiente.Servicios.EmpresaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auspiciante")
public class AuspicianteController {

    @Autowired
    private CampanaServicio campanaServicio;

    @Autowired
    private EmpresaServicio empresaServicio;

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private AuspicianteServicio auspicianteServicio;


    @GetMapping("/registrar/{campanaid}")
    public String registrar(ModelMap modelo,
                            @PathVariable String campanaid) {
        Campana campana = campanaServicio.getOne(campanaid);
        modelo.addAttribute("campana", campana);

        return "auspiciante_registro.html";
    }


    @PostMapping("/registro")
    public String registro(
            @RequestParam String campanaid,
            @RequestParam String empresaid,
            ModelMap modelo)
    {
        try
        {
            Campana campana = campanaServicio.getOne(campanaid);
            
            empresaid = empresaRepositorio.selectEmpresaIdByUsuarioId(empresaid);
            Empresa empresa = empresaServicio.getOne(empresaid);

            Auspiciante auspiciante = new Auspiciante();
            auspiciante.setCampana(campana);
            auspiciante.setEmpresa(empresa);
            auspicianteServicio.crearAuspiciante(auspiciante);

            modelo.put("exito", "Se ha registrado la empresa auspiciante correctamente");
            return "redirect:../campana/lista";
        }
        catch (MyException ex)
        {
            modelo.put("error", ex.getMessage());
            return "redirect:../campana/lista";
        }
    }
}
