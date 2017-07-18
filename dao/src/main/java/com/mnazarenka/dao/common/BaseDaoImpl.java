package com.mnazarenka.dao.common;

import com.mnazarenka.dao.entity.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import java.util.List;

public abstract class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {
    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> genericClass;

    @SuppressWarnings("uncheked")
    protected BaseDaoImpl() {
        this.genericClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseDaoImpl.class);
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public List<T> findAll() {
        return sessionFactory.getCurrentSession().createQuery(String.format("select t from %s t", genericClass.getSimpleName()),
                genericClass).getResultList();
    }

    @Override
    public T find(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.load(genericClass, id);
    }

    @Override
    public T create(T ob) {
        sessionFactory.getCurrentSession().save(ob);
        return ob;
    }

    @Override
    public void update(T ob) {
        sessionFactory.getCurrentSession().update(ob);
    }

    @Override
    public void delete(T ob) {
        sessionFactory.getCurrentSession().delete(ob);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        T entity = session.load(genericClass, id);
        session.delete(entity);
    }

}
