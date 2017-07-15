package com.mnazarenka.dao;

import com.mnazarenka.dao.common.BaseDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;

import java.util.List;

public interface AppartmentDao extends BaseDao<Appartment> {
    <T extends Appartment> T findAppartment(long id, Class clazz);

    List<? extends Appartment> findAppartmentsByRange(int from, int to, Class clazz);

    <T extends Appartment> List<T> findAllAppartments(Class<T> clazz);
}
