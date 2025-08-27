package com.hextech.estoque_api.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

import static com.hextech.estoque_api.domain.entities.MovementType.ENTRADA;
import static com.hextech.estoque_api.domain.entities.MovementType.SAIDA;

@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private MovementType type;
    private Integer quantity;
    private LocalDate moment;
    private String note;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "stock_location_id", nullable = false)
    private StockLocation stockLocation;

    public Movement() {
    }

    public Movement(MovementType type, Integer quantity ,LocalDate moment, String note,
                    Product product, User user, Client client, StockLocation stockLocation) {
        if (type != ENTRADA && type != SAIDA) throw new IllegalArgumentException("Tipo de movimentação inválida.");
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        if (moment == null) throw new IllegalArgumentException("Data de movimento inválida.");
        if (product == null) throw new IllegalArgumentException("Produto inválido.");
        if (user == null) throw new IllegalArgumentException("Usuário inválido.");
        if (client == null) throw new IllegalArgumentException("Cliente inválido.");
        if (stockLocation == null) throw new IllegalArgumentException("Local de estoque inválido.");

        this.type = type;
        this.quantity = quantity;
        this.moment = moment;
        this.note = note;
        this.product = product;
        this.user = user;
        this.client = client;
        this.stockLocation = stockLocation;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getMoment() {
        return moment;
    }

    public void setMoment(LocalDate moment) {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public StockLocation getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocation stockLocation) {
        this.stockLocation = stockLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id) && Objects.equals(type, movement.type) && Objects.equals(quantity, movement.quantity) && Objects.equals(moment, movement.moment) && Objects.equals(note, movement.note) && Objects.equals(product, movement.product) && Objects.equals(user, movement.user) && Objects.equals(client, movement.client) && Objects.equals(stockLocation, movement.stockLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, quantity, moment, note, product, user, client, stockLocation);
    }
}
