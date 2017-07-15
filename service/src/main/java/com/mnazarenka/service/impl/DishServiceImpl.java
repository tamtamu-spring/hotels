package com.mnazarenka.service.impl;

import com.mnazarenka.dao.DishDao;
import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.service.DishServise;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends BaseServiceImpl<Dish> implements DishServise {
    @Getter
    private final DishDao dao;

    @Autowired
    public DishServiceImpl(DishDao dao) {
        this.dao = dao;
    }
}
