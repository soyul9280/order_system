package project.orderminisystem.domain.product.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import project.orderminisystem.domain.product.entity.Product;

@Builder
public record CreateProductResponse(
        Long id,
        String name,
        Integer price,
        Integer stock,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CreateProductResponse from(Product product) {
        return CreateProductResponse.builder()
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
