package com.mycompany.warehouse_desktop.db.user;

import com.mycompany.warehouse_desktop.db.DBConnection;
import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements RepoInterface<UserEntity, Long> {

    public UserRepository() {}


    public Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();

    }

    @Override
    public UserEntity getFromResultSet(ResultSet rs) {
        try {
            return new UserEntity(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("sodienthoai"),
                    rs.getBoolean("enabled")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserEntity findById(Long id) {
        String sql = "SELECT id, username, password, email, sodienthoai, enabled FROM User WHERE id = ?;";
        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return this.getFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserEntity> getList(Integer sttPage, Integer sizePage) {
        String sql = "SELECT * FROM User ORDER BY id DESC LIMIT ?, ?;";
        List<UserEntity> list = new ArrayList<>();

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, (sttPage - 1) * sizePage);
            ps.setInt(2, sizePage);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(this.getFromResultSet(rs));

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserEntity create(UserEntity user) {
        String sql = "INSERT INTO User(username, password, email, sodienthoai, enabled) VALUES (?, ?, ?, ?, ?);";
        user.setId(null);

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setBoolean(5, user.getEnabled());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    return this.findById(id);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public UserEntity update(Long id, UserEntity user) {
        user.setId(id);
        String sql = "UPDATE User SET username=?, password=?, email=?, sodienthoai=?, enabled=? WHERE id=?;";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setBoolean(5, user.getEnabled());
            ps.setLong(6, user.getId());

            ps.executeUpdate();
            return this.findById(user.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM User WHERE id=?;";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public UserEntity findByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username=?;";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return this.getFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public UserEntity findByUsernamePassword(UserDto userDto) {
        String sql = "SELECT * FROM User WHERE username=? AND password=?;";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userDto.getUsername());
            ps.setString(2, userDto.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return this.getFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
