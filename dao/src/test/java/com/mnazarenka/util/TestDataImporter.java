package com.mnazarenka.util;


import com.mnazarenka.dao.entity.Role;
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

        Role admin = saveRole("Admin", session);
        Role user = saveRole("User", session);

        session.getTransaction().commit();
        session.close();
    }

    private Role saveRole(String name, Session session) {
        Role role = new Role();
        role.setName(name);
        session.save(role);
        return role;
    }
}
