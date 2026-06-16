package project.orderminisystem.domain.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import project.orderminisystem.domain.product.command.UpdateProductCommand;

@Builder
public record UpdateProductRequest(
        @Schema(example = "무선 마우스 (리뉴얼)")
        @NotNull(message = "상품명은 필수입니다.")
        String name,

        @Schema(example = "27000")
        @NotNull(message = "상품 가격은 필수입니다.")
        Integer price,

        @Schema(example = "90")
        @NotNull(message = "재고 수량은 필수입니다.")
        Integer stock,

        @Schema(example = "인체공학적 무선 마우스 - 2세대")
        @Size(max = 400, message = "상품 설명은 500자 이하여야 합니다.")
        String description
) {
    public UpdateProductCommand toCommand(Long productId) {
        return UpdateProductCommand.builder()
                .id(productId)
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .build();
    }
}
