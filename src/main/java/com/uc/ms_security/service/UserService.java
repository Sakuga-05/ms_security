package com.uc.ms_security.service;

import com.uc.ms_security.dto.user.CreateUserDTO;
import com.uc.ms_security.dto.user.UpdateUserDTO;
import com.uc.ms_security.dto.user.UserDetailResponseDTO;
import com.uc.ms_security.dto.user.UserResponseDTO;
import com.uc.ms_security.dto.user.UserRolesResponseDTO;
import com.uc.ms_security.dto.user.UserSessionsResponseDTO;
import com.uc.ms_security.entity.User;
import com.uc.ms_security.exception.ApplicationException;
import com.uc.ms_security.exception.ErrorCase;
import com.uc.ms_security.mapper.UserMapper;
import com.uc.ms_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//Aquí se programa la lógica de los CRUDS
@Service //permite inyección de dependencias
@RequiredArgsConstructor //Pide que le mande los datos
public class UserService {

    //Es como una relación 1 a 1. UserService depende de UserRepository
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    //CREAR USUARIO
    public UserResponseDTO create(CreateUserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "El correo ya está registrado"
            );
        }

        String encodedPassword = passwordEncoder.encode(
                dto.getPassword()
        );

        User user = userMapper.toEntity(
                dto,
                encodedPassword
        );

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);
    }

    //LISTAR USUARIOS
    public List<UserResponseDTO> findAll() {
        //.finAll() = buscame todos los registros correspondientes a esta entidad.
        List<User> users = userRepository.findAll();
        return userMapper.toResponseDTOList(users);
    }

    //BUSCAR USUARIO POR ID
    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Usuario no encontrado con id: " + id
                ));
    }

    //BUSCA UN OBJETO TIPO "User" Y LO TRANSFORMA A TIPO "UserDetailResponseDTO"
    public UserDetailResponseDTO findByIdAndProfile(Long id) {
        User user = userRepository.findWithProfileById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Usuario no encontrado con id: " + id
                ));

        return userMapper.toDetailResponseDTO(user);
    }

    //BUSCA UN OBJETO TIPO "User" Y LO TRANSFORMA A TIPO "UserSessionsResponseDTO"
    public UserSessionsResponseDTO findByIdAndSessions(Long id) {
        User user = userRepository.findWithSessionsById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Usuario no encontrado con id: " + id
                ));

        return userMapper.toSessionsResponseDTO(user);
    }

    //BUSCA UN OBJETO TIPO "User" Y LO TRANSFORMA A TIPO "UserRolesResponseDTO"
    public UserRolesResponseDTO findByIdAndRoles(Long id) {
        User user = userRepository.findWithRolesById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Usuario no encontrado con id: " + id
                ));

        return userMapper.toRolesResponseDTO(user);
    }

    //ACTUALIZAR
    public UserResponseDTO update(Long id, UpdateUserDTO dto) {
        User user = findUser(id);
        if (userRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "El email pertenece a otro usuario"
            );
        }
        //No necesito guardarlo en una variable ya que en updateEntity se actualiza por referencia
        userMapper.updateEntity(dto, user);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    //ELIMINAR USUARIO
    public void delete(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }
}