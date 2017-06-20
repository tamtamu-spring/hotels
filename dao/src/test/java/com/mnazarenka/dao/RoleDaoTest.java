package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlRoleDao;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RoleDaoTest extends BaseDaoTest<Role> {
    private Role userRole;
    private Role adminRole;


    @Test
    public void testFindAll() {

        createTestData();

        List<Role> roles = new MySqlRoleDao().findAll();
        List<String> rolesNames = roles.stream().map(Role::getName)
                .collect(toList());

        assertThat(roles, hasSize(2));
        assertThat(rolesNames, containsInAnyOrder("User", "Admin"));

        destroyData();
    }

    @Override
    public Role getEntity() {
        return new Role();
    }

    @Override
    public BaseDaoImpl<Role> getCurrentDao() {
        return new MySqlRoleDao();
    }

    @Override
    public void testUpdate() {
        MySqlRoleDao mySqlRoleDao = new MySqlRoleDao();
        Role role = saveRole("Role", mySqlRoleDao);
        Long id = role.getId();

        role.setName("New name");
        mySqlRoleDao.update(role);

        role = mySqlRoleDao.find(id);

        assertEquals("New name", role.getName());

        mySqlRoleDao.delete(role);

    }

    private Role saveRole(String name, MySqlRoleDao mySqlRoleDao) {
        Role role = new Role();
        role.setName(name);
        mySqlRoleDao.create(role);
        return role;
    }

    private void createTestData() {
        MySqlRoleDao mySqlRoleDao = new MySqlRoleDao();
        adminRole = saveRole("Admin", mySqlRoleDao);
        userRole = saveRole("User", mySqlRoleDao);
    }

    private void destroyData() {
        MySqlRoleDao mySqlRoleDao = new MySqlRoleDao();
        mySqlRoleDao.delete(adminRole);
        mySqlRoleDao.delete(userRole);
    }

}
