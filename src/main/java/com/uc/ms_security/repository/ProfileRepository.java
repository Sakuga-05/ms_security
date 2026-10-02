package com.uc.ms_security.repository;

import com.uc.ms_security.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsByPhone(String phone);

    //Verifica si existe otro usuario con el mismo telefono, excluyendo al usuario con el ID proporcionado.
    boolean existsByPhoneAndIdNot(String phone, Long id);

    //Existe un perfil asociado a un usuario específico, identificado por su ID.
    Optional<Profile> findByUserId(Long userId);

    //Existe un perfil asociado a un usuario específico, identificado por su ID.
    boolean existsByUserId(Long userId);

}
