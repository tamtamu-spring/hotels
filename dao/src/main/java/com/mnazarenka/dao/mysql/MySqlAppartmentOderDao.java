package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.AppartmentOrder;

public class MySqlAppartmentOderDao extends BaseDao<AppartmentOrder> {
    public MySqlAppartmentOderDao() {
        super(AppartmentOrder.class);
    }
}
