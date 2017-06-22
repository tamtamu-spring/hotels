package com.mnazarenka.dao;

import com.mnazarenka.configuration.Config;
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
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Config.class})
@Transactional
public abstract class BaseDaoTest<T extends BaseEntity, E extends BaseDao<T>> {

    private Class<T> entityClass;
    @Autowired
    private E dao;

    @Autowired
    private TestDataImporter testDataImporter;

    public BaseDaoTest() {
        Class[] genericTypes = (Class<T>[]) GenericTypeResolver.resolveTypeArguments(getClass(), BaseDaoTest.class);
        this.entityClass = genericTypes[0];
    }


    @Before
    public void initDb() {
        testDataImporter.importTestData();
    }

    @Test
    public void testCreateAndFind() throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        dao.create(entity);
        Long id = entity.getId();
        entity = dao.find(id);
        assertNotNull(entity);
    }

    @Test
    public void testDelete() throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        dao.create(entity);
        Long id = entity.getId();
        dao.delete(entity);
        entity = dao.find(id);
        assertNull(entity);
    }

    @Test
    public abstract void testFindAll();

    @Test
    public abstract void testUpdate();

    public TestDataImporter getTestDataImporter() {
        return testDataImporter;
    }
        /*  @Test
          public void testCreateAndFind() {
              T entity = getCurrentDao().create(getEntity());
              Long id = entity.getId();
              entity = getCurrentDao().find(id);
              assertNotNull(entity);

              getCurrentDao().delete(entity);
          }
      */

    /*
    @Test
    public void testDelete() {
        T entity = getCurrentDao().create(getEntity());
        Long id = entity.getId();
        getCurrentDao().delete(entity);
        entity = getCurrentDao().find(id);
        assertNull(entity);
    }
*/
}
