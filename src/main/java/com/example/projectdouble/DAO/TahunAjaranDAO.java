package com.example.projectdouble.DAO;

import com.example.projectdouble.model.TahunAjaran;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TahunAjaranDAO {
    /**
     * Menambahkan tahun ajaran baru ke database.
     * @param tahunAjaran Objek TahunAjaran yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addTahunAjaran(TahunAjaran tahunAjaran) {
        String sql = "INSERT INTO tahun_ajaran (tahun_mulai, tahun_selesai, tahun_ganjil_genap) VALUES (?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, tahunAjaran.getTahunMulai());
            stmt.setInt(2, tahunAjaran.getTahunSelesai());
            stmt.setString(3, tahunAjaran.getTahunGanjilGenap());
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
     * Memperbarui data tahun ajaran di database.
     * @param tahunAjaran Objek TahunAjaran dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateTahunAjaran(TahunAjaran tahunAjaran) {
        String sql = "UPDATE tahun_ajaran SET tahun_mulai = ?, tahun_selesai = ?, tahun_ganjil_genap = ? WHERE id_tahun_ajaran = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tahunAjaran.getTahunMulai());
            stmt.setInt(2, tahunAjaran.getTahunSelesai());
            stmt.setString(3, tahunAjaran.getTahunGanjilGenap());
            stmt.setInt(4, tahunAjaran.getIdTahunAjaran());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui tahun ajaran: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus tahun ajaran dari database.
     * @param idTahunAjaran ID tahun ajaran yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteTahunAjaran(int idTahunAjaran) {
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

    /**
     * Mengambil semua data tahun ajaran dari database.
     * @return List objek TahunAjaran.
     */
    public List<TahunAjaran> getAllTahunAjaran() {
        List<TahunAjaran> tahunAjaranList = new ArrayList<>();
        String sql = "SELECT id_tahun_ajaran, tahun_mulai, tahun_selesai, tahun_ganjil_genap FROM tahun_ajaran ORDER BY tahun_mulai DESC, tahun_ganjil_genap DESC";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tahunAjaranList.add(new TahunAjaran(
                        rs.getInt("id_tahun_ajaran"),
                        rs.getInt("tahun_mulai"),
                        rs.getInt("tahun_selesai"),
                        rs.getString("tahun_ganjil_genap")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua tahun ajaran: " + e.getMessage());
            e.printStackTrace();
        }
        return tahunAjaranList;
    }

    /**
     * Mengambil data tahun ajaran berdasarkan ID.
     * @param idTahunAjaran ID tahun ajaran yang dicari.
     * @return Objek TahunAjaran jika ditemukan, null jika tidak.
     */
    public TahunAjaran getTahunAjaranById(int idTahunAjaran) {
        String sql = "SELECT id_tahun_ajaran, tahun_mulai, tahun_selesai, tahun_ganjil_genap FROM tahun_ajaran WHERE id_tahun_ajaran = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTahunAjaran);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TahunAjaran(
                        rs.getInt("id_tahun_ajaran"),
                        rs.getInt("tahun_mulai"),
                        rs.getInt("tahun_selesai"),
                        rs.getString("tahun_ganjil_genap")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil tahun ajaran berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
