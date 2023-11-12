package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Voto;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.VotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VotoServicio {

    @Autowired
    private VotoRepositorio votoRepositorio;

    @Transactional
    public void crearVoto(Voto voto) throws MyException
    {
        votoRepositorio.save(voto);
    }

    public List<Voto> listarVotos()
    {
        List<Voto> votos = votoRepositorio.findAll();
        return votos;
    }
    
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
    public String obtenerIdVoto(String idUsuario, String idPublicacion)
    {
        String idVoto = new String();
        for (Voto voto:listarVotosDeUsuario(idUsuario))
        {
            if(voto.getPublicacion().getId().equals(idPublicacion))
            {
              idVoto = voto.getId();
            }
        }
        return idVoto;
    }


    public Optional<Voto> votosPorIdPublicacion(String idPublicacion){

        Optional<Voto> votosIdPub =votoRepositorio.findById(idPublicacion);
        return votosIdPub;
    }

    public Optional<Voto> votosPorIdUsuario(String idUsuario){

        Optional<Voto> votosIdUsr = votoRepositorio.findById(idUsuario);
        return votosIdUsr;
    }
    
    public Voto obtenerID(String id){

        return votoRepositorio.getOne(id);
    }

}
