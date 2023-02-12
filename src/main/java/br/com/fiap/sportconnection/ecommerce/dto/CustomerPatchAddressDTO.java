package br.com.fiap.sportconnection.ecommerce.dto;

import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CustomerPatchAddressDTO implements Serializable {
    private Set<AddressEntity> addresses;
}
