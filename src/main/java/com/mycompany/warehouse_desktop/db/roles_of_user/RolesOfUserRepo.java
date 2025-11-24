package com.mycompany.warehouse_desktop.db.roles_of_user;

import com.mycompany.warehouse_desktop.db.DBConnection;
import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolesOfUserRepo implements RepoInterface<RolesOfUser, RolesOfUserId> {

    @Override
    public RolesOfUser getFromResultSet(ResultSet rs) {
        try {
            return new RolesOfUser(
                    new RolesOfUserId(
                            rs.getLong("Userid"),
                            rs.getLong("Roleid")
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RolesOfUser findById(RolesOfUserId id) {
        String sql = "SELECT Userid, Roleid FROM RolesOfUser WHERE Userid = ? AND Roleid = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id.getUserId());
            ps.setLong(2, id.getRoleId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RolesOfUser> getList(Integer page, Integer size) {
        String sql = "SELECT Userid, Roleid FROM RolesOfUser ORDER BY Userid DESC, Roleid DESC LIMIT ?, ?;";

        List<RolesOfUser> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, (page - 1) * size);
            ps.setInt(2, size);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getFromResultSet(rs));
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public RolesOfUser create(RolesOfUser roleOfUser) {
        String sql = "INSERT INTO RolesOfUser(Userid, Roleid) VALUES (?, ?);";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, roleOfUser.getId().getUserId());
            ps.setLong(2, roleOfUser.getId().getRoleId());

            ps.executeUpdate();
            return findById(roleOfUser.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RolesOfUser update(RolesOfUserId id, RolesOfUser t) {
        // Bảng join không update, chỉ delete/insert
        return t;
    }

    @Override
    public Boolean delete(RolesOfUserId id) {
        String sql = "DELETE FROM RolesOfUser WHERE Userid = ? AND Roleid = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id.getUserId());
            ps.setLong(2, id.getRoleId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<RolesOfUser> findByUserId(Long userId) {
        String sql = "SELECT Userid, Roleid FROM RolesOfUser WHERE Userid = ?;";

        List<RolesOfUser> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getFromResultSet(rs));
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
