package com.mycompany.warehouse_desktop.db.sale_order_item;

import com.mycompany.warehouse_desktop.db.DBConnection;
import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleOrderItemRepository implements RepoInterface<SaleOrderItem, SaleOrderItemId> {

    @Override
    public SaleOrderItem getFromResultSet(ResultSet rs) {
        try {
            return new SaleOrderItem(
                    new SaleOrderItemId(
                            rs.getLong("VatPhamid"),
                            rs.getLong("MotLuotBanid")
                    ),
                    rs.getInt("soluong"),
                    rs.getLong("gia")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;
    }

    @Override
    public SaleOrderItem findById(SaleOrderItemId id) {
        String sql = "SELECT VatPhamid, MotLuotBanid, soluong, gia FROM DsspBan WHERE VatPhamid=? AND MotLuotBanid=?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id.getProductId());
            ps.setLong(2, id.getSaleTransactionId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return getFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<SaleOrderItem> getList(Integer page, Integer size) {
        String sql = "SELECT * FROM DsspBan ORDER BY MotLuotBanid DESC, VatPhamid DESC LIMIT ?, ?;";

        List<SaleOrderItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, (page - 1) * size);
            ps.setInt(2, size);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(getFromResultSet(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public SaleOrderItem create(SaleOrderItem item) {
        String sql = "INSERT INTO DsspBan(VatPhamid, MotLuotBanid, soluong, gia) VALUES (?, ?, ?, ?);";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, item.getId().getProductId());
            ps.setLong(2, item.getId().getSaleTransactionId());
            ps.setInt(3, item.getQuantity());
            ps.setLong(4, item.getPrice());

            ps.executeUpdate();
            return findById(item.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SaleOrderItem update(SaleOrderItemId id, SaleOrderItem item) {
        String sql = "UPDATE DsspBan SET soluong = ?, gia = ? WHERE VatPhamid = ? AND MotLuotBanid = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getQuantity());
            ps.setLong(2, item.getPrice());
            ps.setLong(3, id.getProductId());
            ps.setLong(4, id.getSaleTransactionId());

            ps.executeUpdate();
            return findById(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean delete(SaleOrderItemId id) {
        String sql = "DELETE FROM DsspBan WHERE VatPhamid = ? AND MotLuotBanid = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id.getProductId());
            ps.setLong(2, id.getSaleTransactionId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean deleteByProductId(Long productId) {
        String sql = "DELETE FROM DsspBan WHERE VatPhamid = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, productId);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean deleteBySaleTransactionId(Long transactionId) {
        String sql = "DELETE FROM DsspBan WHERE MotLuotBanid = ?;";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, transactionId);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<SaleOrderItem> findBySaleTransactionId(Long transactionId) {
        String sql = "SELECT * FROM DsspBan WHERE MotLuotBanid=?;";

        List<SaleOrderItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, transactionId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(getFromResultSet(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
