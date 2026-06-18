package project.orderminisystem.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import project.orderminisystem.domain.order.command.CreateOrderCommand;

@Builder
public record CreateOrderRequest(
        @Schema(example = "1")
        @NotNull(message = "상품Id는 필수입니다.")
        Long productId,

        @Schema(example = "2")
        @Min(value = 1, message = "최소 주문 수량은 1입니다.")
        @NotNull(message = "주문 수량은 필수입니다.")
        Integer quantity
) {
    public CreateOrderCommand toCommand() {
        return CreateOrderCommand.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
