package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Entidades.Voto;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.VotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VotoServicio {

    @Autowired
    private VotoRepositorio votoRepositorio;

    @Transactional
    public void crearVoto(Voto voto) throws MyException
    {
        votoRepositorio.save(voto);
    }

    //Método auxiliar de obtener total de Votos	
    public List<Voto> listarVotos(){

        List<Voto> votos = votoRepositorio.findAll();
        return votos;
    }
    
    //Método auxiliar para obtener lista idpublicaciones votadas
    public List<String> IdDePublicacionesVotadas()
    {
        List<String> IdDePublicacionesVotadas = new ArrayList<>();
        for (Voto voto :listarVotos())
        {
            IdDePublicacionesVotadas.add(voto.getPublicacion().getId());
        }
        return IdDePublicacionesVotadas;
    }
    
    //Conteo de votos
    public int contarVotosDePublicacion(String idPublicacion)
    {
        List<Voto> votosDePublicacion = new ArrayList<>();
        for (Voto voto :listarVotos())
        {
            if(voto.getPublicacion().getId().equals(idPublicacion))
            {votosDePublicacion.add(voto);}
        }
        return (int) votosDePublicacion.stream().count();
    }

    public List<Usuario> obtenerUsuariosQueVotaron(String idPublicacion) {
        return listarVotos().stream()
                .filter(voto -> voto.getPublicacion().getId().equals(idPublicacion))
                .map(Voto::getUsuario)
                .collect(Collectors.toList());
    }

    public List<String> obtenerNombresUsuariosQueVotaron(String idPublicacion) {
        return obtenerUsuariosQueVotaron(idPublicacion).stream()
                .map(Usuario::getNombre)
                .collect(Collectors.toList());
    }


    //Obtener lista de cantidad de votos de cada idPublicación
    //Definir un objeto votación
    public class Votacion 
    {
        String idPublicacion;
        int cantidadDeVotos;
        // constructor
        public Votacion(String idPublicacion, int cantidadDeVotos) {
            this.idPublicacion = idPublicacion;
            this.cantidadDeVotos = cantidadDeVotos;
        }
        public int getCantidad() {
            return cantidadDeVotos;
        }
    }

    //Obtener lista de votaciones con cantidad de votos por idPublicacion	
    public List<Votacion> listaDeVotaciones() 
    {
        List<Votacion> listaDeVotaciones = new ArrayList<>();
        
        for(String idPublicacion : IdDePublicacionesVotadas()) 
        {
            int cantidadDeVotos = contarVotosDePublicacion(idPublicacion);
            listaDeVotaciones.add(new Votacion(idPublicacion, cantidadDeVotos));
        }

        // Ordenar la lista de publicaciones por cantidad de votos
        Collections.sort(listaDeVotaciones, new Comparator<Votacion>() {
            @Override
            public int compare(Votacion actual, Votacion siguiente) 
            {
              return Integer.compare(siguiente.getCantidad(), actual.getCantidad());
              // orden descendente
            }
        });
        return listaDeVotaciones;
    }
    
    // Devuelve los primeros 10 elementos, o menos si la lista es más pequeña
    public List<Votacion> diezPublicacionesMasVotadas() 
    {   
        List<Votacion> publicacionesMasVotadas = listaDeVotaciones();
        return publicacionesMasVotadas.subList(0, Math.min(10, publicacionesMasVotadas.size())); 
    }

    //Obtener los votos del usuario dado  el id
    public List<Voto> listarVotosDeUsuario(String idUsuario)
    {
        List<Voto> votosDeUsuario = new ArrayList<>();
        for (Voto voto :listarVotos())
        {
            if(voto.getUsuario().getId().equals(idUsuario))
            {votosDeUsuario.add(voto);}
        }
        return votosDeUsuario;
    }
    //Obtener el id de un Voto dado el usuario y publicación
    public String obtenerIdVoto(String idUsuario, String idPublicacion)
    {
        String idVoto = "";
        for (Voto voto:listarVotosDeUsuario(idUsuario))
        {
            if(voto.getPublicacion().getId().equals(idPublicacion))
            {
              idVoto = voto.getId();
            }
        }
        return idVoto;
    }
    
    //Obtener Voto dado el id
    public Voto getOne(String id)
    {
        return votoRepositorio.getOne(id);
    }

}

