package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Guru;
import com.example.projectdouble.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuruDAO {
    /**
     * Menambahkan data guru baru ke database.
     * @param guru Objek Guru yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addGuru(Guru guru) {
        String sql = "INSERT INTO guru (nip, nama, jenis_kelamin, email, no_hp) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, guru.getNip());
            stmt.setString(2, guru.getNama());
            stmt.setString(3, guru.getJenisKelamin());
            stmt.setString(4, guru.getEmail());
            stmt.setString(5, guru.getNoHp());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan guru: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data guru di database.
     * @param guru Objek Guru dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateGuru(Guru guru) {
        String sql = "UPDATE guru SET nama = ?, jenis_kelamin = ?, email = ?, no_hp = ? WHERE nip = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, guru.getNama());
            stmt.setString(2, guru.getJenisKelamin());
            stmt.setString(3, guru.getEmail());
            stmt.setString(4, guru.getNoHp());
            stmt.setString(5, guru.getNip());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui guru: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus data guru dari database.
     * @param nip NIP guru yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteGuru(String nip) {
        String sql = "DELETE FROM guru WHERE nip = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nip);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus guru: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data guru dari database.
     * @return List objek Guru.
     */
    public List<Guru> getAllGuru() {
        List<Guru> guruList = new ArrayList<>();
        // Query untuk mengambil guru, dan juga id_user dan username dari tabel users
        String sql = "SELECT g.nip, g.nama, g.jenis_kelamin, g.email, g.no_hp, u.id_user, u.username " +
                "FROM guru g LEFT JOIN users u ON g.nip = u.username AND u.role = (SELECT id_role FROM role WHERE nama_role = 'Guru')";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nip = rs.getString("nip");
                String nama = rs.getString("nama");
                String jenisKelamin = rs.getString("jenis_kelamin");
                String email = rs.getString("email");
                String noHp = rs.getString("no_hp");
                int idUser = rs.getInt("id_user"); // Akan 0 jika NULL
                String usernameUser = rs.getString("username"); // Akan null jika tidak ada user

                guruList.add(new Guru(nip, nama, jenisKelamin, email, noHp, idUser, usernameUser));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua guru: " + e.getMessage());
            e.printStackTrace();
        }
        return guruList;
    }

    /**
     * Mengambil data guru berdasarkan NIP.
     * @param nip NIP guru yang dicari.
     * @return Objek Guru jika ditemukan, null jika tidak.
     */
    public Guru getGuruByNip(String nip) {
        String sql = "SELECT g.nip, g.nama, g.jenis_kelamin, g.email, g.no_hp, u.id_user, u.username " +
                "FROM guru g LEFT JOIN users u ON g.nip = u.username AND u.role = (SELECT id_role FROM role WHERE nama_role = 'Guru') WHERE g.nip = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nip);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idUser = rs.getInt("id_user");
                String usernameUser = rs.getString("username");
                return new Guru(
                        rs.getString("nip"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("email"),
                        rs.getString("no_hp"),
                        idUser,
                        usernameUser
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil guru berdasarkan NIP: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mencari guru berdasarkan NIP atau Nama.
     * @param keyword Kata kunci pencarian.
     * @return List objek Guru yang cocok.
     */
    public List<Guru> searchGuru(String keyword) {
        List<Guru> guruList = new ArrayList<>();
        String sql = "SELECT g.nip, g.nama, g.jenis_kelamin, g.email, g.no_hp, u.id_user, u.username " +
                "FROM guru g LEFT JOIN users u ON g.nip = u.username AND u.role = (SELECT id_role FROM role WHERE nama_role = 'Guru') " +
                "WHERE g.nip ILIKE ? OR g.nama ILIKE ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nip = rs.getString("nip");
                String nama = rs.getString("nama");
                String jenisKelamin = rs.getString("jenis_kelamin");
                String email = rs.getString("email");
                String noHp = rs.getString("no_hp");
                int idUser = rs.getInt("id_user");
                String usernameUser = rs.getString("username");
                guruList.add(new Guru(nip, nama, jenisKelamin, email, noHp, idUser, usernameUser));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari guru: " + e.getMessage());
            e.printStackTrace();
        }
        return guruList;
    }

    /**
     * Memperbarui id_user di tabel guru setelah user dibuat.
     * @param guru Objek Guru dengan id_user yang sudah diatur.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateGuruUser(Guru guru) {
        String sql = "UPDATE guru SET id_user = ? WHERE nip = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, guru.getIdUser());
            stmt.setString(2, guru.getNip());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui id_user guru: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
