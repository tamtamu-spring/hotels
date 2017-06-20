package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.RestaurantDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Restaurant;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlRestaurantDaoImpl extends BaseDaoImpl<Restaurant> implements RestaurantDao{

}
