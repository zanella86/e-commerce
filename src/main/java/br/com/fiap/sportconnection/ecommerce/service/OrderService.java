package br.com.fiap.sportconnection.ecommerce.service;

import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService {

    OrderDTO get(Long id);

    List<OrderDTO> getList();

    @Transactional
    void remove(Long id);

    @Transactional
    OrderDTO update(OrderDTO order);

    @Transactional
    OrderDTO add(OrderDTO order);

}
