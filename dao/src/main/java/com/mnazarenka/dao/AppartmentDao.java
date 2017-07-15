package com.mnazarenka.dao;

import com.mnazarenka.dao.common.BaseDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;

import java.util.List;

public interface AppartmentDao extends BaseDao<Appartment> {
    List<? extends Appartment> findAppartmentsByRange(int from, int to, Class clazz);

    List<EconomAppartment> findAllEconomAppartments();

    List<StandartAppartment> findAllStandartAppartments();

    List<LuxAppartment> findAllLuxAppartments();

    LuxAppartment findLuxAppartment(long id);

    StandartAppartment findStandartAppartment(long id);

    EconomAppartment findEconomAppatrment(long id);
}
