package com.mnazarenka.dao;

import com.mnazarenka.dao.common.BaseDao;
import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;

public interface HotelDao extends BaseDao<Hotel> {
    Adress getAdressByHotelId(Long hotelId);
}
