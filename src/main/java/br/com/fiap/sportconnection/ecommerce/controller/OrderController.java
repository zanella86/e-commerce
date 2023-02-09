package br.com.fiap.sportconnection.ecommerce.controller;

import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderProductDTO;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotFoundException;
import br.com.fiap.sportconnection.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

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
        return orderService.getList();
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
    public OrderDTO add(@RequestBody OrderDTO orderDTO) {
        return orderService.add(orderDTO);
    }

    @PostMapping("product")
    public OrderDTO addProduct(@RequestBody OrderProductDTO orderProductDTO) throws NotFoundException {
        return orderService.addOrderProduct(orderProductDTO);
    }
}
