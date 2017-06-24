package com.mnazarenka.service.common;

import com.mnazarenka.dao.entity.BaseEntity;

import java.util.List;

public interface BaseService <T extends BaseEntity> {
    List<T> findAll();

    T find(Long id);

    T create(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(Long id);
}
