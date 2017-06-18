package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.Restaurant;

public class MySqlRestaurantDao extends BaseDao<Restaurant> {
    public MySqlRestaurantDao() {
        super(Restaurant.class);
    }
}
