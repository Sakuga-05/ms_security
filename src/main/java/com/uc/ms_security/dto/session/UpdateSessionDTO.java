package com.uc.ms_security.dto.session;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSessionDTO extends BaseSessionDTO {

    @Size(min = 6, max = 10, message = "El código 2FA debe tener entre 6 y 10 caracteres")
    @Pattern(regexp = "[0-9]+", message = "El código 2FA debe contener únicamente dígitos")
    private String code2FA;
}
