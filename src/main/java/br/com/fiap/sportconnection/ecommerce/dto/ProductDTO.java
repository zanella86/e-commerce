package br.com.fiap.sportconnection.ecommerce.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String code,
        String name,
        String description,
        Integer quantity,
        BigDecimal price) {
}
