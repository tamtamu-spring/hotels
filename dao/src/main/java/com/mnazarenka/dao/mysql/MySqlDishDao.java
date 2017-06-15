package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.Dish;

public class MySqlDishDao extends BaseDao<Dish>{
    protected MySqlDishDao() {
        super(Dish.class);
    }
}
