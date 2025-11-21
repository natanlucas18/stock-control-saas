package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.UserService;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.controllers.docs.UserControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.users.UserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UserController implements UserControllerDocs {

    @Autowired
    private UserService service;
    @Autowired
    private AuthContext authContext;

    @PostMapping("/register")
    public ResponseEntity<StandardResponse<?>> register(@RequestBody @Valid UserRequestDTO requestDTO) {

        var response = service.createNewUser(requestDTO, authContext.getCurrentCompanyId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponse<>(true, response));
    }
}
