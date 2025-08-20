package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.ProductDTO;
import com.hextech.estoque_api.application.usecases.ProductService;
import com.hextech.estoque_api.interfaces.controllers.docs.ProductControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
public class ProductController implements ProductControllerDocs {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        dto = service.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }
}
