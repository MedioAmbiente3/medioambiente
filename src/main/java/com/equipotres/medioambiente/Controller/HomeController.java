package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Noticia;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import com.equipotres.medioambiente.Servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @Autowired
    private CampanaServicio campanaServicio;

    @GetMapping({"/", ""})
    @PreAuthorize("permitAll()")
    public String index(ModelMap modelo){

        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);

        List<Campana> campanas = campanaServicio.listarCampanas();
        modelo.addAttribute("campanas", campanas);
        return "index.html";
    }


}
