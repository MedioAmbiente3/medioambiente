package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Evidencia;
import com.equipotres.medioambiente.Entidades.Foto;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.EvidenciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class EvidenciaServicio {

    @Autowired
    private EvidenciaRepositorio evidenciaRepositorio;

    @Autowired
    private FotoServicio fotoServicio;


    //Método crear Evidencia
    @Transactional
    public void crearEvidencia(String titulo, StringBuilder contenido, MultipartFile imagen)
            throws MyException {

        validar(titulo, contenido);


        Evidencia evidencia = new Evidencia();

        evidencia.setTitulo(titulo);
        evidencia.setContenido(contenido);
        Foto foto = fotoServicio.guardaImagen(imagen);
        evidencia.setFoto(foto);
        evidencia.setFecha(LocalDate.now());

        evidenciaRepositorio.save(evidencia);

    }

    //Editar o modificar evidencia
    @Transactional
    public void editarEvidencia(String id_evidencia, String titulo, StringBuilder contenido,
                                MultipartFile imagen) throws MyException {

        //Validamos los campos
        validar(titulo, contenido);

        // en caso de que el id de la Evidencia este mal digitado o que no se encuentre,
        // se debe de usar un optional
        Optional<Evidencia> respuesta = evidenciaRepositorio.findById(id_evidencia);

        //comprobar de que si exista un dato con el mismo id
        if (respuesta.isPresent()) {
            //instanciamos un objeto de tipo Evidencia
            Evidencia evidencia = respuesta.get();

            //seteamos el nombre, porque el id no hay como modificarle
            evidencia.setTitulo(titulo);

            evidencia.setContenido(contenido);
            String idFoto = null;
            if (evidencia.getFoto() != null) {
                idFoto = evidencia.getFoto().getId();

            }

            Foto foto = fotoServicio.actualizar(imagen, idFoto);
            evidencia.setFoto(foto);
            //guardamos
            evidenciaRepositorio.save(evidencia);
        }

    }

    //Captura el id del Evidencia
    public Evidencia getOne(String id) {
        return evidenciaRepositorio.getOne(id);
    }

    //Listar todos las Evidencias
    @Transactional
    public List<Evidencia> listarEvidencias() {
        List<Evidencia> evidencias = new ArrayList();

        evidencias = evidenciaRepositorio.findAll();
        //Metodo propio del jpaRepo es traer todos los datos de la tabla con el ".findAll()"
        return evidencias;
    }


    //Método votar por Participante
    public void recibirVotoParticipante(String id_evidencia, Usuario usuario) {

        Optional<Evidencia> respuesta = evidenciaRepositorio.findById(id_evidencia);

        if (respuesta.isPresent()) {

            //instanciamos un objeto de tipo Evidencia
            Evidencia evidencia = respuesta.get();

            HashSet<Usuario> votosActuales = (HashSet<Usuario>) evidencia.getListaVotos();
            votosActuales.add(usuario);

        }

    }


    //Método para contar votos por Participante
    public long contarVotoParticipante(String id_evidencia) {

        Optional<Evidencia> respuesta = evidenciaRepositorio.findById(id_evidencia);

        if (respuesta.isPresent()) {

            //instanciamos un objeto de tipo Evidencia
            Evidencia evidencia = respuesta.get();


            HashSet<Usuario> votosActuales = (HashSet<Usuario>) evidencia.getListaVotos();
            Long cantidadVotos = votosActuales.stream().count();

            return cantidadVotos;
        }

        return 0;

    }


    //Verificar campos vacios o nulos
    // Método para manejar excepciones
    private void validar(String titulo, StringBuilder contenido) throws MyException {

        if (titulo == null) {
            throw new MyException("el titulo de la evidencia no puede ser nulo");
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MyException("el titulo no puede estar vacio");
        }
        if (contenido == null) {
            throw new MyException("el contenido de la evidencia no puede ser nulo");
        }

    }


}
