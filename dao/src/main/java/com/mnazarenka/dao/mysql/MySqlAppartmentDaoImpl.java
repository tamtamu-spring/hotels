package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySqlAppartmentDaoImpl extends BaseDaoImpl<Appartment> implements AppartmentDao {

    @Override
    public List<Appartment> findAppartmentsByRange(int from, int to){
        return getSessionFactory().getCurrentSession().createQuery("select a from Appartment a", Appartment.class)
                .setFirstResult(from)
                .setMaxResults(to)
                .getResultList();
    }

    @Override
    public List<EconomAppartment> findAllEconomAppartments() {
        return getSessionFactory().getCurrentSession().createQuery("select e from EconomAppartment e", EconomAppartment.class)
                .getResultList();
    }

    @Override
    public List<StandartAppartment> findAllStandartAppartments() {
        return getSessionFactory().getCurrentSession().createQuery("select e from StandartAppartment e", StandartAppartment.class)
                .getResultList();
    }

    @Override
    public StandartAppartment findStandartAppartment(long id) {
        return getSessionFactory().getCurrentSession().find(StandartAppartment.class, id);
    }

    @Override
    public List<LuxAppartment> findAllLuxAppartments() {
        return getSessionFactory().getCurrentSession().createQuery("select e from LuxAppartment e", LuxAppartment.class)
                .getResultList();
    }

    @Override
    public LuxAppartment findLuxAppartment(long id) {
        return getSessionFactory().getCurrentSession().find(LuxAppartment.class, id);
    }

    @Override
    public EconomAppartment findEconomAppatrment(long id) {
        return getSessionFactory().getCurrentSession().find(EconomAppartment.class, id);
    }
}
