package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.Dish;

public class MySqlDishDao extends BaseDao<Dish>{
    public MySqlDishDao() {
        super(Dish.class);
    }
}
