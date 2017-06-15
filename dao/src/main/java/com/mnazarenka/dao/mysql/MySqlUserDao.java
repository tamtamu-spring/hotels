package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.User;
import org.hibernate.Session;

public class MySqlUserDao extends BaseDao<User> {
    public MySqlUserDao() {
        super(User.class);
    }

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
