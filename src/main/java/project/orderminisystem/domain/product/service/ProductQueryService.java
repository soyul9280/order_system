package project.orderminisystem.domain.product.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.orderminisystem.domain.product.dto.response.CursorPageProductResponse;
import project.orderminisystem.domain.product.dto.response.ProductItemResponse;
import project.orderminisystem.domain.product.entity.Product;
import project.orderminisystem.domain.product.repository.ProductRepository;
import project.orderminisystem.global.common.Direction;
import project.orderminisystem.global.error.BusinessException;
import project.orderminisystem.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {
    private final ProductRepository productRepository;

    public ProductItemResponse getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        return ProductItemResponse.builder()
                .id(product.getId())
                .name(product.getItemName())
                .stock(product.getStock())
                .price(product.getPrice())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public CursorPageProductResponse getProducts(
            String keyword,
            String orderBy,
            Direction direction,
            String cursor,
            LocalDateTime after,
            int limit
    ) {
        List<Product> products = productRepository.searchProducts(keyword, orderBy, direction, cursor, after, limit+1);
        boolean hasNext = products.size() > limit;
        if (hasNext) {
            products = products.subList(0, limit);
        }
        String nextCursor = null;
        LocalDateTime nextAfter = null;
        if (!products.isEmpty()) {
            Product last = products.get(products.size() - 1);
            nextAfter = last.getCreatedAt();

            nextCursor =
                    switch (orderBy) {
                        case "itemName" -> last.getItemName();
                        default -> null;
                    };
        }
        List<Long> productIds = products.stream().map(Product::getId).toList();

        List<ProductItemResponse> content =
                products.stream()
                        .map(
                                product -> {
                                    return ProductItemResponse.builder()
                                            .id(product.getId())
                                            .name(product.getItemName())
                                            .stock(product.getStock())
                                            .price(product.getPrice())
                                            .description(product.getDescription())
                                            .createdAt(product.getCreatedAt())
                                            .updatedAt(product.getUpdatedAt())
                                            .build();
                                })
                        .toList();

        return new CursorPageProductResponse(
                content, nextCursor, nextAfter, limit, content.size(), hasNext);
    }
}
