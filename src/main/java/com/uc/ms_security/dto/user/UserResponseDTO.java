package com.uc.ms_security.dto.user;

import lombok.Value;

//Lo que hace es que cuando se haga un getter o se mande una respuesta no muestre la contraseña.
@Value
public class UserResponseDTO {
    Long id;
    String name;
    String email;
}