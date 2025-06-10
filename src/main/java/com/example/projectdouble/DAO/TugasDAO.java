package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Tugas;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TugasDAO {
    /**
     * Menambahkan tugas baru ke database.
     * @param tugas Objek Tugas yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addTugas(Tugas tugas) {
        String sql = "INSERT INTO tugas (judul, deskripsi, tanggal_deadline) VALUES (?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tugas.getJudul());
            stmt.setString(2, tugas.getDeskripsi());
            stmt.setDate(3, Date.valueOf(tugas.getTanggalDeadline()));
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tugas.setIdTugas(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan tugas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data tugas di database.
     * @param tugas Objek Tugas dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateTugas(Tugas tugas) {
        String sql = "UPDATE tugas SET judul = ?, deskripsi = ?, tanggal_deadline = ? WHERE id_tugas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tugas.getJudul());
            stmt.setString(2, tugas.getDeskripsi());
            stmt.setDate(3, Date.valueOf(tugas.getTanggalDeadline()));
            stmt.setInt(4, tugas.getIdTugas());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui tugas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus tugas dari database.
     * @param idTugas ID tugas yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteTugas(int idTugas) {
        String sql = "DELETE FROM tugas WHERE id_tugas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTugas);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus tugas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data tugas dari database.
     * @return List objek Tugas.
     */
    public List<Tugas> getAllTugas() {
        List<Tugas> tugasList = new ArrayList<>();
        String sql = "SELECT id_tugas, judul, deskripsi, tanggal_deadline FROM tugas ORDER BY tanggal_deadline DESC";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tugasList.add(new Tugas(
                        rs.getInt("id_tugas"),
                        rs.getString("judul"),
                        rs.getString("deskripsi"),
                        rs.getDate("tanggal_deadline").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua tugas: " + e.getMessage());
            e.printStackTrace();
        }
        return tugasList;
    }

    /**
     * Mengambil data tugas berdasarkan ID.
     * @param idTugas ID tugas yang dicari.
     * @return Objek Tugas jika ditemukan, null jika tidak.
     */
    public Tugas getTugasById(int idTugas) {
        String sql = "SELECT id_tugas, judul, deskripsi, tanggal_deadline FROM tugas WHERE id_tugas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTugas);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Tugas(
                        rs.getInt("id_tugas"),
                        rs.getString("judul"),
                        rs.getString("deskripsi"),
                        rs.getDate("tanggal_deadline").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil tugas berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
