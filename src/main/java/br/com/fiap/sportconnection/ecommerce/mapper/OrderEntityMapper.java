package br.com.fiap.sportconnection.ecommerce.mapper;

import br.com.fiap.sportconnection.ecommerce.dto.OrderDTO;
import br.com.fiap.sportconnection.ecommerce.dto.OrderProductDTO;
import br.com.fiap.sportconnection.ecommerce.entity.OrderEntity;
import br.com.fiap.sportconnection.ecommerce.entity.OrderProductEntity;
import br.com.fiap.sportconnection.ecommerce.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OrderEntityMapper {
    OrderEntity orderDTOToOrderEntity (OrderDTO orderDTO) {

        OrderEntity order = OrderEntity.builder()
                .total(orderDTO.total())
                .discount(orderDTO.discount())
                .description(orderDTO.description()).build();


        return null;
    }

    public static OrderDTO orderEntityToOrderDTO(OrderEntity orderEntity) {

        List<OrderProductDTO> orderProducts = orderEntity.getOrderProducts().stream().map(orderProduct ->
                new OrderProductDTO(orderProduct.getOrderProductId(),
                        orderProduct.getProduct().getId(),
                        orderProduct.getQuantity(),
                        orderProduct.getOrder().getId())).collect(Collectors.toList());

        return new OrderDTO(orderEntity.getId(), orderEntity.getDescription(), orderEntity.getTotal(), orderEntity.getDiscount(), orderProducts);
    }
    public static List<OrderDTO> orderEntityToOrderDTO(List<OrderEntity> orderEntities){
        return orderEntities.stream().map(orderEntity -> orderEntityToOrderDTO(orderEntity))
                .collect(Collectors.toList());
    }

}
