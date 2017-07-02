package com.mnazarenka.service.impl;


import com.mnazarenka.dao.UserDao;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class CusomUserServiceImpl implements UserDetailsService {
    @Getter
    private UserDao dao;

    @Autowired
    public CusomUserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User loadedUser = dao.getUserByLogin(login);

        if (loadedUser == null) {
            throw new UsernameNotFoundException("Can't find user by provided name!");
        }

        return new org.springframework.security.core.userdetails.User(loadedUser.getLogin(), loadedUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(loadedUser.getRole().getName())));
    }
}
