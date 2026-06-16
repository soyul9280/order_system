package project.orderminisystem.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.orderminisystem.domain.product.command.CreateProductCommand;
import project.orderminisystem.domain.product.command.UpdateProductCommand;
import project.orderminisystem.domain.product.dto.response.CreateProductResponse;
import project.orderminisystem.domain.product.dto.response.UpdateProductResponse;
import project.orderminisystem.domain.product.entity.Product;
import project.orderminisystem.domain.product.repository.ProductRepository;
import project.orderminisystem.global.error.BusinessException;
import project.orderminisystem.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public CreateProductResponse create(CreateProductCommand command) {
        Product product = Product.builder()
                .itemName(command.name())
                .price(command.price())
                .stock(command.stock())
                .description(command.description())
                .build();

        Product saved = productRepository.save(product);
        return CreateProductResponse.from(saved);
    }

    public UpdateProductResponse update(UpdateProductCommand command) {
        Product product = productRepository.findById(command.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        product.updateItemName(command.name());
        product.updatePrice(command.price());
        product.updateStock(command.stock());
        product.updateDescription(command.description());

        return UpdateProductResponse.from(product);
    }

    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
