package project.orderminisystem.domain.product.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CursorPageProductResponse(
        List<ProductItemResponse> content,
        String nextCursor,
        LocalDateTime nextAfter,
        int size,
        int totalElements,
        Boolean hasNext
) {
}
