package com.mnazarenka.service.impl;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.HotelDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.service.AppartmentService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppartmentServiceImpl extends BaseServiceImpl<Appartment> implements AppartmentService {

    @Getter
    private AppartmentDao dao;

    private HotelDao hotelDao;

    @Autowired
    public AppartmentServiceImpl(AppartmentDao dao, HotelDao hotelDao)
    {
        this.dao = dao;
        this.hotelDao = hotelDao;
    }

    @Override
    public List<LuxAppartment> findAllLuxAppartments() {
        return dao.findAllLuxAppartments();
    }

    @Override
    public LuxAppartment createLuxAppartmentWithHotelId(LuxAppartment appartment, long hotelId) {
        Hotel hotel = hotelDao.find(hotelId);

        appartment.setHotel(hotel);
        return (LuxAppartment) dao.create(appartment);
    }

    @Override
    public LuxAppartment findLuxAppartment(long id) {
        return dao.findLuxAppartment(id);
    }
}
