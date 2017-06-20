package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.HotelDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlHotelDaoImpl extends BaseDaoImpl<Hotel> implements HotelDao {


    @Override
    public Adress getAdressByHotelId(Long hotelId) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        Adress adress = session.createQuery("select h.adress from Hotel h where h.id = :id", Adress.class)
                .setParameter("id", hotelId)
                .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return adress;
    }
}
