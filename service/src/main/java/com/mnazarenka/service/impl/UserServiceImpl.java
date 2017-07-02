package com.mnazarenka.service.impl;

import com.mnazarenka.dao.RoleDao;
import com.mnazarenka.dao.UserDao;
import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.UserService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Getter
    private UserDao dao;
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao dao, RoleDao roleDao) {
        this.dao = dao;
        this.roleDao = roleDao;
    }

    @Override
    public User create(User user, Long roleId) {
        Role role = roleDao.find(roleId);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return dao.create(user);
    }

    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return dao.create(entity);
    }

    @Override
    public User getUserByLogin(String login) {
        return dao.getUserByLogin(login);
    }
}
