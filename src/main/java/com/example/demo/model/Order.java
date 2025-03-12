package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
        // This constructor is required by Hibernate
    }
    public Order(Long id, String product, Double amount, User user) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public Double getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", amount=" + amount +
                ", user=" + (user != null ? user.getId() : "null") +
                '}';
    }
}
