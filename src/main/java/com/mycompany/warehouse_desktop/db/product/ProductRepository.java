package com.mycompany.warehouse_desktop.db.product;

import com.mycompany.warehouse_desktop.db.DBConnection;
import com.mycompany.warehouse_desktop.db.interfaces.RepoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements RepoInterface<ProductEntity, Long> {

    @Override
    public ProductEntity getFromResultSet(ResultSet rs) {
        try {
            return new ProductEntity(
                    rs.getLong("id"),
                    rs.getString("ten"),
                    rs.getLong("gia"),
                    rs.getString("donvi"),
                    rs.getString("mota"),
                    rs.getInt("soluong")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ProductEntity findById(Long id) {
        String sql = "SELECT id, ten, gia, donvi, mota, soluong FROM VatPham WHERE id = ?;";

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
    public List<ProductEntity> getList(Integer page, Integer size) {
        String sql = "SELECT * FROM VatPham ORDER BY id DESC LIMIT ?, ?;";

        List<ProductEntity> list = new ArrayList<>();

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
    public ProductEntity create(ProductEntity p) {
        String sql = "INSERT INTO VatPham(ten, gia, donvi, mota, soluong) VALUES (?, ?, ?, ?, ?);";

        p.setId(null);

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getName());
            ps.setLong(2, p.getPrice());
            ps.setString(3, p.getUnit());
            ps.setString(4, p.getDescription());
            ps.setInt(5, p.getQuantity());

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
    public ProductEntity update(Long id, ProductEntity p) {
        p.setId(id);

        String sql = """
                UPDATE VatPham
                SET ten = ?, gia = ?, donvi = ?, mota = ?, soluong = ?
                WHERE id = ?
                """;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setLong(2, p.getPrice());
            ps.setString(3, p.getUnit());
            ps.setString(4, p.getDescription());
            ps.setInt(5, p.getQuantity());
            ps.setLong(6, p.getId());

            ps.executeUpdate();
            return findById(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM VatPham WHERE id = ?;";

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

    // Tìm theo tên hoặc mô tả
    public List<ProductEntity> findByName(String name) {
        String sql = "SELECT * FROM VatPham WHERE ten LIKE ? OR mota LIKE ?;";

        List<ProductEntity> list = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + name + "%");

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
