package br.com.fiap.sportconnection.ecommerce.service.impl;

import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import br.com.fiap.sportconnection.ecommerce.dto.ProductPatchDTO;
import br.com.fiap.sportconnection.ecommerce.entity.ProductEntity;
import br.com.fiap.sportconnection.ecommerce.repository.ProductRepository;
import br.com.fiap.sportconnection.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDTO> get(Long id) {
        var product = productRepository.findById(id);
        if(product.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(new ObjectMapper().convertValue(product.get(), ProductDTO.class));
    }

    @Override
    public List<ProductDTO> list() {
        return productRepository.findAll()
                .stream()
                .map(p -> new ObjectMapper().convertValue(p, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Long id) {
        try {
            productRepository.deleteById(id);   //FIXME: De acordo com a documentação, se o elementro não for encontrado o mesmo deveria ser ignorado. (Bug do Spring?)
        }catch(org.springframework.dao.EmptyResultDataAccessException ex) {

        }
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.save(
                ProductEntity.builder()
                        .id(productDTO.id())
                        .code(productDTO.code())
                        .name(productDTO.name())
                        .description(productDTO.description())
                        .stockQuantity(productDTO.stockQuantity())
                        .unityPrice(productDTO.unityPrice())
                        .build()
        );
        return new ObjectMapper().convertValue(productEntity, ProductDTO.class);
    }

    @Override
    public Optional<ProductDTO> update(Long id, ProductPatchDTO productPatchDTO) {
        var product = productRepository.findById(id);
        if(product.isPresent()) {
            product.get().setDescription(productPatchDTO.description());
            product.get().setStockQuantity(productPatchDTO.stockQuantity());
            product.get().setUnityPrice(productPatchDTO.unityPrice());

            ProductEntity productPatched = productRepository.save(product.get());
            return Optional.of(new ObjectMapper().convertValue(productPatched, ProductDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public ProductDTO add(ProductDTO productDTO) {
        var productEntity = new ObjectMapper().convertValue(productDTO, ProductEntity.class);
        return new ObjectMapper().convertValue(productRepository.save(productEntity), ProductDTO.class);
    }

}
