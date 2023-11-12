package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Entidades.Subscripcion;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.PublicacionRepositorio;
import com.equipotres.medioambiente.Repositorios.SubscripcionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicacionServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private SubscripcionServicio subscripcionServicio;

    //Método crear Publicacion
    //Método crear Publicacion
    @Transactional
    public void crearPublicacion(
            String titulo,
            String contenido,
            MultipartFile archivo,
            String idUsuario,
            String idCampana)
            throws MyException
    {
        //Validamos que los campos no esten vacios
        validar(titulo, contenido);
        //Crear un objeto de la clase Publicación
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(titulo);
        publicacion.setContenido(contenido);
        Imagen imagen = imagenServicio.guardaImagen(archivo);
        publicacion.setImagen(imagen);
        String idSubscripcion = subscripcionServicio.
               obtenerIdSubscripcion(idUsuario, idCampana);
        Subscripcion subscripcion = subscripcionServicio.getOne(idSubscripcion);
        publicacion.setSubscripcion(subscripcion);
        //Guardamos la publicacion
        publicacionRepositorio.save(publicacion);
    }

    //Método modificar Publicación


    //Método eliminar Publicación

    //Método consultar Publicación

    public List<Publicacion> listarPublicaciones()
    {
        List<Publicacion> listaDePublicaciones = publicacionRepositorio.findAll();
        return listaDePublicaciones;
    }
    public List<Publicacion> listarPublicacionesDeCampana(String idCampana)
    {
        List<Publicacion> publicacionesDeCampana = new ArrayList<>();
        for(Publicacion pub: listarPublicaciones())
        {
          if(pub.getSubscripcion().getCampana().getId().equals(idCampana))
          {
            publicacionesDeCampana.add(pub);
          }
        }
        return publicacionesDeCampana;
    }
    public String obtenerIdPublicacion(String idUsuario, String idCampana)
    {
        String idPublicacion = new String();
        for (Publicacion pub:listarPublicacionesDeCampana(idCampana))
        {
            if(pub.getSubscripcion().getUsuario().getId().equals(idUsuario))
            {
                idPublicacion = pub.getId();
            }
        }
        return idPublicacion;
    }
    //Obtener la publicación dado el id
    public Publicacion getOne(String id){
        return publicacionRepositorio.getOne(id);

    }


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
