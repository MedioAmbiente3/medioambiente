package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.CampanaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CampanaServicio {

    @Autowired
    private CampanaRepositorio campanaRepositorio;

    //Crear campaña
    @Transactional
    public void crearCampana(String titulo, String redaccion) throws MyException {
        //Validamos que los campos no esten vacios
        validar(titulo, redaccion);

        //Crear un objeto de la clase Campana
        Campana campana = new Campana();

        campana.setTitulo(titulo);
        campana.setRedaccion(redaccion);
        campana.setFecha(LocalDate.now());

        //Guardamos la campaña
        campanaRepositorio.save(campana);

    }

    //Modificar campaña
    @Transactional
    public void modificarCampana(String id_campana, String titulo,
                                 String redaccion) throws MyException {
        //Validamos que los campos no estén vacios
        validar(titulo, redaccion);

        Optional<Campana> respuesta = campanaRepositorio.findById(id_campana);

        if (respuesta.isPresent()) {
            Campana campana = new Campana();
            campana.setTitulo(titulo);
            campana.setRedaccion(redaccion);

            campanaRepositorio.save(campana);

        }

    }

    //Consultar todas las campañas
    public List<Campana> listarCampanas() {
        List<Campana> campanas = new ArrayList();
        campanas = campanaRepositorio.findAll();
        return campanas;

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

    //Validar campos vacios
    private void validar(String titulo, String redaccion)
            throws MyException {

        if (titulo.isEmpty() || titulo == null) {
            throw new MyException("el titulo de la campaña  no puede ser "
                    + "nulo o estar vacio");
        }

        if (redaccion.isEmpty() || redaccion == null) {
            throw new MyException("La descripción de la campaña no puede ser "
                    + "nulo o estar vacio");
        }

    }

}
