package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ImagenServicio {

    // Instanciar los repositorios como variables globales
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    // Crear una Imagen
    @Transactional
    public Imagen guardaImagen(MultipartFile archivo) throws MyException {
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName());

                imagen.setContenido(archivo.getBytes());


                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    //Modificar o editar la imagen
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MyException{
        if (archivo != null) {
            try {

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
                System.err.println(e.getMessage());
            }
        }
        return null;

    }

    //Captura el id de la Imagen
    public Imagen getOne(String id) {
        return imagenRepositorio.getOne(id);
    }


    //Traer una campaña



    //Listar todas las imagenes
    @Transactional
    public List<Imagen> listarTodos() {
        return imagenRepositorio.findAll();
    }


}
