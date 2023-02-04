package br.com.fiap.sportconnection.ecommerce.service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Deprecated
public interface CrudService<T> { //TODO: Essa interface faz sentido? Muito similar à "CrudRepository"...

    Optional<T> get(Long id);   //FIXME: Tipo de chave fixo.

    List<T> list();

    @Transactional
    void remove(T entity);

    @Transactional
    void update(T entity);

    @Transactional
    void add(T entity);

}
