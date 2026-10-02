package com.uc.ms_security.entity;

import jakarta.persistence.*; //
import lombok.Getter;
import lombok.NoArgsConstructor; //Lombok crea cosas solo en tiempo de ejecución.
import lombok.Setter;

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
}