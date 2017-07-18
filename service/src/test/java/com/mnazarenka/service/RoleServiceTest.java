package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Role;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class RoleServiceTest extends BaseServiceTest<Role> {
    @Autowired
    @Getter
    private RoleService service;

    @Override
    public void testFindAll() {
        List<Role> roles = service.findAll();
        assertThat(roles, hasSize(2));
    }

}
