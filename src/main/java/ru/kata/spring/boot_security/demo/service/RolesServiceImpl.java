package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RolesDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

@Service
public class RolesServiceImpl implements RolesService{
    private final RolesDao rolesDao;

    public RolesServiceImpl(RolesDao rolesDao) {
        this.rolesDao = rolesDao;
    }


    @Override
    @Transactional
    public Set<Role> getAllRoles() {
        return rolesDao.getAllRoles();
    }

    @Override
    public Set<Role> getRoles(String[] roles) {
        return rolesDao.getRoles(roles);
    }
}
