package com.hextech.estoque_api.domain.entities.movement;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import com.hextech.estoque_api.domain.entities.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "movements")
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

    public Movement() {
    }

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
        if (product == null) throw new IllegalArgumentException("Produto inválido.");
        if (user == null) throw new IllegalArgumentException("Usuário inválido.");
        if (company == null) throw new IllegalArgumentException("Empresa inválida.");
        return new Movement(type, quantity, moment, note, product, user, company, fromStockLocation, toStockLocation);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public StockLocation getFromStockLocation() {
        return fromStockLocation;
    }

    public void setFromStockLocation(StockLocation fromStockLocation) {
        this.fromStockLocation = fromStockLocation;
    }

    public StockLocation getToStockLocation() {
        return toStockLocation;
    }

    public void setToStockLocation(StockLocation toStockLocation) {
        this.toStockLocation = toStockLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id) && type == movement.type && Objects.equals(quantity, movement.quantity) && Objects.equals(moment, movement.moment) && Objects.equals(note, movement.note) && Objects.equals(product, movement.product) && Objects.equals(user, movement.user) && Objects.equals(company, movement.company) && Objects.equals(fromStockLocation, movement.fromStockLocation) && Objects.equals(toStockLocation, movement.toStockLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, quantity, moment, note, product, user, company, fromStockLocation, toStockLocation);
    }
}
