package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.StockLocationDTO;
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
    public ResponseEntity<List<StockLocationDTO>> findAllByClient() {
        List<StockLocationDTO> responseDTO = service.findAllByClientId();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<StockLocationDTO> insert(@RequestBody StockLocationDTO requestDTO) {
        StockLocationDTO responseDTO = service.insert(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(responseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
