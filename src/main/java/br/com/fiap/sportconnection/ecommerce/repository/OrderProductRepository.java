package br.com.fiap.sportconnection.ecommerce.repository;

import br.com.fiap.sportconnection.ecommerce.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
    @Query("select o from OrderProductEntity o where o.order.id =:orderId")
    List<OrderProductEntity> getAllByOrderId(@Param("orderId") Long orderId);

    @Query("delete from OrderProductEntity o where o.order.id =:orderId")
    void deleteAllByOrderId(@Param("orderId") Long orderId);

}
