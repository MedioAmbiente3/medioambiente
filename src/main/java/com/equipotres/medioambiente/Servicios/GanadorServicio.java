package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.*;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.GanadorRepositorio;
import com.equipotres.medioambiente.Repositorios.SubscripcionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class GanadorServicio {

    @Autowired
    private GanadorRepositorio ganadorRepositorio;

    @Autowired
    private SubscripcionServicio subscripcionServicio;

    //Crear ganador
    public void crearGanador(Ganador ganador) throws MyException
    {
        ganadorRepositorio.save(ganador);
    }
    
    //Obtener ganador por id 
    public Ganador getOne(String id){
        return ganadorRepositorio.getOne(id);
    }

    //Método listar Ganadores
    @Transactional
    public List<Ganador> listarGanadores() {
        List<Ganador> ganadores = new ArrayList();
        ganadores = ganadorRepositorio.findAll();
        return ganadores;
    }

    //Listar ganadores dado el id de la empresa
    @Transactional(readOnly = true)
    public List<Ganador> listarGanadoresPorEmpresa(String empresaid){
        List<Ganador> ganadores;
        ganadores = ganadorRepositorio.selectGanadoresbyEmpresaId(empresaid);
        return ganadores;
    }

    //Si el ganador existe retorna id, de contrario empty
    public String obtenerIdGanador(String usuarioid, String empresaid)
    {
        String idGanador = "";
        for (Ganador gan:listarGanadoresPorEmpresa(empresaid))
        {
            Usuario usuario = gan.getSubscripcion().getUsuario();
            if(usuario.getId().equals(usuarioid))
            {
                idGanador = gan.getId();
            }
        }
        return idGanador;
    }
}
