package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.CampanaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CampanaServicio {

    @Autowired
    private CampanaRepositorio campanaRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    //Crear campaña
    @Transactional
    public void crearCampana(
            String titulo,
            String descripcion,
            String desafio,
            MultipartFile archivo)
            throws MyException {
        //Validamos que los campos no esten vacios
            validar(titulo, desafio,descripcion);
        //Crear un objeto de la clase Campana
            Campana campana = new Campana();
            campana.setTitulo(titulo);
            campana.setDescripcion(descripcion);
            campana.setDesafio("");
            Imagen imagen = imagenServicio.guardaImagen(archivo);
            campana.setEstado(true);
            campana.setImagen(imagen);
        //Guardamos la campaña
            campanaRepositorio.save(campana);
    }

    //Actualizar campaña
    @Transactional
    public void modificarCampana(
            String id,
            String titulo,
            String desafio,
            String descripcion)
            throws MyException {
            //Validamos que los campos no estén vacios
            validar(titulo, desafio,descripcion);
            Optional<Campana> respuesta = campanaRepositorio.findById(id);
        if (respuesta.isPresent())
        {
            Campana campana = new Campana();
            campana.setTitulo(titulo);
            campana.setDescripcion(descripcion);
            campana.setDesafio(desafio);
            campanaRepositorio.save(campana);
        }
    }

    //Consultar todas las campañas
    public List<Campana> listarCampanas()
    {
        List<Campana> campanas = new ArrayList();
        campanas = campanaRepositorio.findAll();
        return campanas;
    }

    //Captura la campaña por id
    public Campana getOne(String id) {
        return campanaRepositorio.getOne(id);
    }

    //Dar de BAJA Campaña por id
    public void darBajaPorId(String id)
            throws MyException
    {
        Optional<Campana> campanaActiva = campanaRepositorio.findById(id);
            if ( campanaActiva.isPresent() )
            {
                campanaActiva.get().setEstado(Boolean.FALSE);
            }
            else
            {
            throw new MyException("No se encontró la campaña con el ID " + id);
            }
    }

    //Eliminar campañas
    @Transactional
    public void eliminarCampana(String id)
            throws MyException
    {
        Optional<Campana> respuesta = campanaRepositorio.findById(id);
            if (respuesta.isPresent())
            {
                campanaRepositorio.delete(respuesta.get());
            }
            else
            {
                throw new MyException("La campaña con el id " + id + " no existe.");
            }
    }
    //Validar campos vacios
    private void validar(
            String titulo,
            String desafio,
            String descripcion)
            throws MyException
    {
        if (titulo.isEmpty() || titulo == null)
        {
            throw new MyException("el título de la campaña  no puede ser "
                    + "nulo o estar vacío");
        }
        if (desafio.isEmpty() ||desafio == null)
        {
            throw new MyException("El desafío de la campaña no puede ser "
                    + "nulo o estar vacío");
        }
        if (descripcion.isEmpty() ||descripcion == null)
        {
            throw new MyException("La descripción de la campaña no puede ser "
                    + "nulo o estar vacío");
        }
    }

}
