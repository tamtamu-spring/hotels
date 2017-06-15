package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.AppartmentOrder;

public class MySqlAppartmentOderDao extends BaseDao<AppartmentOrder> {
    protected MySqlAppartmentOderDao() {
        super(AppartmentOrder.class);
    }
}
