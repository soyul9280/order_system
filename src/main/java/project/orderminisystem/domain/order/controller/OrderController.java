package project.orderminisystem.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.orderminisystem.domain.order.dto.request.CreateOrderRequest;
import project.orderminisystem.domain.order.dto.response.OrderResponse;
import project.orderminisystem.domain.order.service.OrderQueryService;
import project.orderminisystem.domain.order.service.OrderService;
import project.orderminisystem.global.common.ApiResponse;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderQueryService orderQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrderResponse> create(@RequestBody @Valid CreateOrderRequest request) {
        OrderResponse response = orderService.create(request.toCommand());
        return ApiResponse.success(response);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getProductDetail(@PathVariable Long orderId){
        OrderResponse response = orderQueryService.getOrderDetail(orderId);
        return ApiResponse.success(response);
    }
}
