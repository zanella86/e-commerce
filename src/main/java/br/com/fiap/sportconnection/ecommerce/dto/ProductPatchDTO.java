package br.com.fiap.sportconnection.ecommerce.dto;

import java.math.BigDecimal;

public record ProductPatchDTO(
        String description,
        Integer stockQuantity,
        BigDecimal unityPrice) {
}
