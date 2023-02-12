package br.com.fiap.sportconnection.ecommerce.repository;

import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
