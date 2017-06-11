package com.mnazarenka.util;


import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.dao.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

public final class TestDataImporter {
    private static TestDataImporter INSTANCE;

    private TestDataImporter(){
    }

    public static TestDataImporter getInstance(){
        if (INSTANCE == null){
            synchronized (TestDataImporter.class){
                if (INSTANCE == null){
                    INSTANCE = new TestDataImporter();
                }
            }
        }
        return INSTANCE;
    }

    public void importTestData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Role adminRole = saveRole("Admin", session);
        Role userRole = saveRole("User", session);

        User adminUser = saveUser(false, adminRole, "AdminLogin", "AdminPassword", session);
        User userUser = saveUser(true, userRole, "UserLogin", "UserPassword", session);

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel firstHotel = saveHotel("FirstHotel", firstHotelAdress, session);

        Adress secondAdress = new Adress();
        secondAdress.setCity("SecondCity");
        secondAdress.setStreet("SecondStreet");
        Hotel secondHotel = saveHotel("SecondHotel", secondAdress, session);

        Adress thirdAdress = new Adress();
        thirdAdress.setCity("ThirdCity");
        thirdAdress.setStreet("ThirdStreet");
        Hotel thirdHotel = saveHotel("ThirdHotel", thirdAdress, session);

        EconomApartment economApartment = saveEconomAppartment(firstHotel, "EconomAppartmentName", "EconomAppartmentDescription",
                1, true, session);
        StandartAppartment standartAppartment = saveStandartAppartment(firstHotel, "StandartAppartmentName", "StandartAppartmentDescription",
                2, true, true, true, session);
        LuxAppartment luxAppartment = saveLuxAppartment(firstHotel, "LuxAppartmentName", "LuxAppartmentDescription",
                4, true, true, true, true, true, session);

        AppartmentOrder firstOrder = saveOrder(userUser, economApartment,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), session);
        AppartmentOrder secondOrder = saveOrder(adminUser, luxAppartment,
                LocalDate.of(2017, 11, 20), LocalDate.of(2017, 11, 25), session);

        Dish firstDish = saveDish("FirstDish", session);
        Dish secondDish = saveDish("SecondDish", session);

        Restaurant firstRestaurant = saveRestaurant("FirstRestaurant", firstHotel, session);
        firstRestaurant.getDishes().add(firstDish);
        firstRestaurant.getDishes().add(secondDish);

        Restaurant secondRestaurant = saveRestaurant("SecondRestaurant", secondHotel, session);
        secondRestaurant.getDishes().add(firstDish);
        secondRestaurant.getDishes().add(secondDish);

        session.getTransaction().commit();
        session.close();
    }

    private AppartmentOrder saveOrder(User user, Appartment appartment, LocalDate startDate, LocalDate endDate, Session session) {
        AppartmentOrder order = new AppartmentOrder();
        order.setUser(user);
        order.setAppartment(appartment);
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        session.save(order);
        return order;
    }

    private LuxAppartment saveLuxAppartment(Hotel hotel, String name, String description, int guestCount,
                                            boolean wifi, boolean wc, boolean tv, boolean bar, boolean kitchen, Session session) {
        LuxAppartment luxAppartment = new LuxAppartment();
        luxAppartment.setHotel(hotel);
        luxAppartment.setName(name);
        luxAppartment.setDescription(description);
        luxAppartment.setGuestsCounts(guestCount);
        luxAppartment.setWiFi(wifi);
        luxAppartment.setWc(wc);
        luxAppartment.setTv(tv);
        luxAppartment.setBar(bar);
        luxAppartment.setKichen(kitchen);
        session.save(luxAppartment);
        return luxAppartment;

    }

    private StandartAppartment saveStandartAppartment(Hotel hotel, String name, String description, int guestCount,
                                                      boolean wifi, boolean wc, boolean tv, Session session) {
        StandartAppartment standartAppartment = new StandartAppartment();
        standartAppartment.setHotel(hotel);
        standartAppartment.setName(name);
        standartAppartment.setDescription(description);
        standartAppartment.setGuestsCounts(guestCount);
        standartAppartment.setWiFi(wifi);
        standartAppartment.setWc(wc);
        standartAppartment.setTv(tv);
        session.save(standartAppartment);
        return standartAppartment;
    }

    private EconomApartment saveEconomAppartment(Hotel hotel, String name, String description, int guestCount, boolean wifi, Session session) {
        EconomApartment economApartment = new EconomApartment();
        economApartment.setHotel(hotel);
        economApartment.setName(name);
        economApartment.setDescription(description);
        economApartment.setGuestsCounts(guestCount);
        economApartment.setWiFi(wifi);
        session.save(economApartment);
        return economApartment;
    }

    private Dish saveDish(String name, Session session) {
        Dish dish = new Dish();
        dish.setName(name);
        session.save(dish);
        return dish;
    }

    private Restaurant saveRestaurant(String name, Hotel hotel, Session session) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setHotel(hotel);
        session.save(restaurant);
        return restaurant;
    }

    private Hotel saveHotel(String name, Adress adress, Session session) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setAdress(adress);
        session.save(hotel);
        return hotel;
    }

    private User saveUser(boolean blockStatus, Role role, String login, String password, Session session) {
        User user = new User();
        user.setBlockStatus(blockStatus);
        user.setRole(role);
        user.setLogin(login);
        user.setPassword(password);
        session.save(user);
        return user;
    }

    private Role saveRole(String name, Session session) {
        Role role = new Role();
        role.setName(name);
        session.save(role);
        return role;
    }
}
