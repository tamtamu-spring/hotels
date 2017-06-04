package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.entity.AppartmentEntity;
import com.mnazarenka.dao.mysql.db.DbConnector;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class MySqlAppartmentsDao implements AppartmentDao {
    private static final DbConnector dbConnector = DbConnector.getInstance();

    public List<AppartmentEntity> getAllAppartments() {
        List<AppartmentEntity> entities = new ArrayList<>();

        Session session = dbConnector.getSessionWithTransaction();
        entities = session.createQuery("from AppartmentEntity", AppartmentEntity.class).getResultList();

        dbConnector.commitTransactionAndCloseSession(session);

        return entities;
    }
}
