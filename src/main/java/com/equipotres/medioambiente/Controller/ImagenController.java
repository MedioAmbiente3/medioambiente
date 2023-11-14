package com.equipotres.medioambiente.Controller;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Entidades.Noticia;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Servicios.CampanaServicio;
import com.equipotres.medioambiente.Servicios.NoticiaServicio;
import com.equipotres.medioambiente.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenController {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    CampanaServicio campanaServicio;

    @Autowired
    NoticiaServicio noticiaServicio;

    //Imagen de perfil del usuario
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);

        byte[] imagen = usuario.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    //Imagen de una campaña
    @GetMapping("/campana/{id}")
    public ResponseEntity<byte[]> imagenCampana(@PathVariable String id) {
        Campana campana = campanaServicio.getOne(id);

        byte[] imagen = campana.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    //Imagen de una noticia
    @GetMapping("/noticia/{id}")
    public ResponseEntity<byte[]> imagenNoticia(@PathVariable String id) {
        Noticia noticia = noticiaServicio.getOne(id);

        byte[] imagen = noticia.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

}
