package project.orderminisystem.domain.order.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CursorPageOrderResponse(
        List<OrderResponse> content,
        String nextCursor,
        LocalDateTime nextAfter,
        int size,
        int totalElements,
        Boolean hasNext
) {
}
