package project.orderminisystem.domain.product.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ProductItemResponse(
        Long id,
        String name,
        Integer price,
        Integer stock,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
