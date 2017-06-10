package com.mnazarenka.util;


import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public final class TestDataImporter {
    private static TestDataImporter INSTANCE;

    private TestDataImporter(){
    }

    public static TestDataImporter getInstance(){
        if (INSTANCE == null){
            synchronized (TestDataImporter.class){
                if (INSTANCE == null){
                    INSTANCE = new TestDataImporter();
                }
            }
        }
        return INSTANCE;
    }

    public void importTestData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Role adminRole = saveRole("Admin", session);
        Role userRole = saveRole("User", session);

        User adminUser = saveUser(false, adminRole, "AdminLogin", "AdminPassword", session);
        User userUser = saveUser(true, userRole, "UserLogin", "UserPassword", session);

        session.getTransaction().commit();
        session.close();
    }

    private User saveUser(boolean blockStatus, Role role, String login, String password, Session session) {
        User user = new User();
        user.setBlockStatus(blockStatus);
        user.setRole(role);
        user.setLogin(login);
        user.setPassword(password);
        session.save(user);
        return user;
    }

    private Role saveRole(String name, Session session) {
        Role role = new Role();
        role.setName(name);
        session.save(role);
        return role;
    }
}
