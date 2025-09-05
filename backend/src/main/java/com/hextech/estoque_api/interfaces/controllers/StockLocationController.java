package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.stockLocations.StockLocationDTO;
import com.hextech.estoque_api.application.services.StockLocationService;
import com.hextech.estoque_api.interfaces.controllers.docs.StockLocationControllerDocs;
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

    @GetMapping
    public ResponseEntity<List<StockLocationDTO>> findAllByCompany() {
        List<StockLocationDTO> responseDTO = service.findAllByCompanyId();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StockLocationDTO> findById(@PathVariable Long id) {
        StockLocationDTO responseDTO = service.findByIdAndCompanyId(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<StockLocationDTO> insert(@RequestBody StockLocationDTO requestDTO) {
        StockLocationDTO responseDTO = service.insert(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(responseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StockLocationDTO> update(@PathVariable Long id, @RequestBody StockLocationDTO requestDTO) {
        StockLocationDTO responseDTO = service.update(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteByIdAndCompanyId(id);
        return ResponseEntity.noContent().build();
    }
}
