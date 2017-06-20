package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.AppartmentOrder;

public class MySqlAppartmentOderDao extends BaseDaoImpl<AppartmentOrder> {
    public MySqlAppartmentOderDao() {
        super(AppartmentOrder.class);
    }
}
