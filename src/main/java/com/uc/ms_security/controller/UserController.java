package com.uc.ms_security.controller;

import com.uc.ms_security.dto.user.CreateUserDTO;
import com.uc.ms_security.dto.user.UpdateUserDTO;
import com.uc.ms_security.dto.user.UserDetailResponseDTO;
import com.uc.ms_security.dto.user.UserResponseDTO;
import com.uc.ms_security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

//Esto son los CRUDS que llamara el Front y donde se implementa la lógica
//que se programo en UserService
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@RequestBody --> Es donde vienen los datos de la petición
    public UserResponseDTO create(@Valid @RequestBody CreateUserDTO dto) {
        return userService.create(dto);
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll(); //Aquí entra la paginación (consultarla)
    }

    @GetMapping("/{id}")
    public UserDetailResponseDTO findById(@PathVariable Long id) {
        return userService.findByIdAndProfile(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(
            @PathVariable Long id, //Lo arrastro de la ruta al metodo
            @Valid @RequestBody UpdateUserDTO dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}