package com.mycompany.warehouse_desktop.db.purchase_order_item;

import com.mycompany.warehouse_desktop.db.DBConnection;
import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderItemRepo implements RepoInterface<PurchaseOrderItem, PurchaseOrderItemId> {

    // Convert ResultSet → Entity
    @Override
    public PurchaseOrderItem getFromResultSet(ResultSet rs) {
        try {
            return new PurchaseOrderItem(
                    new PurchaseOrderItemId(
                            rs.getLong("VatPhamid"),
                            rs.getLong("MotLuotNhapid")
                    ),
                    rs.getInt("soluong"),
                    rs.getLong("gia")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PurchaseOrderItem findById(PurchaseOrderItemId id) {
        String sql = """
                SELECT VatPhamid, MotLuotNhapid, soluong, gia
                FROM DsspNhap
                WHERE VatPhamid = ? AND MotLuotNhapid = ?
                """;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id.getProductId());
            ps.setLong(2, id.getPurchaseTransactionId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return getFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PurchaseOrderItem> getList(Integer page, Integer size) {
        String sql = """
                SELECT DISTINCT *
                FROM DsspNhap
                ORDER BY MotLuotNhapid DESC, VatPhamid DESC
                LIMIT ?, ?
                """;

        List<PurchaseOrderItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, (page - 1) * size);
            ps.setInt(2, size);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(getFromResultSet(rs));
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PurchaseOrderItem create(PurchaseOrderItem item) {
        String sql = """
                INSERT INTO DsspNhap(VatPhamid, MotLuotNhapid, soluong, gia)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, item.getId().getProductId());
            ps.setLong(2, item.getId().getPurchaseTransactionId());
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
    public PurchaseOrderItem update(PurchaseOrderItemId id, PurchaseOrderItem item) {
        String sql = """
                UPDATE DsspNhap
                SET soluong = ?, gia = ?
                WHERE VatPhamid = ? AND MotLuotNhapid = ?
                """;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getQuantity());
            ps.setLong(2, item.getPrice());
            ps.setLong(3, id.getProductId());
            ps.setLong(4, id.getPurchaseTransactionId());

            ps.executeUpdate();
            return findById(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean delete(PurchaseOrderItemId id) {
        String sql = """
                DELETE FROM DsspNhap
                WHERE VatPhamid = ? AND MotLuotNhapid = ?
                """;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id.getProductId());
            ps.setLong(2, id.getPurchaseTransactionId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public Boolean deleteByProductId(Long productId) {
        String sql = "DELETE FROM DsspNhap WHERE VatPhamid = ?";

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

    public Boolean deleteByPurchaseTransactionId(Long transactionId) {
        String sql = "DELETE FROM DsspNhap WHERE MotLuotNhapid = ?";

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

    public List<PurchaseOrderItem> findByPurchaseTransactionId(Long transactionId) {
        String sql = """
                SELECT * FROM DsspNhap
                WHERE MotLuotNhapid = ?
                """;

        List<PurchaseOrderItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, transactionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) list.add(getFromResultSet(rs));
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
