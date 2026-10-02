package com.uc.ms_security.dto.user;

import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

//Consultar mejor esto: Los decoradores requieren de un campo....

@Getter
@Setter
public class UpdateUserDTO extends BaseUserDTO {

    //Como es opcional no tiene el decorador NotBlank
    @Size(  min = 8,
            message = "La contraseña debe tener mínimo 8 caracteres"
    )
    private String password;
}