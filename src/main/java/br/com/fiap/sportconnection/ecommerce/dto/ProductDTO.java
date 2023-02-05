package br.com.fiap.sportconnection.ecommerce.dto;

import lombok.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        @NonNull String code,
        @NonNull String name,
        String description,
        Integer stockQuantity,
        BigDecimal unityPrice) implements Serializable {
}
