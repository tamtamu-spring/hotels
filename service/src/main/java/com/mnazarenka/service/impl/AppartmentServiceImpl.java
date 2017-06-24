package com.mnazarenka.service.impl;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.service.AppartmentService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppartmentServiceImpl extends BaseServiceImpl<Appartment> implements AppartmentService {

    @Autowired
    @Getter
    private AppartmentDao dao;
}
