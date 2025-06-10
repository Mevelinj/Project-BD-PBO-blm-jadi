package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Ekstrakurikuler;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EkstrakurikulerDAO {
    /**
     * Menambahkan ekstrakurikuler baru ke database.
     * @param ekstrakurikuler Objek Ekstrakurikuler yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addEkstrakurikuler(Ekstrakurikuler ekstrakurikuler) {
        String sql = "INSERT INTO ekstrakurikuler (nama, tingkat) VALUES (?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ekstrakurikuler.getNama());
            stmt.setString(2, ekstrakurikuler.getTingkat());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ekstrakurikuler.setIdEkstrakurikuler(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data ekstrakurikuler di database.
     * @param ekstrakurikuler Objek Ekstrakurikuler dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateEkstrakurikuler(Ekstrakurikuler ekstrakurikuler) {
        String sql = "UPDATE ekstrakurikuler SET nama = ?, tingkat = ? WHERE id_ekstrakurikuler = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ekstrakurikuler.getNama());
            stmt.setString(2, ekstrakurikuler.getTingkat());
            stmt.setInt(3, ekstrakurikuler.getIdEkstrakurikuler());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus ekstrakurikuler dari database.
     * @param idEkstrakurikuler ID ekstrakurikuler yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteEkstrakurikuler(int idEkstrakurikuler) {
        String sql = "DELETE FROM ekstrakurikuler WHERE id_ekstrakurikuler = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEkstrakurikuler);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data ekstrakurikuler dari database.
     * @return List objek Ekstrakurikuler.
     */
    public List<Ekstrakurikuler> getAllEkstrakurikuler() {
        List<Ekstrakurikuler> ekstrakurikulerList = new ArrayList<>();
        String sql = "SELECT id_ekstrakurikuler, nama, tingkat FROM ekstrakurikuler ORDER BY nama";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ekstrakurikulerList.add(new Ekstrakurikuler(
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama"),
                        rs.getString("tingkat")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
        }
        return ekstrakurikulerList;
    }

    /**
     * Mengambil data ekstrakurikuler berdasarkan ID.
     * @param idEkstrakurikuler ID ekstrakurikuler yang dicari.
     * @return Objek Ekstrakurikuler jika ditemukan, null jika tidak.
     */
    public Ekstrakurikuler getEkstrakurikulerById(int idEkstrakurikuler) {
        String sql = "SELECT id_ekstrakurikuler, nama, tingkat FROM ekstrakurikuler WHERE id_ekstrakurikuler = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEkstrakurikuler);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ekstrakurikuler(
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama"),
                        rs.getString("tingkat")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil ekstrakurikuler berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
