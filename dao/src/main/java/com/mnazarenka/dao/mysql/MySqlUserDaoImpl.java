package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.UserDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.User;
import org.hibernate.Session;

public class MySqlUserDaoImpl extends BaseDaoImpl<User> implements UserDao{


    @Override
    public User getUserByLogin(String login) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();

        User user = session.createQuery("select u from User u where u.login = :login",
                User.class).setParameter("login", login)
                .getSingleResult();

        session.getTransaction().commit();
        session.close();

        return user;
    }
}