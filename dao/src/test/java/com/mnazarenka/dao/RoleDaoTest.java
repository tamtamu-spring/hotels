package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import lombok.Getter;
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
    @Getter
    private RoleDao dao;

    @Test
    public void testFindAll() {
        List<Role> roles = dao.findAll();
        List<String> rolesNames = roles.stream().map(Role::getName)
                .collect(toList());

        assertThat(roles, hasSize(2));
        assertThat(rolesNames, containsInAnyOrder("User", "Admin"));
    }

    @Override
    public void testUpdate() {
        Role role = getTestDataImporter().saveRole("Role", dao);
        Long id = role.getId();

        role.setName("New name");
        dao.update(role);

        role = dao.find(id);

        assertEquals("New name", role.getName());
    }
}
