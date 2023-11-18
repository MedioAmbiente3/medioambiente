package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ImagenServicio {

    // Instanciar los repositorios como variables globales
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    long tamanoMaximoEnMB = 10; // 10 megabytes
    long tamanoMaximo = tamanoMaximoEnMB * 1024 * 1024; // convertir a bytes

    // Crear una Imagen
    @Transactional
    public Imagen guardaImagen(MultipartFile archivo) throws MyException {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                if (archivo.getSize() > tamanoMaximo){
                    throw new MyException("La imagen excede el tamaño máximo permitido");
                }

                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                throw new MyException("Error al procesar la imagen.");
            }
        } else {
            throw new MyException("El archivo de imagen está vacío o es nulo.");

        }
    }

    //Modificar o editar la imagen
    @Transactional
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MyException {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                if (archivo.getSize() > tamanoMaximo){
                    throw new MyException("La imagen excede el tamaño máximo permitido");
                }

                Imagen imagen = new Imagen();
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
    
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
    
                return imagenRepositorio.save(imagen);
    
            } catch (Exception e) {
                throw new MyException("Error al procesar la imagen.");
            }
        } else {

            throw new MyException("El archivo de imagen está vacío o es nulo.");
            /*
            // Crear y retornar una imagen por defecto
            Imagen imagenPorDefecto = new Imagen();
            imagenPorDefecto.setMime("image/jpeg"); // ajusta el tipo MIME según tu necesidad
            imagenPorDefecto.setNombre("imagen_por_defecto.jpg"); // ajusta el nombre según tu necesidad
            // Puedes asignar contenido por defecto o dejarlo en null, dependiendo de tus necesidades
            // imagenPorDefecto.setContenido(null);

            return imagenRepositorio.save(imagenPorDefecto);

             */
        }

    }

    //Captura el id de la Imagen
    public Imagen getOne(String id) {
        return imagenRepositorio.getOne(id);
    }

    //Listar todas las imagenes
    @Transactional
    public List<Imagen> listarTodos() {
        return imagenRepositorio.findAll();
    }



}
