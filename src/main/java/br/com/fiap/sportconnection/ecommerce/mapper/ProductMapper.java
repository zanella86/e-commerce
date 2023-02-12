package br.com.fiap.sportconnection.ecommerce.mapper;

import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import br.com.fiap.sportconnection.ecommerce.entity.ProductEntity;

public final class ProductMapper {

    public static ProductDTO productEntityToProductDTO(ProductEntity entity){
        return ProductDTO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .description(entity.getDescription())
                .stockQuantity(entity.getStockQuantity())
                .name(entity.getName())
                .unityPrice(entity.getUnityPrice())
                .build();
    }

    public static ProductEntity productDTOToProductEntity(ProductDTO product){
        return ProductEntity.builder()
                .id(product.getId())
                .code(product.getCode())
                .description(product.getDescription())
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .unityPrice(product.getUnityPrice())
                .build();
    }

}
