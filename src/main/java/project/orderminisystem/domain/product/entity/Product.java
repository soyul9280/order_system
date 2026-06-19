package project.orderminisystem.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.orderminisystem.global.common.BaseTimeEntity;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    private String description;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;


    @Builder
    public Product(
            Long id,
            String itemName,
            Integer price,
            Integer stock,
            String description
    ) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public void updateDescription(String description) {
        if (description != null) {
            String normalized = description.trim();
            if (!Objects.equals(this.description, normalized)) {
                this.description = normalized;
            }
        }
    }

    public void updateItemName(String name) {
        if (name != null) {
            this.itemName = name;
        }
    }

    public void updateStock(Integer stock) {
        if (stock != null) {
            this.stock = stock;
        }
    }

    public void updatePrice(Integer price) {
        if (price != null) {
            this.price = price;
        }
    }

    public void logicallyDelete() {
        this.isDeleted = true;
    }
}
