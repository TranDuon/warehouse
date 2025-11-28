package com.mycompany.warehouse_desktop.controller;

import com.mycompany.warehouse_desktop.db.user.UserEntity;

public class Session {

    private static UserEntity currentUser;

    public static void set(UserEntity user) {
        currentUser = user;
    }

    public static UserEntity get() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}
