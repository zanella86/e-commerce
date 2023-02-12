package br.com.fiap.sportconnection.ecommerce.mapper;

import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;

public final class CustomerMapper {

    public static CustomerEntity customerDTOToCustomerEntitySave(CustomerDTO customer){
        return  CustomerEntity.builder()
                .name(customer.name())
                .documentType(customer.documentType())
                .document(customer.document())
                .birthDate(customer.birthDate())
                .build();
    }
}
