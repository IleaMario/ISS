package com.example.iss.repo;

import com.example.iss.domain.IEntity;

public interface Repository<E extends IEntity> {
    void save(E e);
    void delete(Integer id);
    E findOne(Integer id);
    void update(Integer id, E e);
    Iterable<E> getAll();
}
