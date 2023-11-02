package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Repositorios.PublicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PublicacioServicio {



    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    //Método crear Publicacion
    @Transactional
    public void crearPublicacion(String titulo, String contenido,
                                 MultipartFile archivo){


    }


    //Método modificar Publicación


    //Método eliminar Publicación

    //Método consultar Publicación

    //Verificar campos vacios
}
