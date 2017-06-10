package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.mysql.db.DbConnector;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class MySqlAppartmentsDao implements AppartmentDao {
    private static final DbConnector dbConnector = DbConnector.getInstance();

    public List<Appartment> getAllAppartments() {
        List<Appartment> entities = new ArrayList<>();

        Session session = dbConnector.getSessionWithTransaction();
        entities = session.createQuery("from Appartment", Appartment.class).getResultList();

        dbConnector.commitTransactionAndCloseSession(session);

        return entities;
    }
}
