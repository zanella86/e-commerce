package br.com.fiap.sportconnection.ecommerce.controller;

import br.com.fiap.sportconnection.ecommerce.dto.AddressDTO;
import br.com.fiap.sportconnection.ecommerce.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public AddressDTO getById(@PathVariable("id") Long id) {
        var address = addressService.get(id);
        if(address.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return address.get();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<AddressDTO> getList() {
        var list = addressService.list();
        if(list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return list;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        addressService.remove(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CHECKPOINT)
    public AddressDTO update(@RequestBody AddressDTO addressDTO) {
        return addressService.update(addressDTO);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.CHECKPOINT)
    public AddressDTO alter(
            @PathVariable("id") Long id,
            @RequestBody AddressDTO addressPatchDTO) {
        var addressDTO = addressService.update(id, addressPatchDTO);
        if(addressDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
        }
        return addressDTO.get();
    }

    /*
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.CHECKPOINT)
    public CustomerDTO alter(
            @PathVariable("id") Long id,
            @RequestBody CustomerPatchAddressDTO customerPatchAddressDTO) {
        var customerDTO = customerService.update(id, customerPatchAddressDTO);
        if(customerDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
        }
        return customerDTO.get();
    }
    */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO add(@RequestBody AddressDTO addressDTO) {
        return addressService.add(addressDTO);
    }

}
