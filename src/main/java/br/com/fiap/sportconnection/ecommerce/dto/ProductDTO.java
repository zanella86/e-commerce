package br.com.fiap.sportconnection.ecommerce.dto;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO implements Serializable {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Integer stockQuantity;
    private BigDecimal unityPrice;
}
