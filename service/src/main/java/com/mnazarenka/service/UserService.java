package com.mnazarenka.service;

import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.common.BaseService;

public interface UserService extends BaseService<User> {
    User getUserByLogin(String login);
    User createUserWithRoleId(User user, Long roleId);
    User createUserWithUserRole(User user);
}
