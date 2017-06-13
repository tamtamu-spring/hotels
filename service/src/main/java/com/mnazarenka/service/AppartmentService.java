package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Appartment;

import java.util.List;

public interface AppartmentService {
    List<Appartment> findAllAppartments();
}
