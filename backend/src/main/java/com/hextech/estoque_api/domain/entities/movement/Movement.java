package com.hextech.estoque_api.domain.entities.movement;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.entities.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "movements")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MovementType type;
    private BigDecimal quantity;
    private LocalDateTime moment;
    private String note;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(name = "from_stock_location_id")
    private StockLocation fromStockLocation;
    @ManyToOne
    @JoinColumn(name = "to_stock_location_id")
    private StockLocation toStockLocation;

    private Movement(MovementType type, BigDecimal quantity, LocalDateTime moment, String note, Product product, User user, Company company, StockLocation fromStockLocation, StockLocation toStockLocation) {
        this.type = type;
        this.quantity = quantity;
        this.moment = moment;
        this.note = note;
        this.product = product;
        this.user = user;
        this.company = company;
        this.fromStockLocation = fromStockLocation;
        this.toStockLocation = toStockLocation;
    }

    public static Movement createNewMovement(MovementType type, BigDecimal quantity , LocalDateTime moment, String note,
                                             Product product, User user, Company company, StockLocation fromStockLocation, StockLocation toStockLocation) {
        BigDecimal zero = BigDecimal.ZERO;
        if (!Arrays.stream(MovementType.values()).toList().contains(type)) throw new IllegalArgumentException("Tipo de movimentação inválida.");
        if (quantity == null || quantity.compareTo(zero) <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        if (moment == null) throw new IllegalArgumentException("Data de movimento inválida.");
        if (product == null || !product.getIsEnable()) throw new IllegalArgumentException("Produto inválido ou desabilitado.");
        if (user == null) throw new IllegalArgumentException("Usuário inválido.");
        if (company == null) throw new IllegalArgumentException("Empresa inválida.");
        return new Movement(type, quantity, moment, note, product, user, company, fromStockLocation, toStockLocation);
    }
}
