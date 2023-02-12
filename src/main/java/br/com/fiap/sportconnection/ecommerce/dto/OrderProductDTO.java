package br.com.fiap.sportconnection.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class OrderProductDTO implements Serializable {
    private Long orderProductId;
    private Long productId;
    private Long quantity;
    private Long orderId;
}