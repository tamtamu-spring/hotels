package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.DishOrderDetails;

public class MySqlDishOrderDetailsDao extends BaseDao<DishOrderDetails> {
    public MySqlDishOrderDetailsDao() {
        super(DishOrderDetails.class);
    }
}
