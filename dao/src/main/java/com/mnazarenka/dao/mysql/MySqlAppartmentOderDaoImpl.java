package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.AppartmentOrderDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.AppartmentOrder;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlAppartmentOderDaoImpl extends BaseDaoImpl<AppartmentOrder> implements AppartmentOrderDao {
}
