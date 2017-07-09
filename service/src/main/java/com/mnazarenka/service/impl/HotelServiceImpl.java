package com.mnazarenka.service.impl;

import com.mnazarenka.dao.HotelDao;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.service.HotelService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl extends BaseServiceImpl<Hotel> implements HotelService{
    @Getter
    private HotelDao dao;

    @Autowired
    public HotelServiceImpl(HotelDao dao) {
        this.dao = dao;
    }
}
