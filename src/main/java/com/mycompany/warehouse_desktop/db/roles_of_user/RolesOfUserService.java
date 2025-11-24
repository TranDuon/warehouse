package com.mycompany.warehouse_desktop.db.roles_of_user;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;

import java.util.List;

public class RolesOfUserService implements ServiceInterface<RolesOfUser, RolesOfUserId> {

    private final RolesOfUserRepo repository;

    public RolesOfUserService() {
        this.repository = new RolesOfUserRepo();
    }

    @Override
    public RolesOfUser findById(RolesOfUserId id) {
        return repository.findById(id);
    }

    @Override
    public List<RolesOfUser> getList(Integer page, Integer size) {
        return repository.getList(page, size);
    }

    @Override
    public RolesOfUser create(RolesOfUser entity) {
        return repository.create(entity);
    }

    @Override
    public RolesOfUser update(RolesOfUserId id, RolesOfUser entity) {
        entity.setId(id);
        return repository.update(id, entity);
    }

    @Override
    public Boolean delete(RolesOfUserId id) {
        return repository.delete(id);
    }

    public List<RolesOfUser> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
