package com.uc.ms_security.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseUserDTO {

    //Validación de campos
    @NotBlank( //El campo no puede estar en blanco
            message = "El nombre es obligatorio"
    )
    @Size( //Cantidad de caracteres que debe tener el nombre
            min = 2,
            max = 100,
            message = "El nombre debe tener entre 2 y 100 caracteres"
    )
    private String name;

    @NotBlank( //El campo no puede estar en blanco
            message = "El email es obligatorio"
    )
    @Email( //Valida que el correo tenga formato validp
            message = "El email no tiene un formato válido"
    )
    private String email;
}
