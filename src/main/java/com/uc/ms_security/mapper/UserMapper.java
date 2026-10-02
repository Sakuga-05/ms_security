package com.uc.ms_security.mapper;

import com.uc.ms_security.dto.user.CreateUserDTO;
import com.uc.ms_security.dto.user.UpdateUserDTO;
import com.uc.ms_security.dto.user.UserResponseDTO;
import com.uc.ms_security.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    //DEL FRONT AL BACK
    //De DTO a entidad
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

    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                //.map hace una transformación segun lo definido en el paraentesis
                .map(this::toResponseDTO)//Aquí, por cada usuario de la lista users se ejecuta toResponseDTO
                .toList();
    }

}