package com.equipotres.medioambiente.Entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evidencia  extends Publicacion{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_evidencia;

    @ManyToMany
    private Set<Usuario> listaVotos = new HashSet<>();

    @ManyToOne
    private Campana campana;



}
