package com.mnazarenka.dao;

import com.mnazarenka.configuration.DaoConfig;
import com.mnazarenka.dao.common.BaseDao;
import com.mnazarenka.dao.entity.BaseEntity;
import com.mnazarenka.util.TestDataImporter;
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
@ContextConfiguration(classes = {DaoConfig.class})
@Transactional
public abstract class BaseDaoTest<T extends BaseEntity> {

    private Class<T> entityClass;

    @Autowired
    private TestDataImporter testDataImporter;

    @SuppressWarnings("unchecked")
    public BaseDaoTest() {
        this.entityClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseDaoTest.class);
    }

    public abstract BaseDao<T> getDao();

    @Before
    public void initDb() {
        testDataImporter.importTestData();
    }

    @Test
    public void testCreateAndFind() throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        getDao().create(entity);
        Long id = entity.getId();
        entity = getDao().find(id);
        assertNotNull(entity);
    }

    @Test(expected = org.hibernate.ObjectNotFoundException.class)
    public void testDelete() throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        entity = getDao().create(entity);
        Long id = entity.getId();
        getDao().delete(entity);
        getDao().find(id);
    }

    @Test(expected = org.hibernate.ObjectNotFoundException.class)
    public void testDeleteById() throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        entity = getDao().create(entity);
        Long id = entity.getId();
        getDao().delete(id);
        getDao().find(id);
    }

    @Test
    public abstract void testFindAll();

    @Test
    public abstract void testUpdate();

    public TestDataImporter getTestDataImporter() {
        return testDataImporter;
    }
}
