package com.mnazarenka.dao.mysql;

import com.mnazarenka.dao.RoleDao;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlRoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

}
