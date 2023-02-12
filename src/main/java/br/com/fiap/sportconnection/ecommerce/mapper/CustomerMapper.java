package br.com.fiap.sportconnection.ecommerce.mapper;

import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;

public final class CustomerMapper {

    public static CustomerEntity customerDTOToCustomerEntitySave(CustomerDTO customer){
        return CustomerEntity.builder()
                .name(customer.getName())
                .documentType(customer.getDocumentType())
                .document(customer.getDocument())
                .birthDate(customer.getBirthDate())
                .build();
    }

    public static CustomerDTO customerEntityToCustomerDTO(CustomerEntity customerEntity) {
        return CustomerDTO.builder()
                .id(customerEntity.getId())
                .addresses(customerEntity.getAddresses())
                .documentType(customerEntity.getDocumentType())
                .document(customerEntity.getDocument())
                .birthDate(customerEntity.getBirthDate())
                .name(customerEntity.getName())
                .build();
    }
}
