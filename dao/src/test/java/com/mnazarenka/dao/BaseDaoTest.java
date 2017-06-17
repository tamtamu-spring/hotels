package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.BaseEntity;
import com.mnazarenka.dao.mysql.BaseDao;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;


public abstract class BaseDaoTest<T extends BaseEntity> {

    public abstract T getEntity();

    public abstract BaseDao<T> getCurrentDao();

    @Test
    public abstract void testUpdate();

    @Test
    public void testCreateAndFind() {
        T entity = getCurrentDao().create(getEntity());
        Long id = entity.getId();
        entity = getCurrentDao().find(id);
        assertNotNull(entity);

        getCurrentDao().delete(entity);
    }

    @Test
    public void testFindAll() {
        T firstEntity = getCurrentDao().create(getEntity());
        T secondEntity = getCurrentDao().create(getEntity());
        T thirdEntity = getCurrentDao().create(getEntity());

        List<T> result = getCurrentDao().findAll();
        assertThat(result, hasSize(3));

        getCurrentDao().delete(firstEntity);
        getCurrentDao().delete(secondEntity);
        getCurrentDao().delete(thirdEntity);
    }

    @Test
    public void testDelete() {
        T entity = getCurrentDao().create(getEntity());
        Long id = entity.getId();
        getCurrentDao().delete(entity);
        entity = getCurrentDao().find(id);
        assertNull(entity);
    }
}
