package com.hextech.estoque_api.interfaces.controllers;

import com.hextech.estoque_api.application.services.DashboardService;
import com.hextech.estoque_api.infrastructure.utils.AuthContext;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.dashboard.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/dashboard", produces = "application/json")
public class DashboardController {

    @Autowired
    private AuthContext authContext;
    @Autowired
    private DashboardService service;

    @GetMapping
    public ResponseEntity<StandardResponse<?>> getDashboard() {
        DashboardDTO response = service.getDashboard(authContext.getCurrentCompanyId());
        return ResponseEntity.ok(new StandardResponse<>(true, response));
    }
}
