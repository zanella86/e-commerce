package br.com.fiap.sportconnection.ecommerce.dto;

import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;

import java.time.LocalDate;
import java.util.Set;

public record CustomerDTO(
        Long id,
        String name,
        LocalDate birthDate,
        String document,
        String documentType,
        Set<AddressEntity> addresses) {
}
