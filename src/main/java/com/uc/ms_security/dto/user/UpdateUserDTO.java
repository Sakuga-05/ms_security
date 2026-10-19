package com.uc.ms_security.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

//Consultar mejor esto: Los decoradores requieren de un campo....

@Getter
@Setter
public class UpdateUserDTO extends BaseUserDTO {

    //Como es opcional no tiene el decorador NotBlank
    @Size(
            min = 8,
            max = 72,
            message = "La contraseña debe tener entre 8 y 72 caracteres"
    )
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])\\S{8,72}$",
            message = "La contraseña debe contener al menos una mayúscula, una minúscula, un número y un carácter especial, sin espacios"
    )
    private String password;
}