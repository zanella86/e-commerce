package br.com.fiap.sportconnection.ecommerce.service;

import br.com.fiap.sportconnection.ecommerce.entity.Product;
import br.com.fiap.sportconnection.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements CrudService<Product>{

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> get(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> list() {
        return productRepository.findAll();
    }

    @Override
    public void remove(Product entity) {
        productRepository.delete(entity);
    }

    @Override
    public void update(Product entity) {
        if(get(entity.getId()).isPresent()) {
            productRepository.save(entity);
        }
    }

    @Override
    public void add(Product entity) {
        productRepository.save(entity);
    }

}
