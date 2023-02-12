package br.com.fiap.sportconnection.ecommerce.cache;

public interface AddressCache {

    String NAME_ONE = "addressCache";
    String KEY_ONE = "#id";
    String KEY_ONE_OBJ = "#addressDTO.id";
    String NAME_ALL = "allAddressesCache";
    String UNLESS_ALL = "#result.size() == 0";

}
