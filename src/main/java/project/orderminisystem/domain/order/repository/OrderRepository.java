package project.orderminisystem.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.orderminisystem.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
