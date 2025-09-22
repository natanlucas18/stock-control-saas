package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.StockLocationService;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.controllers.docs.StockLocationControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockLocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/stock-locations", produces = "application/json")
public class StockLocationController implements StockLocationControllerDocs {

    @Autowired
    private StockLocationService service;
    @Autowired
    private AuthContext authContext;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> findAllByCompany() {
        List<StockLocationDTO> response = service.findAllByCompanyId(authContext.getCurrentCompanyId());
        return ResponseEntity.ok().body(new StandardResponse<>(true, response));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StandardResponse<?>> findById(@PathVariable Long id) {
        StockLocationDTO response = service.findByIdAndCompanyId(id, authContext.getCurrentCompanyId());
        return ResponseEntity.ok().body(new StandardResponse<>(true, response));
    }

    @PostMapping
    public ResponseEntity<StandardResponse<?>> insert(@RequestBody StockLocationDTO requestDTO) {
        StockLocationDTO response = service.insert(requestDTO, authContext.getCurrentCompanyId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(new StandardResponse<>(true, response));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StandardResponse<?>> update(@PathVariable Long id, @RequestBody StockLocationDTO requestDTO) {
        StockLocationDTO response = service.update(id, requestDTO, authContext.getCurrentCompanyId());

        return ResponseEntity.ok(new StandardResponse<>(true, response));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StandardResponse<Void>> deleteById(@PathVariable Long id) {
        service.deleteByIdAndCompanyId(id, authContext.getCurrentCompanyId());
        return ResponseEntity.ok(new StandardResponse<>(true, null));
    }
}
