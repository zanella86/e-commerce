package br.com.fiap.sportconnection.ecommerce.repository;

import br.com.fiap.sportconnection.ecommerce.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
