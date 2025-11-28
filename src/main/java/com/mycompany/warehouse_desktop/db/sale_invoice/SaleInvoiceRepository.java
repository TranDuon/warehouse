//package com.mycompany.warehouse_desktop.db.sale_invoice;
//
//import com.mycompany.warehouse_desktop.db.DBConnection;
//import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SaleInvoiceRepository implements RepoInterface<SaleInvoice, Long> {
//
//    @Override
//    public SaleInvoice getFromResultSet(ResultSet rs) {
//        try {
//            return new SaleInvoice(
//                    rs.getLong("id"),
//                    rs.getTimestamp("thoigian"),
//                    rs.getLong("sotien"),
//                    rs.getBoolean("lathanhtoantienmat"),
//                    rs.getLong("MotLuotBanid")
//            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public SaleInvoice findById(Long id) {
//        String sql = "SELECT id, thoigian, sotien, lathanhtoantienmat, MotLuotBanid FROM HoaDonBan WHERE id = ?;";
//
//        try (Connection con = DBConnection.getInstance().getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setLong(1, id);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                return getFromResultSet(rs);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    public List<SaleInvoice> getList(Integer page, Integer size) {
//        String sql = "SELECT * FROM HoaDonBan ORDER BY id DESC LIMIT ?, ?;";
//
//        List<SaleInvoice> list = new ArrayList<>();
//
//        try (Connection con = DBConnection.getInstance().getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, (page - 1) * size);
//            ps.setInt(2, size);
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                list.add(getFromResultSet(rs));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//
//    @Override
//    public SaleInvoice create(SaleInvoice t) {
//        String sql = "INSERT INTO HoaDonBan(thoigian, sotien, lathanhtoantienmat, MotLuotBanid) VALUES (?, ?, ?, ?);";
//
//        t.setId(null);
//
//        try (Connection con = DBConnection.getInstance().getConnection();
//             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            ps.setTimestamp(1, t.getTimestamp());
//            ps.setLong(2, t.getAmount());
//            ps.setBoolean(3, t.getIsCashPayment());
//            ps.setLong(4, t.getSaleTransactionId());
//
//            ps.executeUpdate();
//
//            ResultSet rs = ps.getGeneratedKeys();
//            if (rs.next()) return findById(rs.getLong(1));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    public SaleInvoice update(Long id, SaleInvoice t) {
//        String sql = "UPDATE HoaDonBan SET thoigian = ?, sotien = ?, lathanhtoantienmat = ?, MotLuotBanid = ? WHERE id = ?;";
//
//        t.setId(id);
//
//        try (Connection con = DBConnection.getInstance().getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setTimestamp(1, t.getTimestamp());
//            ps.setLong(2, t.getAmount());
//            ps.setBoolean(3, t.getIsCashPayment());
//            ps.setLong(4, t.getSaleTransactionId());
//            ps.setLong(5, t.getId());
//
//            ps.executeUpdate();
//            return findById(t.getId());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    public Boolean delete(Long id) {
//        String sql = "DELETE FROM HoaDonBan WHERE id = ?;";
//
//        try (Connection con = DBConnection.getInstance().getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setLong(1, id);
//            ps.executeUpdate();
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//}
