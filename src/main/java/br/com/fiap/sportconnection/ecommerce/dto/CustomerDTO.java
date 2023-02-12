package br.com.fiap.sportconnection.ecommerce.dto;

import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO implements Serializable {
    private Long id;
    private String name;
    private Date birthDate;
    private String document;
    private String documentType;
    private Set<AddressEntity> addresses;
}
