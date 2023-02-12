package br.com.fiap.sportconnection.ecommerce.controller;

import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderProductDTO;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotEnoughResourceException;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotFoundException;
import br.com.fiap.sportconnection.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getList() {
        return orderService.list();
    }

    @GetMapping("{id}")
    public OrderDTO getById(@PathVariable("id") Long id) throws NotFoundException {
        return orderService.get(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) throws NotFoundException {
        orderService.remove(id);
    }

    @PostMapping
    public OrderDTO add(@RequestBody OrderDTO orderDTO) throws NotFoundException {
        return orderService.add(orderDTO);
    }

    @PostMapping("product")
    public OrderDTO addProduct(@RequestBody OrderProductDTO orderProductDTO) throws NotFoundException, NotEnoughResourceException {
        try {
            return orderService.addOrderProduct(orderProductDTO);
        } catch (Exception ex){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
