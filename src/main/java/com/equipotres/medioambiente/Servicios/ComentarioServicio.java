package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Comentario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    //Crear comentario
    public void crearComentario(String contenido) throws MyException {
        validarCampos(contenido);

        Comentario comentario = new Comentario();

        comentario.setContenido(contenido);

        comentarioRepositorio.save(comentario);


    }


    //Modificar comentario
    public void modificarComentario(String id_comentario, String contenido)
            throws MyException {

        validarCampos(contenido);

        Optional<Comentario> respuesta = comentarioRepositorio.findById(id_comentario);

        if (respuesta.isPresent()) {

            Comentario comentario = new Comentario();
            comentario.setContenido(contenido);

            comentarioRepositorio.save(comentario);

        }


    }


    //Consultar comentarios

    //Validar campos vacios
    public void validarCampos(String contenido) throws MyException {

        if (contenido.isEmpty() || contenido == null) {
            throw new MyException("el contenido del comentario  no puede ser "
                    + "nulo o estar vacio");

        }


    }

}
