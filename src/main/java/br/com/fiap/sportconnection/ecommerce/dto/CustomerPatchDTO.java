package br.com.fiap.sportconnection.ecommerce.dto;

import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public record CustomerPatchDTO(
        String name,
        Date birthDate,
        String document,
        String documentType,
        Set<AddressEntity> addresses) {
}
