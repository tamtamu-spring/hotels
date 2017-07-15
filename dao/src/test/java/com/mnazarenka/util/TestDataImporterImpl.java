package com.mnazarenka.util;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TestDataImporterImpl implements TestDataImporter {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HotelDao hotelDao;
    @Autowired
    private AppartmentDao appartmentDao;
    @Autowired
    private AppartmentOrderDao appartmentOrderDao;
    @Autowired
    private DishDao dishDao;
    @Autowired
    private DishOrderDao dishOrderDao;
    @Autowired
    private DishOrderDetailsDao dishOrderDetailsDao;
    @Autowired
    private RestaurantDao restaurantDao;

    @Override
    public void importTestData() {

        Role adminRole = saveRole("Admin", roleDao);
        Role userRole = saveRole("User", roleDao);

        User adminUser = saveUser(false, adminRole, "AdminLogin", "AdminPassword", userDao);
        User userUser = saveUser(true, userRole, "UserLogin", "UserPassword", userDao);

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel firstHotel = saveHotel("FirstHotel", firstHotelAdress, hotelDao);

        Adress secondAdress = new Adress();
        secondAdress.setCity("SecondCity");
        secondAdress.setStreet("SecondStreet");
        Hotel secondHotel = saveHotel("SecondHotel", secondAdress, hotelDao);

        Adress thirdAdress = new Adress();
        thirdAdress.setCity("ThirdCity");
        thirdAdress.setStreet("ThirdStreet");
        Hotel thirdHotel = saveHotel("ThirdHotel", thirdAdress, hotelDao);

        EconomAppartment economAppartment = saveEconomAppartment(firstHotel, "EconomAppartmentName", "EconomAppartmentDescription",
                1, true, appartmentDao);
        StandartAppartment standartAppartment = saveStandartAppartment(firstHotel, "StandartAppartmentName", "StandartAppartmentDescription",
                2, true, true, true, appartmentDao);
        LuxAppartment luxAppartment = saveLuxAppartment(firstHotel, "LuxAppartmentName", "LuxAppartmentDescription",
                4, true, true, true, true, true, appartmentDao);

        AppartmentOrder firstOrder = saveOrder(userUser, economAppartment,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), appartmentOrderDao);
        AppartmentOrder secondOrder = saveOrder(adminUser, luxAppartment,
                LocalDate.of(2017, 11, 20), LocalDate.of(2017, 11, 25), appartmentOrderDao);

        Dish firstDish = saveDish("FirstDish", dishDao);
        Dish secondDish = saveDish("SecondDish", dishDao);

        Restaurant firstRestaurant = saveRestaurant("FirstRestaurant", firstHotel, restaurantDao);
        firstRestaurant.getDishes().add(firstDish);
        firstRestaurant.getDishes().add(secondDish);

        Restaurant secondRestaurant = saveRestaurant("SecondRestaurant", secondHotel, restaurantDao);
        secondRestaurant.getDishes().add(firstDish);
        secondRestaurant.getDishes().add(secondDish);

        DishOrder firstDishOrder = saveDishOrder(userUser, economAppartment, LocalDateTime.of(LocalDate.now(), LocalTime.MAX), dishOrderDao);
        DishOrder secondDishOrder = saveDishOrder(adminUser, luxAppartment, LocalDateTime.of(LocalDate.now(), LocalTime.MIN), dishOrderDao);

        DishOrderDetails firstDetail = saveDishOrderDetail(firstDish, firstDishOrder, 1, dishOrderDetailsDao);
        DishOrderDetails secondDetail = saveDishOrderDetail(secondDish, firstDishOrder, 2, dishOrderDetailsDao);
    }

    @Override
    public DishOrderDetails saveDishOrderDetail(Dish dish, DishOrder order, int count, DishOrderDetailsDao dishOrderDetailsDao) {
        DishOrderDetails dishOrderDetails = new DishOrderDetails();
        dishOrderDetails.setDish(dish);
        dishOrderDetails.setOrder(order);
        dishOrderDetails.setCount(count);
        dishOrderDetailsDao.create(dishOrderDetails);
        return dishOrderDetails;
    }

    @Override
    public DishOrder saveDishOrder(User user, Appartment apartment, LocalDateTime time, DishOrderDao dishOrderDao) {
        DishOrder dishOrder = new DishOrder();
        dishOrder.setUser(user);
        dishOrder.setAppartment(apartment);
        dishOrder.setOrderTime(time);
        dishOrderDao.create(dishOrder);
        return dishOrder;
    }

    @Override
    public AppartmentOrder saveOrder(User user, Appartment appartment, LocalDate startDate, LocalDate endDate, AppartmentOrderDao appartmentOrderDao) {
        AppartmentOrder order = new AppartmentOrder();
        order.setUser(user);
        order.setAppartment(appartment);
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        appartmentOrderDao.create(order);
        return order;
    }

    @Override
    public LuxAppartment saveLuxAppartment(Hotel hotel, String name, String description, int guestCount,
                                           boolean wifi, boolean wc, boolean tv, boolean bar, boolean kitchen, AppartmentDao appartmentDao) {
        LuxAppartment luxAppartment = new LuxAppartment();
        luxAppartment.setHotel(hotel);
        luxAppartment.setName(name);
        luxAppartment.setGuestsCounts(guestCount);
        luxAppartment.setWiFi(wifi);
        luxAppartment.setWc(wc);
        luxAppartment.setTv(tv);
        luxAppartment.setBar(bar);
        luxAppartment.setKitchen(kitchen);
        appartmentDao.create(luxAppartment);
        return luxAppartment;

    }

    @Override
    public StandartAppartment saveStandartAppartment(Hotel hotel, String name, String description, int guestCount,
                                                     boolean wifi, boolean wc, boolean tv, AppartmentDao appartmentDao) {
        StandartAppartment standartAppartment = new StandartAppartment();
        standartAppartment.setHotel(hotel);
        standartAppartment.setName(name);
        standartAppartment.setGuestsCounts(guestCount);
        standartAppartment.setWiFi(wifi);
        standartAppartment.setWc(wc);
        standartAppartment.setTv(tv);
        appartmentDao.create(standartAppartment);
        return standartAppartment;
    }

    @Override
    public EconomAppartment saveEconomAppartment(Hotel hotel, String name, String description, int guestCount, boolean wifi, AppartmentDao appartmentDao) {
        EconomAppartment economAppartment = new EconomAppartment();
        economAppartment.setHotel(hotel);
        economAppartment.setName(name);
        economAppartment.setGuestsCounts(guestCount);
        economAppartment.setWiFi(wifi);
        appartmentDao.create(economAppartment);
        return economAppartment;
    }

    @Override
    public Dish saveDish(String name, DishDao dishDao) {
        Dish dish = new Dish();
        dish.setName(name);
        dishDao.create(dish);
        return dish;
    }

    @Override
    public Restaurant saveRestaurant(String name, Hotel hotel, RestaurantDao restaurantDao) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setHotel(hotel);
        restaurantDao.create(restaurant);
        return restaurant;
    }

    @Override
    public Hotel saveHotel(String name, Adress adress, HotelDao hotelDao) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setAdress(adress);
        hotelDao.create(hotel);
        return hotel;
    }

    @Override
    public User saveUser(boolean blockStatus, Role role, String login, String password, UserDao userDao) {
        User user = new User();
        user.setRole(role);
        user.setLogin(login);
        user.setPassword(password);
        userDao.create(user);
        return user;
    }

    @Override
    public Role saveRole(String name, RoleDao roleDao) {
        Role role = new Role();
        role.setName(name);
        roleDao.create(role);
        return role;
    }
}
