package project.orderminisystem.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.orderminisystem.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>,ProductCustomRepository {

}
