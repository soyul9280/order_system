package project.orderminisystem.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.orderminisystem.domain.order.command.CreateOrderCommand;
import project.orderminisystem.domain.order.dto.response.OrderResponse;
import project.orderminisystem.domain.order.entity.Order;
import project.orderminisystem.domain.order.entity.Status;
import project.orderminisystem.domain.order.repository.OrderRepository;
import project.orderminisystem.domain.product.entity.Product;
import project.orderminisystem.domain.product.repository.ProductRepository;
import project.orderminisystem.global.error.BusinessException;
import project.orderminisystem.global.error.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse create(CreateOrderCommand command) {
        Product product = productRepository.findById(command.productId())
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        if (command.quantity() > product.getStock()) {
            throw new BusinessException(ErrorCode.OUT_OF_STOCK);
        }

        product.minusStock(command.quantity());

        Order order = Order.builder()
                .productId(command.productId())
                .quantity(command.quantity())
                .orderPrice(product.getPrice())
                .status(Status.CREATED)
                .build();

        Order saved = orderRepository.save(order);

        int totalPrice = saved.getOrderPrice() * saved.getQuantity();

        return OrderResponse.from(saved, product, totalPrice);
    }

}
