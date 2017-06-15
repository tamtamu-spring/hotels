package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.Restaurant;

public class MySqlRestaurantDao extends BaseDao<Restaurant> {
    protected MySqlRestaurantDao() {
        super(Restaurant.class);
    }
}
