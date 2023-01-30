package br.com.fiap.sportconnection.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product", catalog = "my-ecommerce")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String code;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    private String description;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "unity_price", precision = 6, scale = 2)
    private BigDecimal unityPrice;

}
