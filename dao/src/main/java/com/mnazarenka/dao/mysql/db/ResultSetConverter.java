package com.mnazarenka.dao.mysql.db;

import com.mnazarenka.dao.entityies.AppartmentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Николай on 25.05.2017.
 */
public class ResultSetConverter {
    private ResultSetConverter() {
    }

    public static AppartmentEntity createAppartmentEntity(ResultSet rs) throws SQLException {
        AppartmentEntity entity = new AppartmentEntity();

        Integer id = rs.getInt("id");
        Integer fkCategorId = rs.getInt("fk_appart_category_id");
        Integer fkHotelId = rs.getInt("fk_hotel_id");
        String name = rs.getString("name");

        entity.setId(id);
        entity.setFkKategoryId(fkCategorId);
        entity.setFkHotelId(fkHotelId);
        entity.setName(name);

        return entity;
    }
}
