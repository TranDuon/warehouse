package com.mycompany.warehouse_desktop.db.role;

import com.mycompany.warehouse_desktop.db.interfaces.ServiceInterface;
import java.util.List;
//duong_td
public class RoleService implements ServiceInterface<Role, Long> {

    private final RoleRepo repo;

    public RoleService() {
        this.repo = new RoleRepo();
    }

    @Override
    public Role findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Role> getList(Integer page, Integer size) {
        return repo.getList(page, size);
    }

    @Override
    public Role create(Role role) {
        return repo.create(role);
    }

    @Override
    public Role update(Long id, Role role) {
        return repo.update(id, role);
    }

    @Override
    public Boolean delete(Long id) {
        return repo.delete(id);
    }

    public Role findByName(String name) {
        return repo.findByName(name);
    }
}
