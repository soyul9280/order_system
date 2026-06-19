package project.orderminisystem.domain.product.repository;

import java.time.LocalDateTime;
import java.util.List;
import project.orderminisystem.domain.product.entity.Product;
import project.orderminisystem.global.common.Direction;

public interface ProductCustomRepository {
    List<Product> searchProducts(
            String keyword, String orderBy, Direction direction, String cursor, LocalDateTime after, int limit);
}
