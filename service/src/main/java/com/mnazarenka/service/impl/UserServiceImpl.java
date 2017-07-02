package com.mnazarenka.service.impl;

import com.mnazarenka.dao.UserDao;
import com.mnazarenka.dao.common.BaseDao;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.UserService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService, UserDetailsService {

    @Getter
    private UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User loadedUser = dao.getUserByLogin(login);

        if (loadedUser == null) {
            throw new UsernameNotFoundException("Can't find user by provided name!");
        }

        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(loadedUser.getLogin(), loadedUser.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(loadedUser.getRole().getName())));
        return user;
    }
}
