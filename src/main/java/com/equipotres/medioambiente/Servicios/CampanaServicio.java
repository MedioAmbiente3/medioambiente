package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.CampanaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    public void crearCampana(String titulo, String descripcion, String desafio,  MultipartFile archivo, LocalDate fechaFinal) throws MyException {
        //Validamos que los campos no esten vacios
        validar(titulo, descripcion, desafio);

        //Crear un objeto de la clase Campana
        Campana campana = new Campana();

        campana.setTitulo(titulo);
        campana.setDescripcion(descripcion);
        campana.setDesafio(desafio);
        Imagen imagen = imagenServicio.guardaImagen(archivo);
        campana.setImagen(imagen);
        campana.setEstado(true);
        campana.setFechaCreacion(LocalDate.now());
        campana.setFechaFinal(fechaFinal);

        //Guardamos la campaña
        campanaRepositorio.save(campana);

    }

    //Modificar campaña
    @Transactional
    public void modificarCampana(String id_campana,
                                 String titulo,
                                 String descripcion,
                                 String desafio,
                                 Boolean estado,
                                 MultipartFile archivo) throws MyException {
        //Validamos que los campos no estén vacios
        validar(titulo, descripcion, desafio);

        Optional<Campana> respuesta = campanaRepositorio.findById(id_campana);

        if (respuesta.isPresent()) {
            Campana campana = respuesta.get();
            campana.setTitulo(titulo);
            campana.setDescripcion(descripcion);
            campana.setDesafio(desafio);
            campana.setEstado(estado);
            String idImagen = null;
            if (campana.getImagen() != null) {
                idImagen = campana.getImagen().getId();

            }
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            campana.setImagen(imagen);
            campanaRepositorio.save(campana);

        }

    }

    //Consultar todas las campañas
    public List<Campana> listarCampanas() {
        List<Campana> campanas = new ArrayList();
        campanas = campanaRepositorio.findAll();
        return campanas;

    }

    //traer el id de la campana
    public Campana getOne(String id) {
        return campanaRepositorio.getOne(id);
    }

    //Eliminar campañas
    @Transactional
    public void eliminarCampana(String id_campana) {

        Optional<Campana> respuesta = campanaRepositorio.findById(id_campana);

        if (respuesta.isPresent()) {

            Campana campana = respuesta.get();

            campanaRepositorio.delete(campana);

        }

    }

    //Traer una campaña
    public Optional<Campana> findById(String campanaId) {
        return campanaRepositorio.findById(campanaId);
    }

    //Validar campos vacios
    private void validar(String titulo, String descripcion, String desafio)
            throws MyException {

        if (titulo.isEmpty() || titulo == null) {
            throw new MyException("el titulo de la campaña  no puede ser "
                    + "nulo o estar vacio");
        }

        if (descripcion.isEmpty() ||descripcion == null) {
            throw new MyException("La descripción de la campaña no puede ser "
                    + "nulo o estar vacio");
        }

        if (desafio.isEmpty() || desafio == null) {
            throw new MyException("el desafio de la campaña  no puede ser "
                    + "nulo o estar vacio");
        }

    }

}
