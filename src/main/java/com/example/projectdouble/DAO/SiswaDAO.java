package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Siswa;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SiswaDAO {
    /**
     * Menambahkan data siswa baru ke database.
     * @param siswa Objek Siswa yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addSiswa(Siswa siswa) {
        String sql = "INSERT INTO siswa (nis, nama, jenis_kelamin, tempat_lahir, tanggal_lahir, alamat) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, siswa.getNis());
            stmt.setString(2, siswa.getNama());
            stmt.setString(3, siswa.getJenisKelamin());
            stmt.setString(4, siswa.getTempatLahir());
            stmt.setDate(5, Date.valueOf(siswa.getTanggalLahir())); // Konversi LocalDate ke java.sql.Date
            stmt.setString(6, siswa.getAlamat());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data siswa di database.
     * @param siswa Objek Siswa dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateSiswa(Siswa siswa) {
        String sql = "UPDATE siswa SET nama = ?, jenis_kelamin = ?, tempat_lahir = ?, tanggal_lahir = ?, alamat = ? WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, siswa.getNama());
            stmt.setString(2, siswa.getJenisKelamin());
            stmt.setString(3, siswa.getTempatLahir());
            stmt.setDate(4, Date.valueOf(siswa.getTanggalLahir()));
            stmt.setString(5, siswa.getAlamat());
            stmt.setString(6, siswa.getNis());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus data siswa dari database.
     * @param nis NIS siswa yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteSiswa(String nis) {
        String sql = "DELETE FROM siswa WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data siswa dari database.
     * @return List objek Siswa.
     */
    public List<Siswa> getAllSiswa() {
        List<Siswa> siswaList = new ArrayList<>();
        // Query untuk mengambil siswa, dan juga username jika id_user diisi
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, u.id_user, u.username " +
                "FROM siswa s LEFT JOIN users u ON s.nis = u.username AND u.role = (SELECT id_role FROM role WHERE nama_role = 'Siswa')";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nis = rs.getString("nis");
                String nama = rs.getString("nama");
                String jenisKelamin = rs.getString("jenis_kelamin");
                String tempatLahir = rs.getString("tempat_lahir");
                LocalDate tanggalLahir = rs.getDate("tanggal_lahir") != null ? rs.getDate("tanggal_lahir").toLocalDate() : null;
                String alamat = rs.getString("alamat");
                int idUser = rs.getInt("id_user"); // Akan 0 jika NULL
                String usernameUser = rs.getString("username"); // Akan null jika tidak ada user

                siswaList.add(new Siswa(nis, nama, jenisKelamin, tempatLahir, tanggalLahir, alamat, idUser, usernameUser));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua siswa: " + e.getMessage());
            e.printStackTrace();
        }
        return siswaList;
    }

    /**
     * Mengambil data siswa berdasarkan NIS.
     * @param nis NIS siswa yang dicari.
     * @return Objek Siswa jika ditemukan, null jika tidak.
     */
    public Siswa getSiswaByNis(String nis) {
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, u.id_user, u.username " +
                "FROM siswa s LEFT JOIN users u ON s.nis = u.username AND u.role = (SELECT id_role FROM role WHERE nama_role = 'Siswa') WHERE s.nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LocalDate tanggalLahir = rs.getDate("tanggal_lahir") != null ? rs.getDate("tanggal_lahir").toLocalDate() : null;
                int idUser = rs.getInt("id_user");
                String usernameUser = rs.getString("username");
                return new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        tanggalLahir,
                        rs.getString("alamat"),
                        idUser,
                        usernameUser
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil siswa berdasarkan NIS: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mencari siswa berdasarkan NIS atau Nama.
     * @param keyword Kata kunci pencarian.
     * @return List objek Siswa yang cocok.
     */
    public List<Siswa> searchSiswa(String keyword) {
        List<Siswa> siswaList = new ArrayList<>();
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, u.id_user, u.username " +
                "FROM siswa s LEFT JOIN users u ON s.nis = u.username AND u.role = (SELECT id_role FROM role WHERE nama_role = 'Siswa') " +
                "WHERE s.nis ILIKE ? OR s.nama ILIKE ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nis = rs.getString("nis");
                String nama = rs.getString("nama");
                String jenisKelamin = rs.getString("jenis_kelamin");
                String tempatLahir = rs.getString("tempat_lahir");
                LocalDate tanggalLahir = rs.getDate("tanggal_lahir") != null ? rs.getDate("tanggal_lahir").toLocalDate() : null;
                String alamat = rs.getString("alamat");
                int idUser = rs.getInt("id_user");
                String usernameUser = rs.getString("username");
                siswaList.add(new Siswa(nis, nama, jenisKelamin, tempatLahir, tanggalLahir, alamat, idUser, usernameUser));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari siswa: " + e.getMessage());
            e.printStackTrace();
        }
        return siswaList;
    }

    /**
     * Memperbarui id_user di tabel siswa setelah user dibuat.
     * @param siswa Objek Siswa dengan id_user yang sudah diatur.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateSiswaUser(Siswa siswa) {
        String sql = "UPDATE siswa SET id_user = ? WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, siswa.getIdUser());
            stmt.setString(2, siswa.getNis());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui id_user siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil siswa dalam kelas tertentu untuk tahun ajaran/semester tertentu.
     * Ini memerlukan tabel perantara atau logika yang lebih kompleks jika tidak ada tabel siswa_kelas.
     * Untuk saat ini, fungsi ini adalah placeholder.
     * @param idKelas ID Kelas.
     * @param idTahunAjaran ID Tahun Ajaran.
     * @param semester Semester.
     * @return Daftar siswa dalam kelas tersebut.
     */
    public List<Siswa> getStudentsInClass(int idKelas, int idTahunAjaran, String semester) {
        List<Siswa> studentsInClass = new ArrayList<>();
        // ASUMSI: Ada tabel `peserta_kelas` atau `siswa` memiliki FK ke `kelas` dan `tahun_ajaran`
        // Karena DDL Anda tidak memiliki tabel 'peserta_kelas' atau FK di 'siswa' ke 'kelas',
        // query ini bersifat konseptual. Anda perlu menyesuaikan DDL dan query ini.
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, u.id_user, u.username " +
                "FROM siswa s JOIN peserta_kelas pk ON s.nis = pk.nis " + // Asumsi tabel peserta_kelas
                "JOIN kelas k ON pk.id_kelas = k.id_kelas " +
                "JOIN tahun_ajaran ta ON pk.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "LEFT JOIN users u ON s.nis = u.username AND u.role = (SELECT id_role FROM role WHERE nama_role = 'Siswa') " +
                "WHERE pk.id_kelas = ? AND pk.id_tahun_ajaran = ? AND pk.semester = ?"; // Asumsi kolom semester di peserta_kelas

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKelas);
            stmt.setInt(2, idTahunAjaran);
            stmt.setString(3, semester);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nis = rs.getString("nis");
                String nama = rs.getString("nama");
                String jenisKelamin = rs.getString("jenis_kelamin");
                String tempatLahir = rs.getString("tempat_lahir");
                LocalDate tanggalLahir = rs.getDate("tanggal_lahir") != null ? rs.getDate("tanggal_lahir").toLocalDate() : null;
                String alamat = rs.getString("alamat");
                int idUser = rs.getInt("id_user");
                String usernameUser = rs.getString("username");
                studentsInClass.add(new Siswa(nis, nama, jenisKelamin, tempatLahir, tanggalLahir, alamat, idUser, usernameUser));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil siswa dalam kelas: " + e.getMessage());
            e.printStackTrace();
        }
        return studentsInClass;
    }
}
