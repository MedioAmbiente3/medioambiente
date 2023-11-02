package com.equipotres.medioambiente.Entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

public class Participante extends Usuario{
    public static final String ROL_PARTICIPANTE="PART";
    private String rol;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_user;

    @ManyToMany
    private Campana campana;
}
