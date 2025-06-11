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

    private UserDAO userDao = new UserDAO(); // Digunakan untuk operasi terkait user

    /**
     * Menambahkan data guru baru ke database.
     * @param guru Objek Guru yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addGuru(Guru guru) {
        // Menggunakan nama tabel lowercase 'guru'
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
            System.err.println("Error saat menambahkan data guru: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data guru di database.
     * @param guru Objek Guru dengan data terbaru.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateGuru(Guru guru) {
        // Menggunakan nama tabel lowercase 'guru'
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
            System.err.println("Error saat memperbarui data guru: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil data guru berdasarkan NIP.
     * @param nip NIP guru yang dicari.
     * @return Objek Guru jika ditemukan, null jika tidak.
     */
    public Guru getGuruByNip(String nip) {
        // Menggunakan nama tabel lowercase 'guru' dan 'users'
        String sql = "SELECT g.nip, g.nama, g.jenis_kelamin, g.email, g.no_hp, u.id_user, u.username, u.password " +
                "FROM guru g LEFT JOIN users u ON g.nip = u.username WHERE g.nip = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nip);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Guru(
                        rs.getString("nip"),
                        (Integer) rs.getObject("id_user"), // idUser bisa null
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("email"),
                        rs.getString("no_hp"),
                        rs.getString("username"), // username_user bisa null
                        rs.getString("password") // password_user bisa null
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil guru berdasarkan NIP: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil semua data guru dari database.
     * @return List objek Guru.
     */
    public List<Guru> getAllGuru() {
        List<Guru> guruList = new ArrayList<>();
        // Menggunakan nama tabel lowercase 'guru' dan 'users'
        String sql = "SELECT g.nip, g.nama, g.jenis_kelamin, g.email, g.no_hp, u.id_user, u.username, u.password " +
                "FROM guru g LEFT JOIN users u ON g.nip = u.username";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                guruList.add(new Guru(
                        rs.getString("nip"),
                        (Integer) rs.getObject("id_user"), // idUser bisa null
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("email"),
                        rs.getString("no_hp"),
                        rs.getString("username"), // username_user bisa null
                        rs.getString("password") // password_user bisa null
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua data guru: " + e.getMessage());
            e.printStackTrace();
        }
        return guruList;
    }

    /**
     * Mencari guru berdasarkan keyword di nama atau NIP.
     * @param keyword Kata kunci pencarian.
     * @return List objek Guru yang cocok.
     */
    public List<Guru> searchGuru(String keyword) {
        List<Guru> guruList = new ArrayList<>();
        // Menggunakan nama tabel lowercase 'guru' dan 'users'
        String sql = "SELECT g.nip, g.nama, g.jenis_kelamin, g.email, g.no_hp, u.id_user, u.username, u.password " +
                "FROM guru g LEFT JOIN users u ON g.nip = u.username " +
                "WHERE g.nama ILIKE ? OR g.nip ILIKE ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                guruList.add(new Guru(
                        rs.getString("nip"),
                        (Integer) rs.getObject("id_user"), // idUser bisa null
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("email"),
                        rs.getString("no_hp"),
                        rs.getString("username"), // username_user bisa null
                        rs.getString("password") // password_user bisa null
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari guru: " + e.getMessage());
            e.printStackTrace();
        }
        return guruList;
    }

    /**
     * Menghapus data guru dari database.
     * Ini juga akan menghapus user terkait jika ada.
     * @param nip NIP guru yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteGuru(String nip) {
        Guru guruToDelete = getGuruByNip(nip); // Ambil data guru lengkap
        if (guruToDelete == null) {
            System.err.println("Guru dengan NIP " + nip + " tidak ditemukan untuk dihapus.");
            return false;
        }

        // Hapus entri terkait dari tabel 'pembina' (jika ada)
        String sqlDeletePembina = "DELETE FROM pembina WHERE nip = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmtPembina = conn.prepareStatement(sqlDeletePembina)) {
            stmtPembina.setString(1, nip);
            stmtPembina.executeUpdate(); // Lanjutkan meskipun tidak ada baris yang terpengaruh
        } catch (SQLException e) {
            System.err.println("Error saat menghapus entri pembina untuk guru dengan NIP " + nip + ": " + e.getMessage());
            e.printStackTrace();
            // Lanjutkan eksekusi meskipun ada error di sini, karena penghapusan guru mungkin tetap ingin dilakukan
        }

        // Hapus guru dari tabel guru
        String sqlGuru = "DELETE FROM guru WHERE nip = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmtGuru = conn.prepareStatement(sqlGuru)) {
            stmtGuru.setString(1, nip);
            int rowsAffectedGuru = stmtGuru.executeUpdate();

            if (rowsAffectedGuru > 0) {
                // Jika guru berhasil dihapus, hapus juga user terkait jika ada
                if (guruToDelete.getIdUser() != null && guruToDelete.getIdUser() != 0) {
                    userDao.deleteUser(guruToDelete.getIdUser());
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus guru: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
