package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Entidades.Noticia;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.NoticiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    //Crear una noticia
    public void crearNoticia(String titulo, String contenido, MultipartFile imagen) throws MyException {

        //Validamos los campos vacios
        validar(titulo, contenido);

        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setContenido(contenido);
        noticia.setEstado(true);
        Imagen foto = imagenServicio.guardaImagen(imagen);
        noticia.setImagen(foto);

        //Guardamos la noticia
        noticiaRepositorio.save(noticia);

    }

    //Modificar una noticia
    @Transactional
    public void modificarNoticia(String idnoticia, String titulo,
                                 String contenido, MultipartFile imagen) throws MyException {

        //Validar campos vacios
        validar(titulo, contenido);

        // en caso de que el id de la Evidencia este mal digitado o que no se
        // encuentre, se debe de usar un optional
        Optional<Noticia> respuesta = noticiaRepositorio.findById(idnoticia);

        //comprobar de que si exista un dato con el mismo id
        if (respuesta.isPresent()) {
            //instanciamos un objeto de tipo noticia
            Noticia noticia = respuesta.get();

            //seteamos el nombre, porque el id no hay como modificarle
            noticia.setTitulo(titulo);

            noticia.setContenido(contenido);
            String idFoto = null;
            if (noticia.getImagen() != null) {
                idFoto = noticia.getImagen().getId();

            }

            Imagen foto = imagenServicio.actualizar(imagen, idFoto);
            noticia.setImagen(foto);
            //guardamos
            noticiaRepositorio.save(noticia);
        }


    }

    //Captura el id del Usuario
    public Noticia getOne(String id) {
        return noticiaRepositorio.getOne(id);
    }

    //Consultar noticias
    //Listar todos los usuarios
    @Transactional
    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList();

        noticias = noticiaRepositorio.findAll();
        //Metodo propio del jpaRepo es traer todos los datos de la tabla con el ".findAll()"
        return noticias;
    }

    //Campos vacios
    private void validar(String titulo, String contenido) throws MyException {

        if (titulo == null) {
            throw new MyException("el titulo de la noticia no puede ser nulo");
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MyException("el titulo no puede estar vacio");
        }
        if (contenido.isEmpty() || contenido == null) {
            throw new MyException("el contenido de la noticia no puede ser nulo");
        }

    }
}
