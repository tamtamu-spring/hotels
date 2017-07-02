package com.mnazarenka.service;

import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.common.BaseService;

public interface UserService extends BaseService<User> {
    User getUserByLogin(String login);
    User create(User user, Long roleId);
}
