package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Voto;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.VotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VotoServicio {

    @Autowired
    private VotoRepositorio votoRepositorio;

    @Transactional
    public void crearVoto(Voto voto) throws MyException{

        String validar = votoRepositorio.obtenerIdVoto(voto.getUsuario().getId(),voto.getPublicacion().getId());

        if (validar.isEmpty()){
            votoRepositorio.save(voto);
        }
        else{
            throw new MyException("el voto ya se encuentra registrado.");
        }

    }

    public List<Voto> listarVotos(){

        List<Voto> votos = votoRepositorio.findAll();
        return votos;
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
