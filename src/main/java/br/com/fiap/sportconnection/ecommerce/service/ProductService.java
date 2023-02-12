package br.com.fiap.sportconnection.ecommerce.service;

import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDTO> get(Long id);

    List<ProductDTO> list();

    @Transactional
    void remove(Long id);

    @Transactional
    ProductDTO update(ProductDTO productDTO);

    @Transactional
    ProductDTO add(ProductDTO productDTO);

}
