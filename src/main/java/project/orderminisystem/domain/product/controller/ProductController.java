package project.orderminisystem.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.orderminisystem.domain.product.dto.request.CreateProductRequest;
import project.orderminisystem.domain.product.dto.request.UpdateProductRequest;
import project.orderminisystem.domain.product.dto.response.CreateProductResponse;
import project.orderminisystem.domain.product.dto.response.UpdateProductResponse;
import project.orderminisystem.domain.product.service.ProductService;
import project.orderminisystem.global.common.ApiResponse;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateProductResponse> create(@RequestBody @Valid CreateProductRequest request){
        CreateProductResponse response = productService.create(request.toCommand());
        return ApiResponse.success(response);
    }

    @PatchMapping("/{productId}")
    public ApiResponse<UpdateProductResponse> update(@PathVariable Long productId, @RequestBody @Valid UpdateProductRequest request){
        UpdateProductResponse response = productService.update(request.toCommand(productId));
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return ApiResponse.success();
    }

}
