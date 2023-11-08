package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Subscripcion;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.SubscripcionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscripcionServicio {

 @Autowired
 private SubscripcionRepositorio subscripcionRepositorio;

 //Crear subscripcion
 public void crearSubscripcion(Subscripcion subscripcion)
 throws MyException
 {
   subscripcionRepositorio.save(subscripcion);
 }

//Modificar subscripcion


//Consultar todas las subscripciones
public List<Subscripcion> listarSubscripciones()
{
  List<Subscripcion> subscripciones = subscripcionRepositorio.findAll();
  return subscripciones;
}

public Subscripcion getOne(String id){ return subscripcionRepositorio.getOne(id); }

}//SubscripcionServicio

