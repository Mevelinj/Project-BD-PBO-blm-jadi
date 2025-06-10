package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Kelas;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KelasDAO {
    /**
     * Menambahkan kelas baru ke database.
     * @param kelas Objek Kelas yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addKelas(Kelas kelas) {
        String sql = "INSERT INTO kelas (nama_kelas, tingkat) VALUES (?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, kelas.getNamaKelas());
            stmt.setString(2, kelas.getTingkat()); // Pastikan tingkat diisi atau default di UI
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        kelas.setIdKelas(generatedKeys.getInt(1)); // Set ID yang dihasilkan
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data kelas di database.
     * @param kelas Objek Kelas dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateKelas(Kelas kelas) {
        String sql = "UPDATE kelas SET nama_kelas = ?, tingkat = ? WHERE id_kelas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kelas.getNamaKelas());
            stmt.setString(2, kelas.getTingkat());
            stmt.setInt(3, kelas.getIdKelas());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus kelas dari database.
     * @param idKelas ID kelas yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteKelas(int idKelas) {
        String sql = "DELETE FROM kelas WHERE id_kelas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKelas);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data kelas dari database.
     * @return List objek Kelas.
     */
    public List<Kelas> getAllKelas() {
        List<Kelas> kelasList = new ArrayList<>();
        String sql = "SELECT id_kelas, nama_kelas, tingkat FROM kelas";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                kelasList.add(new Kelas(
                        rs.getInt("id_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getString("tingkat")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua kelas: " + e.getMessage());
            e.printStackTrace();
        }
        return kelasList;
    }

    /**
     * Mengambil data kelas berdasarkan ID.
     * @param idKelas ID kelas yang dicari.
     * @return Objek Kelas jika ditemukan, null jika tidak.
     */
    public Kelas getKelasById(int idKelas) {
        String sql = "SELECT id_kelas, nama_kelas, tingkat FROM kelas WHERE id_kelas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKelas);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Kelas(
                        rs.getInt("id_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getString("tingkat")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil kelas berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mencari kelas berdasarkan nama atau tingkat.
     * @param keyword Kata kunci pencarian.
     * @return List objek Kelas yang cocok.
     */
    public List<Kelas> searchKelas(String keyword) {
        List<Kelas> kelasList = new ArrayList<>();
        String sql = "SELECT id_kelas, nama_kelas, tingkat FROM kelas WHERE nama_kelas ILIKE ? OR tingkat ILIKE ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                kelasList.add(new Kelas(
                        rs.getInt("id_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getString("tingkat")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari kelas: " + e.getMessage());
            e.printStackTrace();
        }
        return kelasList;
    }

}
