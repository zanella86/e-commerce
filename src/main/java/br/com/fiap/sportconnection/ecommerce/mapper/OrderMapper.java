package br.com.fiap.sportconnection.ecommerce.mapper;

import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderProductDTO;
import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;
import br.com.fiap.sportconnection.ecommerce.entity.OrderEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class OrderMapper {

    public static OrderEntity orderDTOToOrderEntity (OrderDTO orderDTO, CustomerEntity customer) {
        OrderEntity order = OrderEntity.builder()
                .total(orderDTO.getTotal())
                .discount(orderDTO.getDiscount())
                .description(orderDTO.getDescription())
                .custumer(customer)
                .build();

        return order;
    }

    public static OrderDTO orderEntityToOrderDTO(OrderEntity orderEntity) {

        List<OrderProductDTO> orderProducts = orderEntity.getOrderProducts().stream().map(orderProduct ->
                new OrderProductDTO(orderProduct.getOrderProductId(),
                        orderProduct.getProduct().getId(),
                        orderProduct.getQuantity(),
                        orderProduct.getOrder().getId())).collect(Collectors.toList());

        return new OrderDTO(orderEntity.getId(), orderEntity.getDescription(), orderEntity.getTotal(), orderEntity.getDiscount(), orderProducts, orderEntity.getCustumer().getId());
    }
    public static List<OrderDTO> orderEntityToOrderDTO(List<OrderEntity> orderEntities) {
        return orderEntities.stream().map(OrderMapper::orderEntityToOrderDTO)
                .collect(Collectors.toList());
    }

}
