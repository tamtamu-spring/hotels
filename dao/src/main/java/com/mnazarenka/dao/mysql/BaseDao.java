package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.mysql.db.DbSessionFactoryCreater;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public abstract class BaseDao<T> {
    protected static final SessionFactory SESSION_FACTORY = DbSessionFactoryCreater.getInstance().getSessionFactory();

    private Class<T> genericClass;

    protected BaseDao(Class<T> genericClass) {
        this.genericClass = genericClass;
    }

    protected static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public List<T> findAll() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<T> resultList = session.createQuery(String.format("select t from %s t", genericClass.getSimpleName())
                , genericClass).getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }

    public T find(Long id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        T ob = session.get(genericClass, id);
        session.getTransaction().commit();
        session.close();
        return ob;
    }

    public T create(T ob) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.save(ob);
        session.getTransaction().commit();
        session.close();
        return ob;
    }

    public void update(T ob) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.update(ob);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(T ob) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.delete(ob);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Long id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.createQuery(String.format("delete from %s t where t.id = :id", genericClass.getSimpleName()),
                genericClass).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
