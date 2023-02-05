package br.com.fiap.sportconnection.ecommerce.dto;

import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;

import java.util.Set;

public record CustomerPatchAddressDTO(
        Set<AddressEntity> addresses) {
}
