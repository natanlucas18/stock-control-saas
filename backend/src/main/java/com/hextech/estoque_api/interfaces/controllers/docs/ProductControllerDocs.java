package com.hextech.estoque_api.interfaces.controllers.docs;

import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.products.ProductRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Produtos", description = "Operações de Produtos")
public interface ProductControllerDocs {

    @Operation(summary = "Busca todos produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> findAll();

    @Operation(summary = "Busca um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> findById(Long id);

    @Operation(summary = "Adiciona um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> insert(ProductRequestDTO requestDTO);

    @Operation(summary = "Atualiza um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    ResponseEntity<StandardResponse<?>> update(Long id, ProductRequestDTO requestDTO);

    @Operation(summary = "Deleta um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = StandardResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)
    })
    ResponseEntity<StandardResponse<Void>> deleteById(Long id);
}
