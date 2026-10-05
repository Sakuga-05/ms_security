package com.uc.ms_security.service;

import com.uc.ms_security.dto.session.CreateSessionDTO;
import com.uc.ms_security.dto.session.UpdateSessionDTO;
import com.uc.ms_security.dto.session.SessionResponseDTO;
import com.uc.ms_security.entity.Session;
import com.uc.ms_security.entity.User;
import com.uc.ms_security.exception.ApplicationException;
import com.uc.ms_security.exception.ErrorCase;
import com.uc.ms_security.mapper.SessionMapper;
import com.uc.ms_security.repository.SessionRepository;
import com.uc.ms_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SessionMapper sessionMapper;

    //CREAR UNA NUEVA SESSION PARA UN USUARIO
    public SessionResponseDTO create(Long userId, CreateSessionDTO dto) {
        User user = findUser(userId);

        if (sessionRepository.existsByToken(dto.getToken())) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "El token ya está registrado"
            );
        }

        Session session = sessionMapper.toEntity(dto);
        session.setUser(user);
        Session saveSession = sessionRepository.save(session);
        return sessionMapper.toResponseDTO(saveSession);
    }

    //LISTAR TODAS LAS SESSIONES DE UN USUARIO
    public List<SessionResponseDTO> findAllByUserId(Long userId) {
        findUser(userId);
        return sessionMapper.toResponseDTOList(
                sessionRepository.findAllByUserId(userId)
        );
    }

    //LISTAR SESSION POR ID Y USERID
    public SessionResponseDTO findById(Long userId, Long sessionId) {
        return sessionMapper.toResponseDTO(findSession(userId, sessionId));
    }

    //ACTUALIZAR SESSION
    public SessionResponseDTO update(Long userId,Long sessionId, UpdateSessionDTO dto) {
        findUser(userId);
        Session session = findSession(userId, sessionId);
        if (sessionRepository.existsByTokenAndIdNot(dto.getToken(), sessionId)) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "El token ya está registrado"
            );
        }

        sessionMapper.updateEntity(dto, session);
        Session updatedSession = sessionRepository.save(session);
        return sessionMapper.toResponseDTO(updatedSession);
    }

    //ELIMINAR SESSION
    public void delete(Long userId, Long sessionId) {
        sessionRepository.delete(findSession(userId, sessionId));
    }

    //VALIDAR SI EXISTE EL USUARIO Y LA SESSION, SI NO EXISTE LANZA UNA EXCEPCION
    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Usuario no encontrado con id: " + userId
                ));
    }

    //VALIDAR SI EXISTE LA SESSION PARA EL USUARIO, SI NO EXISTE LANZA UNA EXCEPCION
    private Session findSession(Long userId, Long sessionId) {
        return sessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Sesión no encontrada para este usuario"
                ));
    }
}
