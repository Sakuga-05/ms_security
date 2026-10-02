package com.uc.ms_security.dto.session;

import lombok.Value;

import java.time.LocalDate;

@Value
public class SessionResponseDTO {
    Long id;
    LocalDate expiration;
}
