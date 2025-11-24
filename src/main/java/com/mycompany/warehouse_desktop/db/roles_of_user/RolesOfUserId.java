package com.mycompany.warehouse_desktop.db.roles_of_user;

public class RolesOfUserId {

    private Long userId;
    private Long roleId;

    public RolesOfUserId() {}

    public RolesOfUserId(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Quyền của người dùng { " +
                "Mã người dùng=" + userId +
                ", Mã quyền=" + roleId +
                " }";
    }
}
