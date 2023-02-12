package br.com.fiap.sportconnection.ecommerce.service.impl;

import br.com.fiap.sportconnection.ecommerce.cache.CustomerCache;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchAddressDTO;
import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;
import br.com.fiap.sportconnection.ecommerce.mapper.CustomerMapper;
import br.com.fiap.sportconnection.ecommerce.repository.AddressRepository;
import br.com.fiap.sportconnection.ecommerce.repository.CustomerRepository;
import br.com.fiap.sportconnection.ecommerce.service.CustomerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Cacheable(value = CustomerCache.NAME_ONE, key = CustomerCache.KEY_ONE)
    public Optional<CustomerDTO> get(Long id) {
        var customer = customerRepository.findById(id);
        if(customer.isEmpty()) {
            return Optional.empty();
        }
        var address =  addressRepository.getAllByCustomerId(id);
        return Optional.ofNullable(CustomerMapper.customerEntityToCustomerDTO(customer.get(), address));
    }

    @Override
    @Cacheable(value = CustomerCache.NAME_ALL, unless = CustomerCache.UNLESS_ALL)
    public List<CustomerDTO> list() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> CustomerMapper
                        .customerEntityToCustomerDTO(
                                customer,
                                addressRepository.getAllByCustomerId(customer.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CustomerCache.NAME_ONE, key = CustomerCache.KEY_ONE),
                    @CacheEvict(value = CustomerCache.NAME_ALL, allEntries = true)
            }
    )
    public void remove(Long id) {
        try {
            addressRepository.deleteAllByCustomerId(id);
            customerRepository.deleteById(id);
        }catch(org.springframework.dao.EmptyResultDataAccessException ex) {
            // FIXME: De acordo com a documentação, se o elementro não for encontrado o mesmo deveria ser ignorado. (Bug do Spring?)
        }
    }

    @Override
    @Caching(
            put = { @CachePut(value = CustomerCache.NAME_ONE, key = CustomerCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = CustomerCache.NAME_ALL, allEntries = true)}
    )
    public CustomerDTO update(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerRepository.save(
                CustomerMapper.customerDTOToCustomerEntitySave(customerDTO)
        );
        return CustomerMapper.customerEntityToCustomerDTO(customerEntity);
    }

    @Override
    @Caching(
            put = { @CachePut(value = CustomerCache.NAME_ONE, key = CustomerCache.KEY_ONE)},
            evict = {@CacheEvict(value = CustomerCache.NAME_ALL, allEntries = true)}
    )
    public Optional<CustomerDTO> update(Long id, CustomerPatchAddressDTO customerPatchAddressDTO) {
        var customer = customerRepository.findById(id);
        if(!customer.isPresent()) {
            return Optional.empty();
        }
        customer.get().setAddresses(customerPatchAddressDTO.getAddresses());
        CustomerEntity customerEntity = customerRepository.save(customer.get());
        return Optional.ofNullable(CustomerMapper.customerEntityToCustomerDTO(customerEntity));
    }

    @Override
    @Caching(
            put = { @CachePut(value = CustomerCache.NAME_ONE, key = CustomerCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = CustomerCache.NAME_ALL, allEntries = true)}
    )
    public CustomerDTO add(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = CustomerMapper.customerDTOToCustomerEntitySave(customerDTO);
        CustomerEntity finalCustomerEntity = customerRepository.save(customerEntity);
        customerDTO.setId(finalCustomerEntity.getId());

        customerDTO.getAddresses().stream().forEach(address -> {
            address.setCustomerEntity(finalCustomerEntity);
            addressRepository.save(address);
        });

        return customerDTO;
    }

}
