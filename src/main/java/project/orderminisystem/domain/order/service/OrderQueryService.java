package project.orderminisystem.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.orderminisystem.domain.order.dto.response.CursorPageOrderResponse;
import project.orderminisystem.domain.order.dto.response.OrderResponse;
import project.orderminisystem.domain.order.entity.Order;
import project.orderminisystem.domain.order.repository.OrderRepository;
import project.orderminisystem.domain.product.entity.Product;
import project.orderminisystem.domain.product.repository.ProductRepository;
import project.orderminisystem.global.common.Direction;
import project.orderminisystem.global.error.BusinessException;
import project.orderminisystem.global.error.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        Product product = productRepository.findById(order.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        int totalPrice = order.getQuantity() * order.getOrderPrice();
        return OrderResponse.from(order, product, totalPrice);
    }

    public CursorPageOrderResponse getOrders(
            String orderBy,
            Direction direction,
            String cursor,
            LocalDateTime after,
            int limit
    ) {
        List<Order> orders = orderRepository.searchOrders(orderBy, direction, cursor, after, limit+1);
        boolean hasNext = orders.size() > limit;
        if (hasNext) {
            orders = orders.subList(0, limit);
        }
        String nextCursor = null;
        LocalDateTime nextAfter = null;
        if (!orders.isEmpty()) {
            Order last = orders.get(orders.size() - 1);
            nextAfter = last.getCreatedAt();
        }
        List<Long> productIds = orders.stream().map(Order::getProductId).toList();
        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<OrderResponse> content = orders.stream()
                .map(order -> {
                    Product product = productMap.get(order.getProductId());
                    int totalPrice = order.getOrderPrice() * order.getQuantity();
                    return OrderResponse.from(order, product, totalPrice);
                })
                .toList();

        return new CursorPageOrderResponse(
                content, nextCursor, nextAfter, limit, content.size(), hasNext);
    }
}
