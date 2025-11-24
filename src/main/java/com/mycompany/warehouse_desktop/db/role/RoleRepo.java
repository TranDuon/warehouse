package com.mycompany.warehouse_desktop.db.role;

import com.mycompany.warehouse_desktop.db.DBConnection;
import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepo implements RepoInterface<Role, Long> {

    @Override
    public Role getFromResultSet(ResultSet rs) {
        try {
            return new Role(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("mota")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Role findById(Long id) {
        String sql = "SELECT id, name, mota FROM Role WHERE id = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
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
    public List<Role> getList(Integer page, Integer size) {
        String sql = "SELECT id, name, mota FROM Role ORDER BY id DESC LIMIT ?, ?";

        List<Role> roles = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, (page - 1) * size);
            ps.setInt(2, size);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                roles.add(getFromResultSet(rs));
            }

            return roles;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Role create(Role role) {
        String sql = "INSERT INTO Role(name, mota) VALUES (?, ?);";
        role.setId(null);

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, role.getName());
            ps.setString(2, role.getDescription());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return findById(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Role update(Long id, Role role) {
        String sql = "UPDATE Role SET name = ?, mota = ? WHERE id = ?;";
        role.setId(id);

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, role.getName());
            ps.setString(2, role.getDescription());
            ps.setLong(3, role.getId());

            ps.executeUpdate();
            return findById(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM Role WHERE id = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Role findByName(String name) {
        String sql = "SELECT * FROM Role WHERE name = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
