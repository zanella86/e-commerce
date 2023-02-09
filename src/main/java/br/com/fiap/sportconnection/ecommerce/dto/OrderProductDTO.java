package br.com.fiap.sportconnection.ecommerce.dto;

public record OrderProductDTO(
        Long orderProductId,
        Long productId,
        Long quantity,
        Long orderId
) {
}
