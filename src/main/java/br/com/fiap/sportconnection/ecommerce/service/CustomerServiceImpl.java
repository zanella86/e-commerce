package br.com.fiap.sportconnection.ecommerce.service;

import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchAddressDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchDTO;
import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;
import br.com.fiap.sportconnection.ecommerce.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Cacheable(value = "customerCache", key = "#id")
    public Optional<CustomerDTO> get(Long id) {
        var customer = customerRepository.findById(id);
        if(customer.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(new ObjectMapper().convertValue(customer.get(), CustomerDTO.class));
    }

    @Override
    public List<CustomerDTO> list() {
        return customerRepository.findAll()
                .stream()
                .map(c -> new ObjectMapper().convertValue(c, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Long id) {
        try {
            customerRepository.deleteById(id);   //FIXME: De acordo com a documentação, se o elementro não for encontrado o mesmo deveria ser ignorado. (Bug do Spring?)
        }catch(org.springframework.dao.EmptyResultDataAccessException ex) {

        }
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerRepository.save(
                CustomerEntity.builder()
                        .id(customerDTO.id())
                        .name(customerDTO.name())
                        .birthDate(customerDTO.birthDate())
                        .document(customerDTO.document())
                        .documentType(customerDTO.documentType())
                        .addresses(customerDTO.addresses())
                        .build()
        );
        return new ObjectMapper().convertValue(customerEntity, CustomerDTO.class);
    }

    @Override
    public Optional<CustomerDTO> update(Long id, CustomerPatchDTO customerPatchDTO) {
        var customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            customer.get().setName(customerPatchDTO.name());
            customer.get().setBirthDate(customerPatchDTO.birthDate());
            customer.get().setDocument(customerPatchDTO.document());
            customer.get().setDocumentType(customerPatchDTO.documentType());
            customer.get().setAddresses(customerPatchDTO.addresses());

            CustomerEntity customerEntity = customerRepository.save(customer.get());
            return Optional.of(new ObjectMapper().convertValue(customerEntity, CustomerDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<CustomerDTO> update(Long id, CustomerPatchAddressDTO customerPatchAddressDTO) {
        var customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            customer.get().setAddresses(customerPatchAddressDTO.addresses());

            CustomerEntity customerEntity = customerRepository.save(customer.get());
            return Optional.of(new ObjectMapper().convertValue(customerEntity, CustomerDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public CustomerDTO add(CustomerDTO customerDTO) {
        var customerEntity = new ObjectMapper().convertValue(customerDTO, CustomerEntity.class);
        return new ObjectMapper().convertValue(customerRepository.save(customerEntity), CustomerDTO.class);
    }

}
