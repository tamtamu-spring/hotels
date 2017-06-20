package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlRoleDaoImpl;
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

        List<Role> roles = new MySqlRoleDaoImpl().findAll();
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
        return new MySqlRoleDaoImpl();
    }

    @Override
    public void testUpdate() {
        MySqlRoleDaoImpl mySqlRoleDaoImpl = new MySqlRoleDaoImpl();
        Role role = saveRole("Role", mySqlRoleDaoImpl);
        Long id = role.getId();

        role.setName("New name");
        mySqlRoleDaoImpl.update(role);

        role = mySqlRoleDaoImpl.find(id);

        assertEquals("New name", role.getName());

        mySqlRoleDaoImpl.delete(role);

    }

    private Role saveRole(String name, MySqlRoleDaoImpl mySqlRoleDaoImpl) {
        Role role = new Role();
        role.setName(name);
        mySqlRoleDaoImpl.create(role);
        return role;
    }

    private void createTestData() {
        MySqlRoleDaoImpl mySqlRoleDaoImpl = new MySqlRoleDaoImpl();
        adminRole = saveRole("Admin", mySqlRoleDaoImpl);
        userRole = saveRole("User", mySqlRoleDaoImpl);
    }

    private void destroyData() {
        MySqlRoleDaoImpl mySqlRoleDaoImpl = new MySqlRoleDaoImpl();
        mySqlRoleDaoImpl.delete(adminRole);
        mySqlRoleDaoImpl.delete(userRole);
    }

}
