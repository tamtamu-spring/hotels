package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.DishOrder;

public class MySqlDishOrderDao extends BaseDaoImpl<DishOrder> {
    public MySqlDishOrderDao() {
        super(DishOrder.class);
    }
}
