package br.com.fiap.sportconnection.ecommerce.dto;


import java.math.BigDecimal;
import java.util.List;

public record OrderDTO (
        Long id,
        String description,
        BigDecimal total,
        BigDecimal discount,
        List<OrderProductDTO> products
) {
}
