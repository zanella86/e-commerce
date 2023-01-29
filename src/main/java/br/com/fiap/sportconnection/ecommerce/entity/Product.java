package br.com.fiap.sportconnection.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCT", catalog = "my-ecommerce")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "CODE", unique = true, nullable = false, length = 10)
    private String code;

    @Column(name = "NAME", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STOCK_QUANTITY")
    private Integer stockQuantity;

    @Column(name = "UNITY_PRICE", precision = 2)
    private BigDecimal unityPrice;

}
