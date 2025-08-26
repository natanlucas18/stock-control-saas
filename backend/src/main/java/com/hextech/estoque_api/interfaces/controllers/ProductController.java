package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.ProductRequestDTO;
import com.hextech.estoque_api.application.dtos.ProductResponseDTO;
import com.hextech.estoque_api.application.services.ProductService;
import com.hextech.estoque_api.interfaces.controllers.docs.ProductControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/products", produces = "application/json")
public class ProductController implements ProductControllerDocs {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO responseDTO = service.insert(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(responseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO responseDTO = service.update(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
