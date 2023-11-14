package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Subscripcion;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.SubscripcionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscripcionServicio {

    @Autowired
    private SubscripcionRepositorio subscripcionRepositorio;

    //Crear subscripcion
    public void crearSubscripcion(Subscripcion subscripcion) throws MyException
    {
        subscripcionRepositorio.save(subscripcion);
    }

    //Consultar todas las Subscripciones
    public List<Subscripcion> listarSubscripciones()
    {
        List<Subscripcion> subscripciones = subscripcionRepositorio.findAll();
        return subscripciones;
    }
    public List<Subscripcion> listarSubscripcionesDeUsuario(String idUsuario)
    {
        List<Subscripcion> subscripcionesDeUsuario = new ArrayList<>();
        for (Subscripcion sub :listarSubscripciones())
        {
            if(sub.getUsuario().getId().equals(idUsuario))
            {subscripcionesDeUsuario.add(sub);}
        }
        return subscripcionesDeUsuario;
    }
    public String obtenerIdSubscripcion(String idUSuario, String idCampana)
    {
        String idSubscripcion = new String();
        for (Subscripcion sub:listarSubscripcionesDeUsuario(idUSuario))
        {
            if(sub.getCampana().getId().equals(idCampana))
            {
              idSubscripcion = sub.getId();
            }
        }
        return idSubscripcion;
    }

    //Obtener subscripcion dado el Id
    public Subscripcion getOne(String id){
        return subscripcionRepositorio.getOne(id);

    }




}
