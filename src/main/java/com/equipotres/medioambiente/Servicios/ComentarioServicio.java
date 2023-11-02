package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    //Crear comentario
    public void crearComentario(){}


    //Modificar comentario

    //Consultar comentarios

    //Validar campos vacios


}
