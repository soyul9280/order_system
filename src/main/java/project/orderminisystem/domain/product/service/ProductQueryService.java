package project.orderminisystem.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.orderminisystem.domain.product.dto.response.ProductItemResponse;
import project.orderminisystem.domain.product.entity.Product;
import project.orderminisystem.domain.product.repository.ProductRepository;
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
}
