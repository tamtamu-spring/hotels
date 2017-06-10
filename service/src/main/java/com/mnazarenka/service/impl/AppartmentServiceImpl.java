package com.mnazarenka.service.impl;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.mysql.MySqlAppartmentsDao;
import com.mnazarenka.service.AppartmentService;

import java.util.List;

public class AppartmentServiceImpl implements AppartmentService{

    public List<Appartment> getAllAppartments() {
        AppartmentDao appartmentDao = new MySqlAppartmentsDao();
        return appartmentDao.getAllAppartments();
    }
}
