package br.com.fiap.sportconnection.ecommerce.cache;

public interface OrderCache {

    String NAME_ONE = "orderCache";
    String KEY_ONE = "#id";
    String KEY_ONE_OBJ = "#orderDTO.id";
    String NAME_ALL = "allOrderCache";
    String UNLESS_ALL = "#result.size() == 0";

}
