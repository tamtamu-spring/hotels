package com.mnazarenka.service.impl;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.mysql.MySqlAppartmentDaoImpl;
import com.mnazarenka.service.AppartmentService;

import java.util.List;

public class AppartmentServiceImpl implements AppartmentService{

    public List<Appartment> findAllAppartments() {
        MySqlAppartmentDaoImpl appartmentDao = new MySqlAppartmentDaoImpl();
        return appartmentDao.findAll();
    }
}
