package br.com.fiap.sportconnection.ecommerce.repository;

import br.com.fiap.sportconnection.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // TODO: Remover todos os métodos comentados após os testes
    //Optional<Product> findById(Long id);

    //List<Product> findAll();

    //void delete(Product product);

    //Product save(Product product);

}
