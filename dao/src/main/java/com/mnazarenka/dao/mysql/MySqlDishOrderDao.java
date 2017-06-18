package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.DishOrder;

public class MySqlDishOrderDao extends BaseDao<DishOrder> {
    public MySqlDishOrderDao() {
        super(DishOrder.class);
    }
}
