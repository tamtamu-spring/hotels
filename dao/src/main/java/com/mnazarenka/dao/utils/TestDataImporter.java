package com.mnazarenka.dao.utils;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.AppartmentOrderDao;
import com.mnazarenka.dao.DishDao;
import com.mnazarenka.dao.DishOrderDao;
import com.mnazarenka.dao.DishOrderDetailsDao;
import com.mnazarenka.dao.HotelDao;
import com.mnazarenka.dao.RestaurantDao;
import com.mnazarenka.dao.RoleDao;
import com.mnazarenka.dao.UserDao;
import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.DishOrderDetails;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.dao.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TestDataImporter {
    void importTestData();

    DishOrderDetails saveDishOrderDetail(Dish dish, DishOrder order, int count, DishOrderDetailsDao dishOrderDetailsDao);

    DishOrder saveDishOrder(User user, Appartment apartment, LocalDateTime time, DishOrderDao dishOrderDao);

    AppartmentOrder saveOrder(User user, Appartment appartment, LocalDate startDate, LocalDate endDate, AppartmentOrderDao appartmentOrderDao);

    LuxAppartment saveLuxAppartment(Hotel hotel, String name, String description, int guestCount, String image,
                                    boolean wifi, boolean wc, boolean tv, boolean bar, boolean kitchen, AppartmentDao appartmentDao);

    StandartAppartment saveStandartAppartment(Hotel hotel, String name, String description, int guestCount, String image,
                                              boolean wifi, boolean wc, boolean tv, AppartmentDao appartmentDao);

    EconomAppartment saveEconomAppartment(Hotel hotel, String name, String description, int guestCount, String image, boolean wifi, AppartmentDao appartmentDao);

    Dish saveDish(String name, String image, DishDao dishDao);

    Restaurant saveRestaurant(String name, Hotel hotel, RestaurantDao restaurantDao);

    Hotel saveHotel(String name, Adress adress, HotelDao hotelDao);

    User saveUser(boolean blockStatus, Role role, String login, String password, UserDao userDao);

    Role saveRole(String name, RoleDao roleDao);
}
