package com.mnazarenka.service;

import com.mnazarenka.configuration.ServiceTestConfiguration;
import com.mnazarenka.dao.entity.BaseEntity;
import com.mnazarenka.dao.utils.TestDataImporter;
import com.mnazarenka.service.common.BaseService;
import lombok.Getter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceTestConfiguration.class})
@Transactional
public abstract class BaseServiceTest <T extends BaseEntity>{

    private Class<T> entityClass;

    @Autowired
    @Getter
    private TestDataImporter testDataImporter;

    @SuppressWarnings("unchecked")
    public BaseServiceTest() {
        this.entityClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseServiceTest.class);
    }

    public abstract BaseService<T> getService();

    @Before
    public void initDb() {
        testDataImporter.importTestData();
    }

    @Test
    public void testCreateAndFind() throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        getService().create(entity);
        Long id = entity.getId();
        entity = getService().find(id);
        assertNotNull(entity);
    }

    @Test(expected = org.hibernate.ObjectNotFoundException.class)
    public void testDelete() throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        entity = getService().create(entity);
        Long id = entity.getId();
        getService().delete(entity);
        getService().find(id);
    }

    @Test(expected = org.hibernate.ObjectNotFoundException.class)
    public void testDeleteById() throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        entity = getService().create(entity);
        Long id = entity.getId();
        getService().deleteById(id);
        getService().find(id);
    }

    @Test
    public abstract void testFindAll();
}
