package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.HotelDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlHotelDaoImpl extends BaseDaoImpl<Hotel> implements HotelDao {

    @Override
    public Adress getAdressByHotelId(Long hotelId) {
        return getSessionFactory().getCurrentSession().createQuery("select h.adress from Hotel h where h.id = :id", Adress.class)
                .setParameter("id", hotelId)
                .getSingleResult();
    }
}
