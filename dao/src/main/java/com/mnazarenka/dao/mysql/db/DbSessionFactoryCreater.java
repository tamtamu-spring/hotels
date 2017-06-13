package com.mnazarenka.dao.mysql.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbSessionFactoryCreater {
    private static final DbSessionFactoryCreater instance = new DbSessionFactoryCreater();
    private SessionFactory sessionFactory;

    private DbSessionFactoryCreater() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static DbSessionFactoryCreater getInstance() {
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void closeSessionFactory(){
        sessionFactory.close();
    }

}
