package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.entity.Role;

public class MySqlRoleDao extends BaseDao<Role> {
    protected MySqlRoleDao() {
        super(Role.class);
    }
}
