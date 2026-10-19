package com.uc.ms_security.repository;

import com.uc.ms_security.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    //Verifica que no exista un rol con el mismo nombre (ignorando mayúsculas y minúsculas)
    boolean existsByNameIgnoreCase(String name);

    //Verifica si existe otro rol con el mismo nombre, excluyendo al rol con el ID proporcionado.
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    @EntityGraph(attributePaths = {"rolePermissions", "rolePermissions.permission"})
    Optional<Role> findWithPermissionsById(Long id);
}
