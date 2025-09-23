package com.hextech.estoque_api.interfaces.controllers.docs;

import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.users.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuários", description = "Operações de para Gerenciamento de usuários")
public interface UserControllerDocs {

    @Operation(summary = "Realiza o login de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> register(UserRequestDTO requestDTO);
}
