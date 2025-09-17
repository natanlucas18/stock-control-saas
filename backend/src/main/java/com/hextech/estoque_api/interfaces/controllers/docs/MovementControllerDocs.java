package com.hextech.estoque_api.interfaces.controllers.docs;

import com.hextech.estoque_api.interfaces.dtos.movements.MovementRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Movimentações", description = "Operações de Movimentações")
public interface MovementControllerDocs {

    @Operation(summary = "Gera um relatório de movimentações filtrando por data de início e fim (opcional), formato aceito: yyyy-MM-dd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    ResponseEntity<List<MovementResponseDTO>> reportMovements(String startDate, String endDate);

    @Operation(summary = "Cria uma nova movimentação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    ResponseEntity<MovementResponseDTO> createMovement(MovementRequestDTO requestDTO);
}
