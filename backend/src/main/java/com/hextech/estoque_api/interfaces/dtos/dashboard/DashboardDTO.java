package com.hextech.estoque_api.interfaces.dtos.dashboard;

import com.hextech.estoque_api.interfaces.dtos.movements.MovementMinDTO;
import com.hextech.estoque_api.interfaces.dtos.products.ProductResumeDTO;

import java.util.List;

public record DashboardDTO(Long totalProducts, Long lowStockCount, Long totalMovements,
                           List<MovementMinDTO> recentMovements, List<TopMovingProducts> topMovingProducts,
                           List<ProductResumeDTO> lowStockProducts) {
}
