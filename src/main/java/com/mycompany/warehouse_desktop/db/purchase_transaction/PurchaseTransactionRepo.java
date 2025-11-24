package com.mycompany.warehouse_desktop.db.purchase_transaction;

import com.mycompany.warehouse_desktop.db.DBConnection;
import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseTransactionRepo implements RepoInterface<PurchaseTransaction, Long> {

    @Override
    public PurchaseTransaction getFromResultSet(ResultSet rs) {
        try {
            return new PurchaseTransaction(
                    rs.getLong("id"),
                    rs.getTimestamp("thoigian"),
                    rs.getBoolean("dathanhtoan"),
                    rs.getLong("UseridNhanvien")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PurchaseTransaction findById(Long id) {
        String sql = """
                SELECT id, thoigian, dathanhtoan, UseridNhanvien
                FROM MotLuotNhap
                WHERE id = ?
                """;

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
    public List<PurchaseTransaction> getList(Integer page, Integer size) {
        List<PurchaseTransaction> list = new ArrayList<>();

        String sql = """
                SELECT DISTINCT id, thoigian, dathanhtoan, UseridNhanvien
                FROM MotLuotNhap
                ORDER BY id DESC
                LIMIT ?, ?
                """;

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
    public PurchaseTransaction create(PurchaseTransaction t) {
        String sql = """
                INSERT INTO MotLuotNhap(thoigian, dathanhtoan, UseridNhanvien)
                VALUES (?, ?, ?)
                """;

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
    public PurchaseTransaction update(Long id, PurchaseTransaction t) {
        String sql = """
                UPDATE MotLuotNhap
                SET thoigian = ?, dathanhtoan = ?, UseridNhanvien = ?
                WHERE id = ?
                """;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, t.getTimestamp());
            ps.setBoolean(2, t.getIsPaid());
            ps.setLong(3, t.getUserIdEmployee());
            ps.setLong(4, id);

            ps.executeUpdate();
            return findById(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM MotLuotNhap WHERE id = ?";

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

    public List<PurchaseTransaction> findByTime(Timestamp from, Timestamp to) {
        String sql = """
                SELECT * FROM MotLuotNhap
                WHERE thoigian BETWEEN ? AND ?
                """;

        List<PurchaseTransaction> list = new ArrayList<>();

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
