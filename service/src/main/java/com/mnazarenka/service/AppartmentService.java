package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.service.common.BaseService;

import java.util.List;

public interface AppartmentService extends BaseService<Appartment> {
    List<LuxAppartment> findAllLuxAppartments();

    LuxAppartment findLuxAppartment(long id);

    LuxAppartment createLuxAppartmentWithHotelId(LuxAppartment appartment, long hotelId);
}
