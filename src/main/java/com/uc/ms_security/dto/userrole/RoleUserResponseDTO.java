package com.uc.ms_security.dto.userrole;

import com.uc.ms_security.dto.user.UserResponseDTO;
import lombok.Value;

@Value
public class RoleUserResponseDTO { //Funciona para mirar el usuario de un rol
    Long id;
    Long roleId;
    UserResponseDTO user;
}
