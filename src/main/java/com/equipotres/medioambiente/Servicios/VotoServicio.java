package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Entidades.Voto;
import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.PublicacionRepositorio;
import com.equipotres.medioambiente.Repositorios.UsuarioRepositorio;
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

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private PublicacionServicio publicacionServicio;

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
    
    //Conteo de votos de publicación
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

    //Conteo de votos de usuario suscrito
    public int contarVotosDeSuscrito(String idUsuario, String idCampana)
    { 
        String idPublicacion = publicacionServicio.obtenerIdPublicacion(idUsuario,idCampana);
        return contarVotosDePublicacion(idPublicacion);
    }

    //Obtener las más votadas de una campaña
    public List<Publicacion> publicacionesMasVotadasDeCampana(String campanaid)
    {
        List <Publicacion> publicacionesDeCampana;
        publicacionesDeCampana = publicacionRepositorio.findPublicacionesByCampana(campanaid);
        return votoRepositorio.findPublicacionesMasVotadas(publicacionesDeCampana);
    }

    //Usuarios más votados
    public List<Usuario> usuariosMasVotadosDeCampana(String campanaid)
    {
        List <Publicacion> publicacionesMasVotadas;
        publicacionesMasVotadas = publicacionesMasVotadasDeCampana(campanaid);
        return votoRepositorio.findUsuariosMasVotados(publicacionesMasVotadas);
    }

    public List<Voto> listarVotosDeUsuario(String idUsuario)
    {
        List<Voto> votosDeUsuario = new ArrayList<>();
        for(Voto voto: listarVotos())
        {
            if(voto.getUsuario().getId().equals(idUsuario))
            {
                votosDeUsuario.add(voto);
            }
        }
        return votosDeUsuario;
    }

    //Obtener el id de un Voto o empty dado el usuario y publicación
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

