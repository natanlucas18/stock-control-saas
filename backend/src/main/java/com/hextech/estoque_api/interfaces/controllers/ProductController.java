package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.ProductService;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.controllers.docs.ProductControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.products.ProductRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResponseDTO;
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
    private AuthContext authContext;
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> findAll() {
        List<ProductResponseDTO> response = service.findAllByCompanyId(authContext.getCurrentCompanyId());
        return ResponseEntity.ok(new StandardResponse<>(true, response));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StandardResponse<?>> findById(@PathVariable Long id) {
        ProductResponseDTO response = service.findByIdAndCompanyId(id, authContext.getCurrentCompanyId());
        return ResponseEntity.ok(new StandardResponse<>(true, response));
    }

    @PostMapping
    public ResponseEntity<StandardResponse<?>> insert(@RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO response = service.insert(requestDTO, authContext.getCurrentCompanyId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(new StandardResponse<>(true, response));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StandardResponse<?>> update(@PathVariable Long id, @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO response = service.update(id, requestDTO, authContext.getCurrentCompanyId());
        return ResponseEntity.ok(new StandardResponse<>(true, response));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StandardResponse<Void>> deleteById(@PathVariable Long id) {
        service.deleteByIdAndCompanyId(id, authContext.getCurrentCompanyId());
        return ResponseEntity.ok(new StandardResponse<>(true, null));
    }
}
