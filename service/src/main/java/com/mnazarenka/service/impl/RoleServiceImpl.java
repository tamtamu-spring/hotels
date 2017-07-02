package com.mnazarenka.service.impl;

import com.mnazarenka.dao.RoleDao;
import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.service.RoleService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Getter
    public RoleDao dao;

    @Autowired
    public RoleServiceImpl(RoleDao dao) {
        this.dao = dao;
    }
}
