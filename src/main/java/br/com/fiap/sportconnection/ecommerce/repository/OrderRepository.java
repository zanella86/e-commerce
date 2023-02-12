package br.com.fiap.sportconnection.ecommerce.repository;

import br.com.fiap.sportconnection.ecommerce.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
