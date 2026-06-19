package project.orderminisystem.domain.order.dto.response;

import lombok.Builder;
import project.orderminisystem.domain.order.entity.Order;
import project.orderminisystem.domain.order.entity.Status;
import project.orderminisystem.domain.product.entity.Product;

import java.time.LocalDateTime;

@Builder
public record OrderResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        Integer orderPrice,
        Integer totalPrice,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static OrderResponse from(Order order, Product product, Integer totalPrice) {
        return OrderResponse.builder()
                .id(order.getId())
                .productId(product.getId())
                .productName(product.getItemName())
                .quantity(order.getQuantity())
                .orderPrice(order.getOrderPrice())
                .totalPrice(totalPrice)
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}