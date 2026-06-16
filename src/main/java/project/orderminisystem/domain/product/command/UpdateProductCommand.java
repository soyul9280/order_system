package project.orderminisystem.domain.product.command;

import lombok.Builder;

@Builder
public record UpdateProductCommand(
        Long id,
        String name,
        Integer price,
        Integer stock,
        String description
) {
}
