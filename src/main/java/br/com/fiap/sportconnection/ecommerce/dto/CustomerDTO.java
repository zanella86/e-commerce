package br.com.fiap.sportconnection.ecommerce.dto;

import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public record CustomerDTO (
        Long id,
        String name,
        Date birthDate,
        String document,
        String documentType,
        Set<AddressEntity> addresses) implements Serializable {
}
