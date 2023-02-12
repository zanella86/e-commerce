package br.com.fiap.sportconnection.ecommerce.cache;

public interface ProductCache {
    String NAME_ONE = "productCache";
    String KEY_ONE = "#id";
    String KEY_ONE_OBJ = "#productDTO.id";
    String NAME_ALL = "allProductsCache";
    String UNLESS_ALL = "#result.size() == 0";

}
