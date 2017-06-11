package com.mnazarenka.util;


import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

        Restaurant firstRestaurant = saveRestaurant("FirstRestaurant", firstHotel, session);
        Restaurant secondRestaurant = saveRestaurant("SecondRestaurant", secondHotel, session);


        session.getTransaction().commit();
        session.close();
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
