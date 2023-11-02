package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Foto;
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
    private FotoServicio fotoServicio;

    //Crear campaña
    @Transactional
    public void crearCampana(String titulo, StringBuilder contenido, MultipartFile imagen) throws MyException {
        //Validamos que los campos no esten vacios
        validar(titulo, contenido);

        //Crear un objeto de la clase Campana
        Campana campana = new Campana();

        campana.setTitulo(titulo);
        campana.setContenido(contenido);
        campana.setFecha(LocalDate.now());
        Foto foto = fotoServicio.guardaImagen(imagen);
        campana.setEstado(true);
        campana.setPremio("Ganador");
        campana.setFoto(foto);
        //Guardamos la campaña
        campanaRepositorio.save(campana);

    }

    //Modificar campaña
    @Transactional
    public void modificarCampana(String id_campana, String titulo,
                                 StringBuilder contenido) throws MyException {
        //Validamos que los campos no estén vacios
        validar(titulo, contenido);

        Optional<Campana> respuesta = campanaRepositorio.findById(id_campana);

        if (respuesta.isPresent()) {
            Campana campana = new Campana();
            campana.setTitulo(titulo);
            campana.setContenido(contenido);

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
    private void validar(String titulo, StringBuilder contenido)
            throws MyException {

        if (titulo.isEmpty() || titulo == null) {
            throw new MyException("el titulo de la campaña  no puede ser "
                    + "nulo o estar vacio");
        }

        if (contenido == null) {
            throw new MyException("La descripción de la campaña no puede ser "
                    + "nulo o estar vacio");
        }

    }

}
