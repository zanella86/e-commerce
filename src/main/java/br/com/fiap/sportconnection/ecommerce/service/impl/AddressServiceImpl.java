package br.com.fiap.sportconnection.ecommerce.service.impl;

import br.com.fiap.sportconnection.ecommerce.cache.AddressCache;
import br.com.fiap.sportconnection.ecommerce.dto.AddressDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;
import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;
import br.com.fiap.sportconnection.ecommerce.repository.AddressRepository;
import br.com.fiap.sportconnection.ecommerce.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Cacheable(value = AddressCache.NAME_ONE, key = AddressCache.KEY_ONE)
    public Optional<AddressDTO> get(Long id) {
        var address = addressRepository.findById(id);
        if(address.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(new ObjectMapper().convertValue(address.get(), AddressDTO.class));
    }

    @Override
    @Cacheable(value = AddressCache.NAME_ALL, unless = AddressCache.UNLESS_ALL)
    public List<AddressDTO> list() {
        return addressRepository.findAll()
                .stream()
                .map(a -> new ObjectMapper().convertValue(a, AddressDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = AddressCache.NAME_ONE, key = AddressCache.KEY_ONE),
                    @CacheEvict(value = AddressCache.NAME_ALL, allEntries = true)
            }
    )
    public void remove(Long id) {
        try {
            addressRepository.deleteById(id);   //FIXME: De acordo com a documentação, se o elementro não for encontrado o mesmo deveria ser ignorado. (Bug do Spring?)
        }catch(org.springframework.dao.EmptyResultDataAccessException ex) {

        }
    }

    @Override
    @Caching(
            put = { @CachePut(value = AddressCache.NAME_ONE, key = AddressCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = AddressCache.NAME_ALL, allEntries = true)}
    )
    public AddressDTO update(AddressDTO addressDTO) {
        AddressEntity addressEntity = addressRepository.save(
                AddressEntity.builder()
                        .id(addressDTO.id())
                        .streetName(addressDTO.streetName())
                        .number(addressDTO.number())
                        .neighborhood(addressDTO.neighborhood())
                        .postalCode(addressDTO.postalCode())
                        .country(addressDTO.country())
                        .city(addressDTO.city())
                        .customerEntity(
                                CustomerEntity.builder()    // FIXME: Concluir relacionamento: Customer x Address
                                        .id(1L)
                                        .build()
                        )
                        .build()
        );
        return new ObjectMapper().convertValue(addressEntity, AddressDTO.class);
    }

    @Override
    @Caching(
            put = { @CachePut(value = AddressCache.NAME_ONE, key = AddressCache.KEY_ONE)},
            evict = {@CacheEvict(value = AddressCache.NAME_ALL, allEntries = true)}
    )
    public Optional<AddressDTO> update(Long id, AddressDTO addressDTO) {
        var address = addressRepository.findById(id);
        if(address.isPresent()) {
            address.get().setStreetName(addressDTO.streetName());
            address.get().setNumber(addressDTO.number());
            address.get().setNeighborhood(addressDTO.neighborhood());
            address.get().setPostalCode(addressDTO.postalCode());
            address.get().setCountry(addressDTO.country());
            address.get().setCity(addressDTO.city());
            address.get().setCustomerEntity(CustomerEntity.builder()    // FIXME: Concluir relacionamento: Customer x Address
                    .id(1L)
                    .build()
            );

            AddressEntity addressEntity = addressRepository.save(address.get());
            return Optional.of(new ObjectMapper().convertValue(addressEntity, AddressDTO.class));
        }
        return Optional.empty();
    }

    @Override
    @Caching(
            put = { @CachePut(value = AddressCache.NAME_ONE, key = AddressCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = AddressCache.NAME_ALL, allEntries = true)}
    )
    public AddressDTO add(AddressDTO addressDTO) {
        var addressEntity = new ObjectMapper().convertValue(addressDTO, AddressEntity.class);
        return new ObjectMapper().convertValue(addressRepository.save(addressEntity), AddressDTO.class);
    }

}
