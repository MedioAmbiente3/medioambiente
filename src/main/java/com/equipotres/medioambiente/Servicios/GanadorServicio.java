package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.*;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.GanadorRepositorio;
import com.equipotres.medioambiente.Repositorios.EmpresaRepositorio;
import com.equipotres.medioambiente.Servicios.SubscripcionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GanadorServicio {

    @Autowired
    private GanadorRepositorio ganadorRepositorio;

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

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
        return ganadorRepositorio.selectGanadoresByEmpresaId(empresaid);
    }
    @Transactional(readOnly = true)
    public List<Ganador> listarGanadoresPorEmpresaYCampana(String empresaid, String campanaid){
        return ganadorRepositorio.selectGanadoresByEmpresaIdAndCampanaId(empresaid, campanaid);
    }
    public boolean noHayGanadorEnEmpresa(String empresaid, String campanaid)
    {
        empresaid = empresaRepositorio.selectEmpresaIdByUsuarioId(empresaid);
        return listarGanadoresPorEmpresaYCampana(empresaid,campanaid).stream().count() == 0;
    }
    
    //Si el ganador existe retorna id, de contrario empty
    public String obtenerIdGanador(String empresaid,String usuarioid, String campanaid)
    {
        empresaid = empresaRepositorio.selectEmpresaIdByUsuarioId(empresaid);
        String subscripcionid = subscripcionServicio.
                obtenerIdSubscripcion(usuarioid,campanaid);
        String idGanador = "";
        for (Ganador gan:listarGanadoresPorEmpresa(empresaid))
        {
            Subscripcion subscripcion = gan.getSubscripcion();
            if(subscripcion.getId().equals(subscripcionid))
            {
                idGanador = gan.getId();
            }
        }
        return idGanador;
    }
}
