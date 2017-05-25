package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.entity.AppartmentEntity;
import com.mnazarenka.dao.mysql.db.DbConnector;
import com.mnazarenka.dao.mysql.db.ResultSetConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlAppartmentsDao implements AppartmentDao {
    private static final DbConnector dbConnector = DbConnector.getInstance();

    public List<AppartmentEntity> getAllAppartments() {
        List<AppartmentEntity> entities = new ArrayList<>();

        try (Connection connection = dbConnector.getConnetction();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM appartments;");) {

            while (resultSet.next()) {
                AppartmentEntity entity = ResultSetConverter.createAppartmentEntity(resultSet);
                entities.add(entity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }
}
