package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Comentario;
import com.equipotres.medioambiente.Entidades.Noticia;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    //Crear comentario
    @Transactional
    public void crearComentario(String contenido) throws MyException {
        validarCampos(contenido);

        Comentario comentario = new Comentario();

        comentario.setContenido(contenido);

        comentarioRepositorio.save(comentario);


    }


    //Modificar comentario
    @Transactional
    public void modificarComentario(String id_comentario, String contenido)
            throws MyException {

        validarCampos(contenido);

        Optional<Comentario> respuesta = comentarioRepositorio.findById(id_comentario);

        if (respuesta.isPresent()) {

            Comentario comentario = respuesta.get();
            comentario.setContenido(contenido);

            comentarioRepositorio.save(comentario);

        }


    }
//eliminar comentario
    @Transactional
    public void eliminarComentario(String id) {

        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Comentario comentario = respuesta.get();

            comentarioRepositorio.delete(comentario);

        }
    }

    //Validar campos vacios
    public void validarCampos(String contenido) throws MyException {

        if (contenido.isEmpty() || contenido == null) {
            throw new MyException("el contenido del comentario  no puede ser "
                    + "nulo o estar vacio");

        }


    }

}
