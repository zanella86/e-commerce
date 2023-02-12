package br.com.fiap.sportconnection.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Builder
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO implements Serializable {
    private Long id;
    private String description;
    private BigDecimal total;
    private BigDecimal discount;
    private List<OrderProductDTO> products;
    private Long costumerId;
}
