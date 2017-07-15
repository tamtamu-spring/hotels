package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySqlAppartmentDaoImpl extends BaseDaoImpl<Appartment> implements AppartmentDao {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Appartment> T findAppartment(long id, Class clazz) {
        T entity = (T) getSessionFactory().getCurrentSession().load(clazz, id);
        Hibernate.initialize(entity);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<? extends Appartment> findAppartmentsByRange(int from, int to, Class clazz) {
        return  getSessionFactory().getCurrentSession().createQuery("select a from " + clazz.getSimpleName() + " a", clazz)
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
    public List<LuxAppartment> findAllLuxAppartments() {
        return getSessionFactory().getCurrentSession().createQuery("select e from LuxAppartment e", LuxAppartment.class)
                .getResultList();
    }
}
