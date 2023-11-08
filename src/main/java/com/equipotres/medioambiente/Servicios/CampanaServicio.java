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
public void crearCampana
(
  String        titulo,
  String        descripcion,
  String        desafio,
  MultipartFile archivo
)
throws MyException
{
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
public void modificarCampana
(
  String id,
  String titulo,
  String descripcion,
  String desafio
)
throws MyException
{
  validar(titulo, descripcion, desafio);
  Optional<Campana> actual = campanaRepositorio.findById(id);
  if(actual.isPresent())
  { //se recupera la campaña a modificar
    Campana campana = actual.get();
    campana.setTitulo(titulo);
    campana.setDescripcion(descripcion);
    campana.setDesafio(desafio);
    campanaRepositorio.save(campana);
  }
  else
  {
    throw new MyException("No se encontró la campaña con el ID " + id);
  }
}

//Consultar todas las campañas, activas o dadas de  baja
public List<Campana> listarCampanas(String opt)
{
  List<Campana> listado = campanaRepositorio.findAll();
  List<Campana> activas = new ArrayList<>();
  List<Campana> deBajas = new ArrayList<>();
  for (Campana campana : listado)
  {
    if( campana.getEstado() ){ activas.add(campana); }
    else{ deBajas.add(campana); }
  }
  if(opt.equals("a")) listado = activas;
  if(opt.equals("b")) listado = deBajas;
  return listado;
}

//Retorna una campaña por id
public Campana getOne(String id) { return campanaRepositorio.getOne(id); }

//Dar baja a campaña
@Transactional
public void darBajaCampana(String id)
{
  Optional<Campana> campanaActiva = campanaRepositorio.findById(id);
  if(campanaActiva.isPresent())
  {
    Campana campana = campanaActiva.get();
    campana.setEstado(Boolean.FALSE);
    campanaRepositorio.save(campana);
  }
}

//Dar alta(activar) campaña
@Transactional
public void activarCampana(String id)
{
  Optional<Campana> campanaDeBaja = campanaRepositorio.findById(id);
  if(campanaDeBaja.isPresent())
  {
   Campana campana = campanaDeBaja.get();
   campana.setEstado(Boolean.TRUE);
   campanaRepositorio.save(campana);
  }
}

//Traer una campaña
public Optional<Campana> findById(String id) { return campanaRepositorio.findById(id); }

//Validar campos vacios
private void validar(String titulo, String descripcion, String desafio)
throws MyException
{
  if (titulo.isEmpty() || titulo == null) {
    throw new MyException("el titulo de la campaña  no puede ser nulo o estar vacio");
    }
  if (descripcion.isEmpty() ||descripcion == null) {
    throw new MyException("La descripción de la campaña no puede ser nulo o estar vacio");
    }
  if (desafio.isEmpty() || desafio == null) {
    throw new MyException("el desafio de la campaña  no puede ser nulo o estar vacio");
    }
}

}//CampanaServicio
