package com.mycompany.warehouse_desktop.db.sale_transaction;

import com.mycompany.warehouse_desktop.db.DBConnection;
import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleTransactionRepository implements RepoInterface<SaleTransaction, Long> {

    @Override
    public SaleTransaction getFromResultSet(ResultSet rs) {
        try {
            return new SaleTransaction(
                    rs.getLong("id"),
                    rs.getTimestamp("thoigian"),
                    rs.getBoolean("dathanhtoan"),
                    rs.getLong("UseridNhanvien")
            );
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SaleTransaction findById(Long id) {
        String sql = "SELECT id, thoigian, dathanhtoan, UseridNhanvien FROM MotLuotBan WHERE id = ?;";

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
    public List<SaleTransaction> getList(Integer page, Integer size) {
        String sql = "SELECT * FROM MotLuotBan ORDER BY id DESC LIMIT ?, ?;";

        List<SaleTransaction> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, (page - 1) * size);
            ps.setInt(2, size);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public SaleTransaction create(SaleTransaction t) {
        String sql = "INSERT INTO MotLuotBan(thoigian, dathanhtoan, UseridNhanvien) VALUES (?, ?, ?);";
        t.setId(null);

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, t.getTimestamp());
            ps.setBoolean(2, t.getIsPaid());
            ps.setLong(3, t.getUserIdEmployee());

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
    public SaleTransaction update(Long id, SaleTransaction t) {
        String sql = "UPDATE MotLuotBan SET thoigian = ?, dathanhtoan = ?, UseridNhanvien = ? WHERE id = ?;";
        t.setId(id);

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, t.getTimestamp());
            ps.setBoolean(2, t.getIsPaid());
            ps.setLong(3, t.getUserIdEmployee());
            ps.setLong(4, t.getId());

            ps.executeUpdate();
            return findById(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM MotLuotBan WHERE id = ?;";

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

    public List<SaleTransaction> findByTime(Timestamp from, Timestamp to) {
        String sql = "SELECT * FROM MotLuotBan WHERE thoigian BETWEEN ? AND ?;";

        List<SaleTransaction> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, from);
            ps.setTimestamp(2, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(getFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
