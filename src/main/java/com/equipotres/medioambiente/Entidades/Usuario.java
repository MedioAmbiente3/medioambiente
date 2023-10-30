package com.equipotres.medioambiente.Entidades;

import com.equipotres.medioambiente.Enumeraciones.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_usuario;

    private String nombre;
    private String correo;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role rol;

    @ManyToOne
    private Campana campana;
}
