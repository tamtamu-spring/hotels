package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Role;

public class MySqlRoleDao extends BaseDaoImpl<Role> {
    public MySqlRoleDao() {
        super(Role.class);
    }
}
