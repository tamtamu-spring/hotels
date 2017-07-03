package com.mnazarenka.service.impl;

import com.mnazarenka.dao.RoleDao;
import com.mnazarenka.dao.UserDao;
import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.UserService;
import com.mnazarenka.service.common.BaseServiceImpl;
import com.mnazarenka.service.enums.RoleEnum;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public User createUserWithUserRole(User user) {
        List<Role> roles = roleDao.findAll();
        roles.stream().filter(r -> r.getName().equals(RoleEnum.USER.toString())).collect(Collectors.toList());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roles.get(0));
        return dao.create(user);
    }

    @Override
    public User createUserWithRoleId(User user, Long roleId) {
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
