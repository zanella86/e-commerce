package br.com.fiap.sportconnection.ecommerce.dto;

import java.io.Serializable;

public record OrderProductDTO(
        Long orderProductId,
        Long productId,
        Long quantity,
        Long orderId
) implements Serializable {
}
