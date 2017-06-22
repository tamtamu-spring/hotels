package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RoleDaoTest extends BaseDaoTest<Role, RoleDao> {
    @Autowired
    private RoleDao roleDao;

    private Role userRole;
    private Role adminRole;

    @Test
    public void testFindAll() {

        createTestData();

        List<Role> roles = roleDao.findAll();
        List<String> rolesNames = roles.stream().map(Role::getName)
                .collect(toList());

        assertThat(roles, hasSize(2));
        assertThat(rolesNames, containsInAnyOrder("User", "Admin"));

        destroyData();
    }

   /* @Override
    public Role getEntity() {
        return new Role();
    }

    @Override
    public BaseDaoImpl<Role> getCurrentDao() {
        return new MySqlRoleDaoImpl();
    }*/

    @Override
    public void testUpdate() {
        Role role = saveRole("Role", roleDao);
        Long id = role.getId();

        role.setName("New name");
        roleDao.update(role);

        role = roleDao.find(id);

        assertEquals("New name", role.getName());

        roleDao.delete(role);

    }

    private Role saveRole(String name, RoleDao roleDao) {
        Role role = new Role();
        role.setName(name);
        roleDao.create(role);
        return role;
    }

    private void createTestData() {
        adminRole = saveRole("Admin", roleDao);
        userRole = saveRole("User", roleDao);
    }

    private void destroyData() {
        roleDao.delete(adminRole);
        roleDao.delete(userRole);
    }

}
