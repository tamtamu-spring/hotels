package com.mnazarenka.dao.mysql.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbConnector {
    private static final DbConnector instance = new DbConnector();
    private SessionFactory sessionFactory;

    private DbConnector() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static DbConnector getInstance() {
        return instance;
    }

    public Session getSession() {
        Session session = sessionFactory.openSession();
        return session;
    }

    public Session getSessionWithTransaction() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public void closeSession(Session session) {
        session.close();
    }

    public void commitTransactionAndCloseSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }
}
