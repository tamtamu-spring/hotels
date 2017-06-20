package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.DishDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Dish;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlDishDaoImpl extends BaseDaoImpl<Dish> implements DishDao{

}
