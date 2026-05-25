package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.entities.movement.Movement;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.infrastructure.repositories.MovementRepository;
import com.hextech.estoque_api.infrastructure.repositories.ProductRepository;
import com.hextech.estoque_api.interfaces.dtos.dashboard.DashboardDTO;
import com.hextech.estoque_api.interfaces.dtos.dashboard.TopMovingProducts;
import com.hextech.estoque_api.interfaces.dtos.movements.MovementMinDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResumeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MovementRepository movementRepository;

    @Transactional(readOnly = true)
    public DashboardDTO getDashboard(Long companyId) {

        Long totalProducts = productRepository.countByCompanyId(companyId);
        Long lowStockCount = productRepository.countByCompanyIdAndStockStatus(companyId, "LOW");
        List<Product> lowProducts = productRepository
                .findTop5ByStockStatus(companyId, "LOW", PageRequest.of(0, 5));
        Long totalMovements = movementRepository.countByCompanyId(companyId);
        List<Movement> recentMovements = movementRepository.findTop5ByCompanyIdOrderByMomentDesc(companyId);
        List<TopMovingProducts> topMovingProducts = movementRepository
                .findMostMovedProducts(companyId, PageRequest.of(0, 5));

        return new DashboardDTO(totalProducts, lowStockCount, totalMovements,
                recentMovements.stream().map(MovementMinDTO::new).toList(), topMovingProducts,
                lowProducts.stream().map(ProductResumeDTO::new).toList());
    }
}
