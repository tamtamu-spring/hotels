package com.mnazarenka.dao.mysql.db;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnector {
    private static final DbConnector instance = new DbConnector();
    private DataSource dataSource;
    private PoolProperties poolProperties;

    private DbConnector() {
        poolProperties = new PoolProperties();
        poolProperties.setUrl("jdbc:mysql://localhost:3306/hotels");
        poolProperties.setUsername("root");
        poolProperties.setPassword("root");
        poolProperties.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource = new DataSource(poolProperties);
    }

    public static DbConnector getInstance() {
        return instance;
    }

    public Connection getConnetction() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Getting connection failed");
        }
        return connection;
    }
}
