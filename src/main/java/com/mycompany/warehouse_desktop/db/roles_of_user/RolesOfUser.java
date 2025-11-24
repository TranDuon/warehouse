package com.mycompany.warehouse_desktop.db.roles_of_user;

public class RolesOfUser {

    private RolesOfUserId id;

    public RolesOfUser() {
    }

    public RolesOfUser(RolesOfUserId id) {
        this.id = id;
    }

    public RolesOfUserId getId() {
        return id;
    }

    public void setId(RolesOfUserId id) {
        this.id = id;
    }
}