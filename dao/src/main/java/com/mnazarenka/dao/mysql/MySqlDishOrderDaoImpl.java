package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.DishOrderDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.DishOrder;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlDishOrderDaoImpl extends BaseDaoImpl<DishOrder> implements DishOrderDao{

}
