package br.com.fiap.sportconnection.ecommerce.service.impl;

import br.com.fiap.sportconnection.ecommerce.cache.ProductCache;
import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import br.com.fiap.sportconnection.ecommerce.dto.ProductPatchDTO;
import br.com.fiap.sportconnection.ecommerce.entity.ProductEntity;
import br.com.fiap.sportconnection.ecommerce.mapper.ProductMapper;
import br.com.fiap.sportconnection.ecommerce.repository.ProductRepository;
import br.com.fiap.sportconnection.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    @Cacheable(value = ProductCache.NAME_ONE, key = ProductCache.KEY_ONE)
    public Optional<ProductDTO> get(Long id) {
        var product = productRepository.findById(id);
        if(product.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(ProductMapper.productEntityToProductDTO(product.get()));
    }

    @Override
    @Cacheable(value = ProductCache.NAME_ALL, unless = ProductCache.UNLESS_ALL)
    public List<ProductDTO> list() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::productEntityToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = ProductCache.NAME_ONE, key = ProductCache.KEY_ONE),
                    @CacheEvict(value = ProductCache.NAME_ALL, allEntries = true)
            }
    )
    public void remove(Long id) {
        try {
            productRepository.deleteById(id);   //FIXME: De acordo com a documentação, se o elementro não for encontrado o mesmo deveria ser ignorado. (Bug do Spring?)
        }catch(org.springframework.dao.EmptyResultDataAccessException ex) {

        }
    }

    @Override
    @Caching(
            put = { @CachePut(value = ProductCache.NAME_ONE, key = ProductCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = ProductCache.NAME_ALL, allEntries = true)}
    )
    public ProductDTO update(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.save(
                ProductEntity.builder()
                        .id(productDTO.getId())
                        .code(productDTO.getCode())
                        .name(productDTO.getName())
                        .description(productDTO.getDescription())
                        .stockQuantity(productDTO.getStockQuantity())
                        .unityPrice(productDTO.getUnityPrice())
                        .build()
        );

        return ProductMapper.productEntityToProductDTO(productEntity);
    }

    @Override
    @Caching(
            put = { @CachePut(value = ProductCache.NAME_ONE, key = ProductCache.KEY_ONE_OBJ)},
            evict = {@CacheEvict(value = ProductCache.NAME_ALL, allEntries = true)}
    )
    public ProductDTO add(ProductDTO productDTO) {
        var productEntity = ProductMapper.productDTOToProductEntity(productDTO);
        productDTO.setId(productRepository.save(productEntity).getId());
        return productDTO;
    }

}
