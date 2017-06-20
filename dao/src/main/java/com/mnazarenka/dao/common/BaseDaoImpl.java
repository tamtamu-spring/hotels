package com.mnazarenka.dao.common;

import com.mnazarenka.dao.entity.BaseEntity;
import com.mnazarenka.dao.mysql.db.DbSessionFactoryCreater;
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<T> resultList = session.createQuery(String.format("select t from %s t", genericClass.getSimpleName())
                , genericClass).getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }

    @Override
    public T find(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        T ob = session.get(genericClass, id);
        session.getTransaction().commit();
        session.close();
        return ob;
    }

    @Override
    public T create(T ob) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(ob);
        session.getTransaction().commit();
        session.close();
        return ob;
    }

    @Override
    public void update(T ob) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(ob);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T ob) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(ob);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery(String.format("delete from %s t where t.id = :id", genericClass.getSimpleName()),
                genericClass).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
