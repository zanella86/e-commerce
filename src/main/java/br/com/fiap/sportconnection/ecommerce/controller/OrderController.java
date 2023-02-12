package br.com.fiap.sportconnection.ecommerce.controller;

import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderProductDTO;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotEnoughResourceException;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotFoundException;
import br.com.fiap.sportconnection.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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
    public ResponseEntity<String> getById(@PathVariable("id") Long id) throws NotFoundException {
        try {
            orderService.remove(id);
            return ResponseEntity.ok(orderService.get(id).toString());
        } catch(Exception ex){
            if(ex instanceof NotFoundException)
                return ResponseEntity.notFound().build();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws NotFoundException {
        try {
            orderService.remove(id);
            return ResponseEntity.ok().build();
        } catch(Exception ex){
            if(ex instanceof NotFoundException)
                return ResponseEntity.unprocessableEntity().build();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody OrderDTO orderDTO) throws NotFoundException {
        try {
            return ResponseEntity.ok(orderService.add(orderDTO).toString());
        } catch(Exception ex) {
            if(ex instanceof NotFoundException)
                return ResponseEntity.notFound().build();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("product")
    public ResponseEntity<String> addProduct(@RequestBody OrderProductDTO orderProductDTO) throws NotFoundException, NotEnoughResourceException {
        try {
            return ResponseEntity.ok(orderService.addOrderProduct(orderProductDTO).toString());
        } catch (Exception ex){
            if(ex instanceof NotFoundException)
                return ResponseEntity.notFound().build();
            if(ex instanceof NotEnoughResourceException)
                return ResponseEntity.badRequest().body("Not enough resources");
            return ResponseEntity.internalServerError().build();
        }
    }
}
