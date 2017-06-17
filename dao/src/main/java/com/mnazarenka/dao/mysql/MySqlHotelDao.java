package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import org.hibernate.Session;

public class MySqlHotelDao extends BaseDao<Hotel> {
    public MySqlHotelDao() {
        super(Hotel.class);
    }

    public Adress getAdressByHotelId(Long hotelId) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Adress adress = session.createQuery("select h.adress from Hotel h where h.id = :id", Adress.class)
                .setParameter("id", hotelId)
                .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return adress;
    }
}
