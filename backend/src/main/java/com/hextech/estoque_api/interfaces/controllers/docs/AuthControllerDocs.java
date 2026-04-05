package com.hextech.estoque_api.interfaces.controllers.docs;

import com.fasterxml.jackson.databind.JsonNode;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = "Operações de Autenticação")
public interface AuthControllerDocs {

    @Operation(summary = "Retorna a sessão do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    ResponseEntity<?> getCurrentSession(HttpServletRequest request);


    @Operation(summary = "Realiza o login de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> login(AccountCredentialsDTO credentials, HttpServletResponse response);

    @Operation(summary = "Realiza o refresh de um token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> refreshToken(JsonNode body, HttpServletResponse response, HttpServletRequest request);

    @Operation(summary = "Finaliza a sessão do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            })
    void logout(HttpServletResponse response);
}
