package br.com.fiap.sportconnection.ecommerce.dto;

import java.io.Serializable;

public record AddressDTO(
        Long id,
        String streetName,
        String number,
        String neighborhood,
        String postalCode,
        String country,
        String city,
        CustomerDTO customer) implements Serializable {
}
