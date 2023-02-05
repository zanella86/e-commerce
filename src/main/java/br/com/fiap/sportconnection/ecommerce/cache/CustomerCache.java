package br.com.fiap.sportconnection.ecommerce.cache;

public interface CustomerCache {

    String NAME_ONE = "customerCache";
    String KEY_ONE = "#id";
    String KEY_ONE_OBJ = "#customerDTO.id";
    String NAME_ALL = "allCustomersCache";
    String UNLESS_ALL = "#result.size() == 0";

}
