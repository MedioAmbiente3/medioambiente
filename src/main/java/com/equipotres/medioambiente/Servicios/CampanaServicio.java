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
    public void crearCampana(String titulo, String descripcion, String desafio,  MultipartFile archivo) throws MyException {
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

        //Guardamos la campaña
        campanaRepositorio.save(campana);

    }

    //Modificar campaña
    @Transactional
    public void modificarCampana(String id_campana, String titulo,
                                 String descripcion, String desafio) throws MyException {
        //Validamos que los campos no estén vacios
        validar(titulo, descripcion, desafio);

        Optional<Campana> respuesta = campanaRepositorio.findById(id_campana);

        if (respuesta.isPresent()) {
            Campana campana = new Campana();
            campana.setTitulo(titulo);
            campana.setDescripcion(descripcion);
            campana.setDesafio(desafio);

            campanaRepositorio.save(campana);

        }

    }

    //Consultar todas las campañas
    public List<Campana> listarCampanas() {
        List<Campana> campanas = new ArrayList();
        campanas = campanaRepositorio.findAll();
        return campanas;

    }

    //Captura el id del autor
    public Campana getOne(String id) {
        return campanaRepositorio.getOne(id);
    }

    //Eliminar campañas
    @Transactional
    public void eliminarCampana(String id_campana) {

        Optional<Campana> respuesta = campanaRepositorio.findById(id_campana);

        if (respuesta.isPresent()) {
            Campana campana = new Campana();

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
