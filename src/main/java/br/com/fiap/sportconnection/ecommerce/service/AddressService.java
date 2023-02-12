package br.com.fiap.sportconnection.ecommerce.service;

import br.com.fiap.sportconnection.ecommerce.dto.AddressDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Optional<AddressDTO> get(Long id);

    List<AddressDTO> list();

    @Transactional
    void remove(Long id);

    @Transactional
    AddressDTO update(AddressDTO addressDTO);

    @Transactional
    Optional<AddressDTO> update(Long id, AddressDTO AddressDTO);

    @Transactional
    AddressDTO add(AddressDTO addressDTO);

}
