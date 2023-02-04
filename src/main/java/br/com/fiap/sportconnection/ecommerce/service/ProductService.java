package br.com.fiap.sportconnection.ecommerce.service;

import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import br.com.fiap.sportconnection.ecommerce.dto.ProductPatchDTO;
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
    Optional<ProductDTO> update(Long id, ProductPatchDTO productPatchDTO);

    @Transactional
    ProductDTO add(ProductDTO productDTO);

}
