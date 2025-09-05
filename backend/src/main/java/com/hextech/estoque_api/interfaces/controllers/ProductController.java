package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.products.ProductRequestDTO;
import com.hextech.estoque_api.application.dtos.products.ProductResponseDTO;
import com.hextech.estoque_api.application.services.ProductService;
import com.hextech.estoque_api.interfaces.controllers.docs.ProductControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products", produces = "application/json")
public class ProductController implements ProductControllerDocs {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> responseDTOS = service.findAllByCompanyId();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductResponseDTO responseDTO = service.findByIdAndCompanyId(id);
        return ResponseEntity.ok(responseDTO);
    }

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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteByIdAndCompanyId(id);
        return ResponseEntity.noContent().build();
    }
}
