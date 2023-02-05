package br.com.fiap.sportconnection.ecommerce.service;

import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchAddressDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<CustomerDTO> get(Long id);

    List<CustomerDTO> list();

    @Transactional
    void remove(Long id);

    @Transactional
    CustomerDTO update(CustomerDTO customerDTO);

    @Transactional
    Optional<CustomerDTO> update(Long id, CustomerPatchDTO customerPatchDTO);

    @Transactional
    Optional<CustomerDTO> update(Long id, CustomerPatchAddressDTO customerPatchAddressDTO);

    @Transactional
    CustomerDTO add(CustomerDTO customerDTO);

}
