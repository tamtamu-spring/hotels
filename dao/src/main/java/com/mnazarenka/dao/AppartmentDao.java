package com.mnazarenka.dao;

import com.mnazarenka.dao.common.BaseDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;

import java.util.List;

public interface AppartmentDao extends BaseDao<Appartment> {
    List<Appartment> findAppartmentsByRange(int from, int to);

    List<EconomApartment> findAllEconomAppartments();

    List<StandartAppartment> findAllStandartAppartments();

    List<LuxAppartment> findAllLuxAppartments();
}
