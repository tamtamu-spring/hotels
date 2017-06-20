package com.mnazarenka.dao.common;

import com.mnazarenka.dao.entity.BaseEntity;

import java.util.List;

public interface  BaseDao <T extends BaseEntity> {
    List findAll();

    T find(Long id);

    T create(T entity);

    void update(T entity);

    void delete(T entity);

    void delete(Long id);
}
