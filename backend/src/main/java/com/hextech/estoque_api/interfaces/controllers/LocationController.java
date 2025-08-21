package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.dtos.LocationDTO;
import com.hextech.estoque_api.application.usecases.LocationService;
import com.hextech.estoque_api.interfaces.controllers.docs.LocationControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/locations", produces = "application/json")
public class LocationController implements LocationControllerDocs {

    @Autowired
    private LocationService service;

    @PostMapping
    public ResponseEntity<LocationDTO> insert(@RequestBody LocationDTO requestDTO) {
        LocationDTO responseDTO = service.insert(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(responseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
