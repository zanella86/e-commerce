package br.com.fiap.sportconnection.ecommerce.service;

import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderProductDTO;
import br.com.fiap.sportconnection.ecommerce.exceptions.EmptyException;
import br.com.fiap.sportconnection.ecommerce.exceptions.NotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService {

    OrderDTO get(Long id) throws NotFoundException;

    List<OrderDTO> getList();

    @Transactional
    void remove(Long id) throws NotFoundException;

    @Transactional
    OrderDTO update(OrderDTO order) throws NotFoundException;

    @Transactional
    OrderDTO add(OrderDTO order);

    OrderDTO addOrderProduct(OrderProductDTO orderProduct) throws NotFoundException;



}
