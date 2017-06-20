package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.DishOrderDetails;

public class MySqlDishOrderDetailsDao extends BaseDaoImpl<DishOrderDetails> {
    public MySqlDishOrderDetailsDao() {
        super(DishOrderDetails.class);
    }
}
