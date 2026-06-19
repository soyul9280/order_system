package project.orderminisystem.domain.order.command;

import lombok.Builder;

@Builder
public record CreateOrderCommand(
        Long productId,
        Integer quantity
) {
}
