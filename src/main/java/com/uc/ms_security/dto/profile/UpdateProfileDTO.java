package com.uc.ms_security.dto.profile;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDTO extends BaseProfileDTO {

    @Size(max = 20, message = "El teléfono no debe superar los 20 caracteres")
    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "El teléfono debe contener entre 7 y 15 dígitos, con + opcional al inicio")
    private String phone;
}
