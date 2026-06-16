package project.orderminisystem.domain.product.command;

import lombok.Builder;

@Builder
public record CreateProductCommand(
        String name,
        Integer price,
        Integer stock,
        String description
) {
}
