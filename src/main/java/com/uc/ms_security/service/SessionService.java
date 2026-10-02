package com.uc.ms_security.service;

import com.uc.ms_security.dto.session.CreateSessionDTO;
import com.uc.ms_security.dto.session.SessionResponseDTO;
import com.uc.ms_security.dto.session.UpdateSessionDTO;
import com.uc.ms_security.entity.Session;
import com.uc.ms_security.exception.ApplicationException;
import com.uc.ms_security.exception.ErrorCase;
import com.uc.ms_security.mapper.SessionMapper;
import com.uc.ms_security.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    //CREAR SESSION
    public SessionResponseDTO create(CreateSessionDTO dto) {
        if (sessionRepository.existsByToken(dto.getToken())) {
            throw new ApplicationException(ErrorCase.ALREADY_EXISTS, "Ya existe una sesión con este token");
        }
        Session session = sessionMapper.toEntity(dto);
        Session saveSession = sessionRepository.save(session);
        return sessionMapper.toResponseDTO(saveSession);
    }

    //LISTAR SESSIONS
    public List<SessionResponseDTO> findAll() {
        return sessionMapper.toResponseDTOList(sessionRepository.findAll());
    }

    //BUSCA UN SESSION DADO UN ID
    private Session findSession(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Sesión no encontrada con id: " + id
                ));
    }

    //CONVIERTE UN DATO TIPO "Session" A TIPO "SessionResponseDTO"
    public SessionResponseDTO findById(Long id) {
        return sessionMapper.toResponseDTO(findSession(id));
    }

    //ACTUALIZAR UNA SESSION
    public SessionResponseDTO update(Long id, UpdateSessionDTO dto) {
        Session session = findSession(id);

        if (sessionRepository.existsByTokenAndIdNot(dto.getToken(), id)) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "El token pertenece a otra sesión"
            );
        }
        sessionMapper.updateEntity(dto, session);
        return sessionMapper.toResponseDTO(sessionRepository.save(session));
    }

    //ELIMINA UNA SESSION
    public void delete(Long id) {
        sessionRepository.delete(findSession(id));
    }

}
