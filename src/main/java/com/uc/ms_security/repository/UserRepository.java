package com.uc.ms_security.repository;

import com.uc.ms_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//UserRepository hereda de JpaRepository algunas funciones y procedimientos:
//save(), findAll(), findById(), delete(), existsById()

//Aquí realizo las consultas de la base de datos
public interface UserRepository extends JpaRepository<User, Long> {

    //El "exist" internamente hace una verificación de que si existe en la BD
    //y devuelve true o false según el caso
    boolean existsByEmail(String email);

    //Verifica si existe otro usuario con el mismo email, excluyendo al usuario con el ID proporcionado.
    boolean existsByEmailAndIdNot(String email, Long id);
}
