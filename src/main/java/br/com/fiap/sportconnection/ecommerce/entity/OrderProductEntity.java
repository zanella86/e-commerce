package br.com.fiap.sportconnection.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_product")
public class OrderProductEntity {


    public OrderProductEntity(OrderEntity order, ProductEntity product, Long quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(unique = true, nullable = false)
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private Long quantity;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof OrderProductEntity)) return false;
        OrderProductEntity that = (OrderProductEntity) obj;
        return Objects.equals(product.getName(), that.product.getName()) &&
                Objects.equals(product.getId(), that.product.getId()) &&
                Objects.equals(product.getCode(), that.product.getCode()) &&
                Objects.equals(order.getId(), that.order.getId()) &&
                Objects.equals(order.getTotal(), that.order.getTotal()) &&
                Objects.equals(quantity, that.quantity);
    }
}
