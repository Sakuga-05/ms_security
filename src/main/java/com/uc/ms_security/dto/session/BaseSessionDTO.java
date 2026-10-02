package com.uc.ms_security.dto.session;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class BaseSessionDTO {

    @NotBlank(message = "El token es obligatorio")
    @Size(max = 2048, message = "El token no debe superar los 2048 caracteres")
    private String token;

    @NotNull(message = "La fecha de expiración es obligatoria")
    @Future(message = "La fecha de expiración debe ser superior a hoy")
    private LocalDate expiration;
}
