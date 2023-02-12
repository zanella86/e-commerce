package br.com.fiap.sportconnection.ecommerce.repository;

import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;
import br.com.fiap.sportconnection.ecommerce.entity.OrderProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Query("select o from AddressEntity o where o.customerEntity.id =:customerId")
    Set<AddressEntity> getAllByCustomerId(@Param("customerId") Long customerId);
    @Modifying
    @Transactional
    @Query("delete from AddressEntity a where a.customerEntity.id =:customerId")
    void deleteAllByCustomerId(@Param("customerId") Long customerId);
}
