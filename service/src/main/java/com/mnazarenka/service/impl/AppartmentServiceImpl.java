package com.mnazarenka.service.impl;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.HotelDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
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
    public List<? extends Appartment> getAppartmentsByRange(int pageNumber, int pageCounts, Class clazz) {
        return dao.findAppartmentsByRange(pageNumber, pageCounts, clazz);
    }

    @Override
    public List<LuxAppartment> findAllLuxAppartments() {
        return dao.findAllLuxAppartments();
    }

    @Override
    public List<StandartAppartment> findAllStandartAppartments() {
        return dao.findAllStandartAppartments();
    }


    @Override
    public StandartAppartment findStandartAppartment(long id) {
        return dao.findStandartAppartment(id);
    }

    @Override
    public LuxAppartment findLuxAppartment(long id) {
        return dao.findLuxAppartment(id);
    }

    @Override
    public List<EconomAppartment> findAllEconomAppartments() {
        return dao.findAllEconomAppartments();
    }

    @Override
    public EconomAppartment findEconomAppartment(long id) {
        return dao.findEconomAppatrment(id);
    }

    @Override
    public void createAppartmentWithHotelId(Appartment appartment, long hotelId) {
        Hotel hotel = hotelDao.find(hotelId);

        appartment.setHotel(hotel);
        dao.create(appartment);
    }

    @Override
    public void updateAppartmentWithHotelId(Appartment appartment, long hotelId) {
        Hotel hotel = hotelDao.find(hotelId);

        appartment.setHotel(hotel);
        dao.update(appartment);
    }
}
