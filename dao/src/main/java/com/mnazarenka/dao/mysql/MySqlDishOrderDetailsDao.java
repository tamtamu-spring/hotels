package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.DishOrderDetails;

public class MySqlDishOrderDetailsDao extends BaseDao<DishOrderDetails> {
    protected MySqlDishOrderDetailsDao() {
        super(DishOrderDetails.class);
    }
}
