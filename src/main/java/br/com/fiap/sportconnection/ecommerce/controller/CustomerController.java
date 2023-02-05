package br.com.fiap.sportconnection.ecommerce.controller;

import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchAddressDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchDTO;
import br.com.fiap.sportconnection.ecommerce.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public CustomerDTO getById(@PathVariable("id") Long id) {
        var customer = customerService.get(id);
        if(customer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return customer.get();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<CustomerDTO> getList() {
        var list = customerService.list();
        if(list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return list;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        customerService.remove(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CHECKPOINT)
    public CustomerDTO update(@RequestBody CustomerDTO customerDTO) {
        return customerService.update(customerDTO);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.CHECKPOINT)
    public CustomerDTO alter(
            @PathVariable("id") Long id,
            @RequestBody CustomerPatchDTO customerPatchDTO) {
        var customerDTO = customerService.update(id, customerPatchDTO);
        if(customerDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
        }
        return customerDTO.get();
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
    public CustomerDTO add(@RequestBody CustomerDTO customerDTO) {
        return customerService.add(customerDTO);
    }

}
