package project.orderminisystem.domain.order.repository;

import project.orderminisystem.domain.order.entity.Order;
import project.orderminisystem.global.common.Direction;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderCustomRepository {
    List<Order> searchOrders(
            String orderBy, Direction direction, String cursor, LocalDateTime after, int limit);
}
