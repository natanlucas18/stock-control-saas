package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.UnitMeasureService;
import com.hextech.estoque_api.infrastructure.security.utils.AuthContext;
import com.hextech.estoque_api.interfaces.controllers.docs.UnitMeasureControllerDocs;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PageMetadata;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.PaginatedResponse;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.unitMeasure.UnitMeasureRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/unit-measures", produces = "application/json")
public class UnitMeasureController implements UnitMeasureControllerDocs {

    @Autowired
    private AuthContext authContext;
    @Autowired
    private UnitMeasureService service;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> findAllPaged(@RequestParam(value = "query", defaultValue = "") String query, Pageable pageable) {
        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        Pageable adjustedPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());
        var response = service.findAllByCompanyId(query, authContext.getCurrentCompanyId(), adjustedPageable);

        List<?> content = response.getContent();
        PageMetadata pageMetadata = new PageMetadata(response);

        PaginatedResponse<?> paginatedResponse = new PaginatedResponse<>(content, pageMetadata);
        return ResponseEntity.ok(new StandardResponse<>(true, paginatedResponse));
    }

    @PostMapping
    public ResponseEntity<StandardResponse<?>> insert(@RequestBody @Valid UnitMeasureRequestDTO requestDTO) {
        var response = service.insert(requestDTO, authContext.getCurrentCompanyId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(new StandardResponse<>(true, response));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StandardResponse<?>> update(@PathVariable Long id, @RequestBody @Valid UnitMeasureRequestDTO request) {
        var response = service.update(id, request, authContext.getCurrentCompanyId());
        return ResponseEntity.ok(new StandardResponse<>(true, response));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StandardResponse<?>> deleteById(@PathVariable Long id) {
        service.deleteByIdAndCompanyId(id, authContext.getCurrentCompanyId());
        return ResponseEntity.ok(new StandardResponse<>(true, null));
    }
}
