package com.equipotres.medioambiente.Entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Empresa extends Usuario{
    public static final String ROL_EMPRESA="EMPS";
    private String rol;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_emps;

    @OneToMany
    private Campana campana;
}
