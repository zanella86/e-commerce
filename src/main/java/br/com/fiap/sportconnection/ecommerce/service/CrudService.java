package br.com.fiap.sportconnection.ecommerce.service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    Optional<T> get(Long id);

    List<T> list();

    @Transactional
    void remove(T entity);

    @Transactional
    void update(T entity);

    @Transactional
    void add(T entity);

}
