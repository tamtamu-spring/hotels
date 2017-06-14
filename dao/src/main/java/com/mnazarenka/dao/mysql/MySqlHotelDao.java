package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.Hotel;

public class MySqlHotelDao extends BaseDao<Hotel> {
    public MySqlHotelDao() {
        super(Hotel.class);
    }
}
