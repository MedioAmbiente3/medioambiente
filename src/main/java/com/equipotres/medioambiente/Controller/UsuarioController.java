package com.equipotres.medioambiente.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UsuarioController {
    @GetMapping("/")
    public String index() {

        return "index.html";
    }
}