package com.uc.ms_security.dto.session;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateSessionDTO extends BaseSessionDTO {

    @NotBlank(message = "El código de Verificación en dos pasos es obligatorio")
    @Size(min = 6, max = 6, message = "El código 2FA debe tener exactamente 6 dígitos")
    @Pattern(regexp = "[0-9]{6}", message = "El código 2FA debe contener únicamente dígitos")
    private String code2FA;

}
