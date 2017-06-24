package com.mnazarenka.service.common;

import com.mnazarenka.dao.common.BaseDao;
import com.mnazarenka.dao.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    public abstract BaseDao<T> getDao();

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public T find(Long id) {
        return getDao().find(id);
    }

    @Override
    public T create(T entity) {
        return getDao().create(entity);
    }

    @Override
    public void update(T entity) {
        getDao().update(entity);
    }

    @Override
    public void delete(T entity) {
        getDao().delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        getDao().delete(id);
    }
}
