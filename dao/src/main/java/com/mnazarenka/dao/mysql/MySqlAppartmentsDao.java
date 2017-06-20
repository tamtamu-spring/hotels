package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import org.hibernate.Session;

import java.util.List;

public class MySqlAppartmentsDao extends BaseDaoImpl<Appartment> {

    public MySqlAppartmentsDao() {
        super(Appartment.class);
    }

    public List<Appartment> findAppartmentsByRange(int from, int to){
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Appartment> resultList = session.createQuery("select a from Appartment a", Appartment.class)
                .setFirstResult(from)
                .setMaxResults(to)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;

    }

    public List<EconomApartment> findAllEconomAppartments() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<EconomApartment> resultList = session.createQuery("select e from EconomApartment e", EconomApartment.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }

    public List<StandartAppartment> findAllStandartAppartments() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<StandartAppartment> resultList = session.createQuery("select e from StandartAppartment e", StandartAppartment.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }

    public List<LuxAppartment> findAllLuxAppartments() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<LuxAppartment> resultList = session.createQuery("select e from LuxAppartment e", LuxAppartment.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }
}
