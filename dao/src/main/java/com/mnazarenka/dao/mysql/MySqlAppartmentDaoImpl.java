package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Appartment;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySqlAppartmentDaoImpl extends BaseDaoImpl<Appartment> implements AppartmentDao {

    @Override
    public Long getAppartmentsCount(Class<? extends Appartment> apprtmentClass) {
        return getSessionFactory().getCurrentSession().createQuery("select count(*) from " + apprtmentClass.getSimpleName() + " a", Long.class)
                .getSingleResult();
    }

    @Override
    public <T extends Appartment> List<T> findAllAppartments(Class<T> clazz) {
        return  getSessionFactory().getCurrentSession().createQuery("select a from " + clazz.getSimpleName() + " a", clazz)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Appartment> T findAppartment(long id, Class clazz) {
        T entity = (T) getSessionFactory().getCurrentSession().load(clazz, id);
        Hibernate.initialize(entity);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<? extends Appartment> findAppartmentsByRange(int from, int to, Class clazz) {
        return  getSessionFactory().getCurrentSession().createQuery("select a from " + clazz.getSimpleName() + " a", clazz)
                .setFirstResult(from)
                .setMaxResults(to)
                .getResultList();
    }
}
