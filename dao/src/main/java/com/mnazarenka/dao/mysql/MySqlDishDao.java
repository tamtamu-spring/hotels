package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Dish;

public class MySqlDishDao extends BaseDaoImpl<Dish> {
    public MySqlDishDao() {
        super(Dish.class);
    }
}
