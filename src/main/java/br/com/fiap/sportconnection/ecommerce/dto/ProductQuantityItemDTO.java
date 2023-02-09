package br.com.fiap.sportconnection.ecommerce.dto;

import java.io.Serializable;

public record ProductQuantityItemDTO(
        Long id,
        Long quantity
) implements Serializable {
}
