package com.equipotres.medioambiente.Entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Admin extends Usuario{
    public static final String ROL_ADMIN="ADMIN";
    private String rol;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_admin;

    @OneToMany
    private Campana campana;
}
