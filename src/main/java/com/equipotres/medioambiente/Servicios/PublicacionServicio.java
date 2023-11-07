package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Entidades.Subscripcion;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.PublicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private SubscripcionServicio subscripcionServicio;

    //Método crear Publicacion
    @Transactional
    public void crearPublicacion(
                   String titulo,
                   String contenido,
            MultipartFile archivo,
             Subscripcion subscripcion)
    throws MyException {
        //Validamos que los campos no esten vacios
        validar(titulo, contenido);
        //Crear un objeto de la clase Publicación
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(titulo);
        publicacion.setContenido(contenido);
        Imagen imagen = imagenServicio.guardaImagen(archivo);
        publicacion.setImagen(imagen);
        publicacion.setSubscripcion(subscripcion);
        //Guardamos la publicacion
        publicacionRepositorio.save(publicacion);
    }

    //Consultar todas las publicaciones
    public List<Publicacion> listarPublicaciones()
    {
        List<Publicacion> publicaciones = publicacionRepositorio.findAll();
        return publicaciones;
    }

    public List<Publicacion> publicacionesDeCampana(Campana campana)
    {
        List<Subscripcion> subscripciones = subscripcionServicio.listarSubscripciones();

        List<Publicacion>  publicaciones = new ArrayList<>();
        for (Subscripcion sub :subscripciones) {
            if( sub.getCampana().getId().equals( campana.getId() ) )
            {
                String id_subscripcion = sub.getId();
                publicaciones.add(publicacionRepositorio.findByIdSubscripcion(id_subscripcion));
            }
        }
        return publicaciones;
    }

    //Método eliminar Publicación
    @Transactional
    public void eliminarPublicacion(String id)
            throws MyException {
        Optional<Publicacion> desafio = publicacionRepositorio.findById(id);
        if (desafio.isPresent())
        {
            publicacionRepositorio.delete(desafio.get());
        }
        else
        {
            throw new MyException("La publicacion con el id " + id + " no existe.");
        }
    }

    //Método consultar Publicación



    //Validar campos vacios
    private void validar(
            String titulo,
            String contenido)
            throws MyException
    {
        if (titulo.isEmpty() || titulo == null)
        {
            throw new MyException("el título de su desfío no puede ser "
                    + "nulo o estar vacío");
        }
        if (contenido.isEmpty() ||contenido == null)
        {
            throw new MyException("El contenido de su desafío no puede ser "
                    + "nulo o estar vacío");
        }
    }
}
