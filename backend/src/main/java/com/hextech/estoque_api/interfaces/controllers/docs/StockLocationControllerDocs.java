package com.hextech.estoque_api.interfaces.controllers.docs;

import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Localização de estoque", description = "Operações de locais armazenamento de produtos")
public interface StockLocationControllerDocs {

    @Operation(summary = "Busca todos os locais de armazenamento da empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    ResponseEntity<StandardResponse<?>> findAllByCompanyPaged(Pageable pageable);

    @Operation(summary = "Busca um local de armazenamento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> findById(Long id);

    @Operation(summary = "Adiciona um novo local de armazenamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> insert(StockLocationDTO StockLocationDTO);

    @Operation(summary = "Atualiza um local de armazenamento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> update(Long id, StockLocationDTO StockLocationDTO);

    @Operation(summary = "Deleta um local de armazenamento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No Content", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    ResponseEntity<StandardResponse<Void>> deleteById(Long id);
}
