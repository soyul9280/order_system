package project.orderminisystem.domain.product.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import project.orderminisystem.domain.product.entity.Product;

@Builder
public record UpdateProductResponse(
        Long id,
        String name,
        Integer price,
        Integer stock,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UpdateProductResponse from(Product product) {
        return UpdateProductResponse.builder()
                .id(product.getId())
                .name(product.getItemName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}

