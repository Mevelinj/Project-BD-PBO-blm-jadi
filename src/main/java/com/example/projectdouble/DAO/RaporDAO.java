package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Rapor;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaporDAO {
    /**
     * Menambahkan rapor baru ke database.
     * @param rapor Objek Rapor yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addRapor(Rapor rapor) {
        String sql = "INSERT INTO rapor (nis, semester, tanggal_cetak) VALUES (?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, rapor.getNis());
            stmt.setString(2, rapor.getSemester());
            stmt.setDate(3, Date.valueOf(rapor.getTanggalCetak()));
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rapor.setIdRapor(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan rapor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data rapor di database.
     * @param rapor Objek Rapor dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateRapor(Rapor rapor) {
        String sql = "UPDATE rapor SET nis = ?, semester = ?, tanggal_cetak = ? WHERE id_rapor = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rapor.getNis());
            stmt.setString(2, rapor.getSemester());
            stmt.setDate(3, Date.valueOf(rapor.getTanggalCetak()));
            stmt.setInt(4, rapor.getIdRapor());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui rapor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus rapor dari database.
     * @param idRapor ID rapor yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteRapor(int idRapor) {
        String sql = "DELETE FROM rapor WHERE id_rapor = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRapor);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus rapor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data rapor dari database.
     * @return List objek Rapor.
     */
    public List<Rapor> getAllRapor() {
        List<Rapor> raporList = new ArrayList<>();
        String sql = "SELECT id_rapor, nis, semester, tanggal_cetak FROM rapor ORDER BY tanggal_cetak DESC";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                raporList.add(new Rapor(
                        rs.getInt("id_rapor"),
                        rs.getString("nis"),
                        rs.getString("semester"),
                        rs.getDate("tanggal_cetak").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua rapor: " + e.getMessage());
            e.printStackTrace();
        }
        return raporList;
    }

    /**
     * Mengambil data rapor berdasarkan ID.
     * @param idRapor ID rapor yang dicari.
     * @return Objek Rapor jika ditemukan, null jika tidak.
     */
    public Rapor getRaporById(int idRapor) {
        String sql = "SELECT id_rapor, nis, semester, tanggal_cetak FROM rapor WHERE id_rapor = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRapor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Rapor(
                        rs.getInt("id_rapor"),
                        rs.getString("nis"),
                        rs.getString("semester"),
                        rs.getDate("tanggal_cetak").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil rapor berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil daftar rapor untuk siswa tertentu.
     * @param nis NIS siswa.
     * @return List objek Rapor.
     */
    public List<Rapor> getRaporByNis(String nis) {
        List<Rapor> raporList = new ArrayList<>();
        String sql = "SELECT id_rapor, nis, semester, tanggal_cetak FROM rapor WHERE nis = ? ORDER BY tanggal_cetak DESC";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                raporList.add(new Rapor(
                        rs.getInt("id_rapor"),
                        rs.getString("nis"),
                        rs.getString("semester"),
                        rs.getDate("tanggal_cetak").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil rapor berdasarkan NIS: " + e.getMessage());
            e.printStackTrace();
        }
        return raporList;
    }

}
