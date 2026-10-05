package com.uc.ms_security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor; //Lombok crea cosas solo en tiempo de ejecución.
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

//los @ se llaman decoradores.
//Si quiero que sea una base de datos no relacional el @Table se reemplaza por @Document
@Entity //Necesito que algo se guarda en una tabla
@Table(name = "users") //Da el nombre de la tabla
@Getter
@Setter
@NoArgsConstructor
public class User {

    //Propio de jacarta (urm traduce el código de aquí a la base de datos
    @Id //Esto va ser el campo id en la tabla.
    @GeneratedValue( //Que esto es auto incrementable
            strategy = GenerationType.IDENTITY
    )
    private Long id; //Long en java y en la tabla su equivalente es integer.

    @Column( //El como necesito que se cree el campo en la base de datos.
            nullable = false,
            length = 100
    )
    private String name; //En java String, en base de datos es varchar.

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String email;

    @Column(
            nullable = false
    )
    private String password;

    @OneToOne( //Tengo pegado un perfil a este usuario, es una relación 1 a 1
            mappedBy = "user", //Referencia a la variable que está en la otra clase (Profile)
            cascade = CascadeType.ALL, //Si se borra el usuario, se borra el perfil
            orphanRemoval = true,
            fetch = FetchType.LAZY 
    )
    private Profile profile;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Session> sessions = new ArrayList<>();
}