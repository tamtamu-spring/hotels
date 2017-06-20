package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.DishOrderDetailsDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.DishOrderDetails;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlDishOrderDetailsDaoImpl extends BaseDaoImpl<DishOrderDetails> implements DishOrderDetailsDao {

}
