package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Foto;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.FotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FotoServicio {

    // Instanciar los repositorios como variables globales
    @Autowired
    private FotoRepositorio fotoRepositorio;

    // Crear una Imagen
    @Transactional
    public Foto guardaImagen(MultipartFile archivo) throws MyException {
        if (archivo != null) {
            try {

                Foto foto = new Foto();

                foto.setMime(archivo.getContentType());

                foto.setNombre(archivo.getName());

                foto.setContenido(archivo.getBytes());


                return fotoRepositorio.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Foto actualizar(MultipartFile archivo, String idImagen) throws MyException{
        if (archivo != null) {
            try {

                Foto foto = new Foto();

                if (idImagen != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idImagen);

                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }


                foto.setMime(archivo.getContentType());

                foto.setNombre(archivo.getName());

                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }

    @Transactional
    public List<Foto> listarTodos() {
        return fotoRepositorio.findAll();
    }


}
