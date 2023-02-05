package br.com.fiap.sportconnection.ecommerce.service.impl;

import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.entity.OrderEntity;
import br.com.fiap.sportconnection.ecommerce.repository.OrderProductRepository;
import br.com.fiap.sportconnection.ecommerce.repository.OrderRepository;
import br.com.fiap.sportconnection.ecommerce.service.OrderService;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }


    @Override
    public OrderDTO get(Long id) {

        orderRepository.findById(id);

        return null;
    }

    @Override
    public List<OrderDTO> getList() {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public OrderDTO update(OrderDTO order) {
        return null;
    }

    @Override
    public OrderDTO add(OrderDTO order) {
        return null;
    }
}
