package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Restaurant;

public class MySqlRestaurantDao extends BaseDaoImpl<Restaurant> {
    public MySqlRestaurantDao() {
        super(Restaurant.class);
    }
}
