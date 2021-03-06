package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import lombok.Getter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class AppartmentDaoTest extends BaseDaoTest<Appartment> {
    @Autowired
    @Getter
    private AppartmentDao dao;

    @Test
    public void testUpdate() {
        Appartment appartment = new Appartment();
        appartment.setName("New Appartment");
        appartment = dao.create(appartment);

        appartment.setName("New Name");
        dao.update(appartment);

        appartment = dao.find(appartment.getId());

        assertEquals("New Name", appartment.getName());
    }

    @Test
    public void testFindByRange() {
        List<? extends Appartment> appartments = dao.findAppartmentsByRange(0, 3, LuxAppartment.class);

        List<String> appartmentNames = appartments.stream().map(Appartment::getName)
                .collect(toList());

        assertThat(appartments, hasSize(1));
        assertThat(appartmentNames, containsInAnyOrder("LuxAppartmentName"));
    }

    @Test
    public void testFindAll() {
        List<Appartment> appartments = dao.findAll();

        List<String> appartmentNames = appartments.stream().map(Appartment::getName)
                .collect(toList());
        List<Integer> appartmentsCounts = appartments.stream().map(Appartment::getGuestsCounts).collect(toList());
        List<Boolean> appartmentsWifiOptions = appartments.stream().map(Appartment::getWiFi).collect(toList());
        List<String> images = appartments.stream().map(Appartment::getImage).collect(toList());

        assertThat(appartments, hasSize(3));
        assertThat(appartmentNames, containsInAnyOrder("EconomAppartmentName", "StandartAppartmentName",
                "LuxAppartmentName"));
        assertThat(images, containsInAnyOrder("economImage", "standartImage", "luxImage"));
        assertThat(appartmentsCounts, containsInAnyOrder(1, 2, 4));
        appartmentsWifiOptions.forEach(a -> assertNotNull(a));
    }

    @Test
    public void testFindEconomAppartments() {
        List<EconomAppartment> appartments = dao.findAllAppartments(EconomAppartment.class);

        assertEquals(appartments.get(0).getName(), "EconomAppartmentName");
        assertEquals((long) appartments.get(0).getGuestsCounts(), 1);
        assertEquals(appartments.get(0).getWiFi(), true);
        assertEquals(appartments.get(0).getImage(), "economImage");
    }

    @Test
    public void findStandatAppartmentTest() {
        List<StandartAppartment> appartments = dao.findAllAppartments(StandartAppartment.class);

        assertEquals(appartments.get(0).getName(), "StandartAppartmentName");
        assertEquals((long) appartments.get(0).getGuestsCounts(), 2);
        assertEquals(appartments.get(0).getWiFi(), true);
        assertEquals(appartments.get(0).getWc(), true);
        assertEquals(appartments.get(0).getTv(), true);
        assertEquals(appartments.get(0).getImage(), "standartImage");
    }

    @Test
    public void findLuxAppartmentTest() {
        List<LuxAppartment> appartments = dao.findAllAppartments(LuxAppartment.class);

        assertEquals(appartments.get(0).getName(), "LuxAppartmentName");
        assertEquals((long) appartments.get(0).getGuestsCounts(), 4);
        assertEquals(appartments.get(0).getWiFi(), true);
        assertEquals(appartments.get(0).getWc(), true);
        assertEquals(appartments.get(0).getTv(), true);
        assertEquals(appartments.get(0).getBar(), true);
        assertEquals(appartments.get(0).getKitchen(), true);
        assertEquals(appartments.get(0).getImage(), "luxImage");
    }
}
