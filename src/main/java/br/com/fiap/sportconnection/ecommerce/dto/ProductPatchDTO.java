package br.com.fiap.sportconnection.ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProductPatchDTO(
        String description,
        Integer stockQuantity,
        BigDecimal unityPrice) implements Serializable {
}
