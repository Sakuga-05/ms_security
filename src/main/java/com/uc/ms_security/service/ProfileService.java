package com.uc.ms_security.service;

import com.uc.ms_security.dto.profile.CreateProfileDTO;
import com.uc.ms_security.dto.profile.ProfileResponseDTO;
import com.uc.ms_security.dto.profile.UpdateProfileDTO;
import com.uc.ms_security.entity.Profile;
import com.uc.ms_security.mapper.ProfileMapper;
import com.uc.ms_security.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    //CREA PERFIL
    public ProfileResponseDTO create(CreateProfileDTO dto) {
        if (profileRepository.existsByPhone(dto.getPhone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un perfil con este teléfono");
        }
        Profile profile = profileMapper.toEntity(dto);
        Profile saveProfile = profileRepository.save(profile);
        return profileMapper.toResponseDTO(saveProfile);
    }

    //LISTA PERFILES
    //Devuelve una lista con los datos de la base de datos y algunos atributos filtrados
    public List<ProfileResponseDTO> findAll() {
        return profileMapper.toResponseDTOList(profileRepository.findAll());
    }

    //BUSCAR PERFIL POR ID
    //Busca un perfil dado un ID y lo retorna
    private Profile findProfile(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil no encontrado"));
    }

    //RETORNA UN OBJETO TIPO "profile" Y LO CONVIERTE A "ProfileResponseDTO"
    //Retorna un perfil con algunos atributos filtrados
    public ProfileResponseDTO findById(Long id) {
        return profileMapper.toResponseDTO(findProfile(id));
    }

    //ACTUALIZA PERFIL
    public ProfileResponseDTO update(Long id, UpdateProfileDTO dto) {
        Profile profile = findProfile(id);
        if (dto.getPhone() != null && profileRepository.existsByPhoneAndIdNot(dto.getPhone(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El teléfono pertenece a otro perfil");
        }
        profileMapper.updateEntity(dto, profile);
        return profileMapper.toResponseDTO(profileRepository.save(profile));
    }

    public void delete(Long id) {
        profileRepository.delete(findProfile(id));
    }

}
