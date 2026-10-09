package com.uc.ms_security.mapper;

import com.uc.ms_security.dto.user.CreateUserDTO;
import com.uc.ms_security.dto.user.UpdateUserDTO;
import com.uc.ms_security.dto.user.UserDetailResponseDTO;
import com.uc.ms_security.dto.user.UserResponseDTO;
import com.uc.ms_security.dto.user.UserRolesResponseDTO;
import com.uc.ms_security.dto.user.UserSessionsResponseDTO;
import com.uc.ms_security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    //Inyección de dependencia de ProfileMapper para poder usarlo en este mapper
    //UsserMapper necesita ProfileMapper para poder mapear el perfil del usuario a DTO
    private final ProfileMapper profileMapper;
    private final SessionMapper sessionMapper;
    private final UserRoleMapper userRoleMapper;

    //DEL BACK AL FRONT
    // De entidad a DTO
    public User toEntity(CreateUserDTO dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    public void updateEntity(UpdateUserDTO dto, User user) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
    }

    //DEL BACK AL FRONT
    // De entidad a DTO
    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(), //Aquí deberia mostrarse el id?
                user.getName(),
                user.getEmail()
        );
    }

    public UserDetailResponseDTO toDetailResponseDTO(User user) {
        return new UserDetailResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                profileMapper.toResponseDTO(user.getProfile())
        );
    }

    public UserSessionsResponseDTO toSessionsResponseDTO(User user) {
        return new UserSessionsResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                sessionMapper.toResponseDTOList(user.getSessions())
        );
    }

    public UserRolesResponseDTO toRolesResponseDTO(User user) {
        return new UserRolesResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                userRoleMapper.toResponseDTOList(user.getUserRoles())
        );
    }

    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                //.map hace una transformación segun lo definido en el paraentesis
                .map(this::toResponseDTO)//Aquí, por cada usuario de la lista users se ejecuta toResponseDTO
                .toList();
    }

}