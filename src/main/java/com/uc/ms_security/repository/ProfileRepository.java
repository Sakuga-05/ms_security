package com.uc.ms_security.repository;

import com.uc.ms_security.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsByPhone(String phone);

    //Verifica si existe otro usuario con el mismo telefono, excluyendo al usuario con el ID proporcionado.
    boolean existsByPhoneAndIdNot(String phone, Long id);
}
