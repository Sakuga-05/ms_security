package com.uc.ms_security.repository;

import com.uc.ms_security.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//UserRepository hereda de JpaRepository algunas funciones y procedimientos:
//save(), findAll(), findById(), delete(), existsById()

//Aquí realizo las consultas de la base de datos
public interface UserRepository extends JpaRepository<User, Long> {

    //El "exist" internamente hace una verificación de que si existe en la BD
    //y devuelve true o false según el caso
    boolean existsByEmail(String email);

    //Verifica si existe otro usuario con el mismo email, excluyendo al usuario con el ID proporcionado.
    boolean existsByEmailAndIdNot(String email, Long id);

    @EntityGraph(attributePaths = {"profile"})
    Optional<User> findWithProfileById(Long id);

    //Carga el JOIN de la relación entre User y Session para evitar el problema de N+1 consultas
    @EntityGraph(attributePaths = {"sessions"}) 
    Optional<User> findWithSessionsById(Long id);

    //Se esta haciendo un JOIN de tres tablas, con user, userRole y desde userRole ingreso a role.
    //Esto es para que cuando busque un usuario, me traiga todos sus roles asociados
    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})
    Optional<User> findWithRolesById(Long id);
}
