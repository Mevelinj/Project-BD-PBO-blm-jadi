package com.example.projectdouble.DAO;

import com.example.projectdouble.model.TahunAjaran;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TahunAjaranDAO {

    /**
     * Menambahkan tahun ajaran baru ke database.
     * @param tahunAjaran Objek TahunAjaran yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */

    public boolean addTahunAjaran(TahunAjaran tahunAjaran) {
        // SQL query updated: removed tahun_ganjil_genap from INSERT statement
        String sql = "INSERT INTO tahun_ajaran (tahun_mulai, tahun_selesai) VALUES (?, ?) RETURNING id_tahun_ajaran";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, tahunAjaran.getTahunMulai());
            stmt.setInt(2, tahunAjaran.getTahunSelesai());
            // Removed: stmt.setString(3, tahunAjaran.getTahunGanjilGenap()); // This line is no longer needed

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tahunAjaran.setIdTahunAjaran(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan tahun ajaran: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua tahun ajaran dari database.
     * @return List objek TahunAjaran.
     */
    public List<TahunAjaran> getAllTahunAjaran() {
        List<TahunAjaran> tahunAjaranList = new ArrayList<>();
        // Menggunakan nama tabel lowercase 'tahun_ajaran'
        // SQL query updated: removed tahun_ganjil_genap from SELECT clause
        String sql = "SELECT id_tahun_ajaran, tahun_mulai, tahun_selesai FROM tahun_ajaran ORDER BY tahun_mulai DESC";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tahunAjaranList.add(new TahunAjaran(
                        rs.getInt("id_tahun_ajaran"),
                        rs.getInt("tahun_mulai"),
                        rs.getInt("tahun_selesai")
                        // Removed: rs.getString("tahun_ganjil_genap")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua tahun ajaran: " + e.getMessage());
            e.printStackTrace();
        }
        return tahunAjaranList;
    }

    /**
     * Mengambil tahun ajaran berdasarkan ID.
     * @param idTahunAjaran ID tahun ajaran.
     * @return Objek TahunAjaran jika ditemukan, null jika tidak.
     */
    public TahunAjaran getTahunAjaranById(int idTahunAjaran) {
        // SQL query updated: removed tahun_ganjil_genap from SELECT clause
        String sql = "SELECT id_tahun_ajaran, tahun_mulai, tahun_selesai FROM tahun_ajaran WHERE id_tahun_ajaran = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTahunAjaran);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TahunAjaran(
                        rs.getInt("id_tahun_ajaran"),
                        rs.getInt("tahun_mulai"),
                        rs.getInt("tahun_selesai")
                        // Removed: rs.getString("tahun_ganjil_genap")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil tahun ajaran berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Memperbarui data tahun ajaran di database.
     * @param tahunAjaran Objek TahunAjaran dengan data terbaru.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateTahunAjaran(TahunAjaran tahunAjaran) {
        // SQL query updated: removed tahun_ganjil_genap from SET clause
        String sql = "UPDATE tahun_ajaran SET tahun_mulai = ?, tahun_selesai = ? WHERE id_tahun_ajaran = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tahunAjaran.getTahunMulai());
            stmt.setInt(2, tahunAjaran.getTahunSelesai());
            // Removed: stmt.setString(3, tahunAjaran.getTahunGanjilGenap());
            stmt.setInt(3, tahunAjaran.getIdTahunAjaran()); // Index changed from 4 to 3
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui tahun ajaran: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus tahun ajaran dari database berdasarkan ID.
     * @param idTahunAjaran ID tahun ajaran yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteTahunAjaran(int idTahunAjaran) {
        // Menggunakan nama tabel lowercase 'tahun_ajaran'
        String sql = "DELETE FROM tahun_ajaran WHERE id_tahun_ajaran = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTahunAjaran);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus tahun ajaran: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
