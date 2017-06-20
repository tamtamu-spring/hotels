package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySqlAppartmentDaoImpl extends BaseDaoImpl<Appartment> implements AppartmentDao {

    @Override
    public List<Appartment> findAppartmentsByRange(int from, int to){
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        List<Appartment> resultList = session.createQuery("select a from Appartment a", Appartment.class)
                .setFirstResult(from)
                .setMaxResults(to)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;

    }

    @Override
    public List<EconomApartment> findAllEconomAppartments() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        List<EconomApartment> resultList = session.createQuery("select e from EconomApartment e", EconomApartment.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }

    @Override
    public List<StandartAppartment> findAllStandartAppartments() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        List<StandartAppartment> resultList = session.createQuery("select e from StandartAppartment e", StandartAppartment.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }

    @Override
    public List<LuxAppartment> findAllLuxAppartments() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        List<LuxAppartment> resultList = session.createQuery("select e from LuxAppartment e", LuxAppartment.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }
}
