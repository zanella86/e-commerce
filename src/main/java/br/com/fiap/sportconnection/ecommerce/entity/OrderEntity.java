package br.com.fiap.sportconnection.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order", catalog = "my-ecommerce")
public class OrderEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    private String description;

    @Column(precision = 6, scale = 2)
    private BigDecimal total;

    @Column(precision = 6, scale = 2)
    private BigDecimal discount;

   /* @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;*/

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="order", orphanRemoval = true)
    private List<OrderProductEntity> orderProducts = new ArrayList<>();

}
