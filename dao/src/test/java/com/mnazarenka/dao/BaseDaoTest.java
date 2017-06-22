package com.mnazarenka.dao;

import com.mnazarenka.configuration.Config;
import com.mnazarenka.dao.common.BaseDao;
import com.mnazarenka.dao.entity.BaseEntity;
import com.mnazarenka.dao.common.BaseDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Config.class})
@Transactional
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
    public abstract void testFindAll();

    @Test
    public void testDelete() {
        T entity = getCurrentDao().create(getEntity());
        Long id = entity.getId();
        getCurrentDao().delete(entity);
        entity = getCurrentDao().find(id);
        assertNull(entity);
    }
}
