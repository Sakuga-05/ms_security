package com.uc.ms_security.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProfileDTO extends BaseProfileDTO {

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no debe superar los 20 caracteres")
    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "El teléfono debe contener entre 7 y 15 dígitos, " +
            "puedes agregar el prefijo de tu pais de origen")
    private String phone;
}
