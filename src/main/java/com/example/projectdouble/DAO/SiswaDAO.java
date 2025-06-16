package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Siswa;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SiswaDAO {

    private UserDAO userDao = new UserDAO(); // Digunakan untuk operasi terkait user

    /**
     * Menambahkan data siswa baru ke database.
     * @param siswa Objek Siswa yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addSiswa(Siswa siswa) {
        // Menggunakan nama tabel lowercase 'siswa'
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
            System.err.println("Error saat menambahkan data siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data siswa di database.
     * @param siswa Objek Siswa dengan data terbaru.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateSiswa(Siswa siswa) {
        // Menggunakan nama tabel lowercase 'siswa'
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
            System.err.println("Error saat memperbarui data siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengaitkan user ID ke siswa.
     * Digunakan setelah user baru dibuat.
     * @param siswa Objek Siswa yang akan diperbarui idUser-nya.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateSiswaUser(Siswa siswa) {
        // Menggunakan nama tabel lowercase 'siswa'
        String sql = "UPDATE siswa SET id_user = ? WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Gunakan setInt jika idUser tidak null, atau setNull jika null
            if (siswa.getIdUser() != null) {
                stmt.setInt(1, siswa.getIdUser());
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
            stmt.setString(2, siswa.getNis());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat mengaitkan user ke siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus semua informasi kelas dari siswa (set id_kelas, id_tahun_ajaran, semester ke NULL).
     * Ini mengasumsikan kolom-kolom ini ada di tabel siswa.
     * JIKA DDL Anda tidak memiliki kolom id_kelas, id_tahun_ajaran, semester di tabel siswa,
     * MAKA FUNGSI INI PERLU DIMODIFIKASI UNTUK MENGHAPUS DARI TABEL `kelas_siswa` ATAU TABEL PENGHUBUNG LAINNYA.
     * Contoh implementasi untuk tabel `kelas_siswa` akan diberikan di bawah (jika Anda memiliki tabel ini).
     * @param nis NIS siswa yang akan direset informasi kelasnya.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean removeClassInfoFromStudent(String nis) {
        // ASUMSI: Ada tabel 'kelas_siswa' yang menghubungkan siswa ke kelas.
        // Jika Anda ingin hanya menghapus siswa dari kelas (bukan menghapus siswa dari database),
        // maka Anda harus menghapus entri dari tabel 'kelas_siswa'.
        // DDL Anda saat ini tidak memiliki tabel 'kelas_siswa',
        // jadi saya akan memberikan SQL hipotetis untuk tabel 'kelas_siswa'.
        // Jika Anda telah mengubah DDL 'siswa' untuk menyertakan id_kelas, id_tahun_ajaran, semester:
        // String sql = "UPDATE siswa SET id_kelas = NULL, id_tahun_ajaran = NULL, semester = NULL WHERE nis = ?";
        // Jika Anda memiliki tabel 'kelas_siswa' sebagai tabel penghubung:
//        String sql = "DELETE FROM kelas_siswa WHERE nis = ?"; // Hapus semua entri kelas untuk siswa ini
//
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, nis);
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.err.println("Error saat menghapus informasi kelas dari siswa (di tabel kelas_siswa): " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
        String sql = "UPDATE siswa SET id_kelas = NULL, id_tahun_ajaran = NULL, semester = NULL WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus informasi kelas dari siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Mengambil data siswa berdasarkan NIS.
     * @param nis NIS siswa yang dicari.
     * @return Objek Siswa jika ditemukan, null jika tidak.
     */
    public Siswa getSiswaByNis(String nis) {
        // Menggunakan nama tabel lowercase 'siswa' dan 'users'
        // Memasukkan join ke tabel kelas_siswa, kelas, dan tahun_ajaran untuk mengambil info kelas
        // Jika tabel kelas_siswa tidak ada di DDL Anda, bagian JOIN ini akan menyebabkan error
//        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
//                "u.id_user, u.username AS username_user, u.password AS password_user, " +
//                "ks.id_kelas, k.nama_kelas, ks.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, ks.semester " +
//                "FROM siswa s " +
//                "LEFT JOIN users u ON s.id_user = u.id_user " + // Bergabung dengan tabel users (bukan "USER")
//                "LEFT JOIN kelas_siswa ks ON s.nis = ks.nis " + // ASUMSI: ada tabel kelas_siswa
//                "LEFT JOIN kelas k ON ks.id_kelas = k.id_kelas " +
//                "LEFT JOIN tahun_ajaran ta ON ks.id_tahun_ajaran = ta.id_tahun_ajaran " +
//                "WHERE s.nis = ?";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, nis);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                // Perhatikan konstruktor Siswa yang digunakan harus sesuai dengan model Anda
//                return new Siswa(
//                        rs.getString("nis"),
//                        rs.getString("nama"),
//                        rs.getString("jenis_kelamin"),
//                        rs.getString("tempat_lahir"),
//                        rs.getDate("tanggal_lahir").toLocalDate(),
//                        rs.getString("alamat"),
//                        (Integer) rs.getObject("id_kelas"), // Bisa null
//                        rs.getString("nama_kelas"), // Bisa null
//                        (Integer) rs.getObject("id_tahun_ajaran"), // Bisa null
//                        rs.getString("tahun_ajaran_lengkap"), // Bisa null
//                        rs.getString("semester"), // Bisa null
//                        (Integer) rs.getObject("id_user"), // idUser bisa null
//                        rs.getString("username_user"), // username_user bisa null
//                        rs.getString("password_user") // password_user bisa null
//                );
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mengambil siswa berdasarkan NIS: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;

        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "u.id_user, u.username AS username_user, u.password AS password_user, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester " +
                "FROM siswa s " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "WHERE s.nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        rs.getDate("tanggal_lahir").toLocalDate(),
                        rs.getString("alamat"),
                        (Integer) rs.getObject("id_kelas"),
                        rs.getString("nama_kelas"),
                        (Integer) rs.getObject("id_tahun_ajaran"),
                        rs.getString("tahun_ajaran_lengkap"),
                        rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil siswa berdasarkan NIS: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil semua data siswa dari database.
     * @return List objek Siswa.
     */
    public List<Siswa> getAllSiswa() {
//        List<Siswa> siswaList = new ArrayList<>();
//        // Menggunakan nama tabel lowercase 'siswa' dan 'users'
//        // Memasukkan join ke tabel kelas_siswa, kelas, dan tahun_ajaran untuk mengambil info kelas
//        // Jika tabel kelas_siswa tidak ada di DDL Anda, bagian JOIN ini akan menyebabkan error
//        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
//                "u.id_user, u.username AS username_user, u.password AS password_user, " +
//                "ks.id_kelas, k.nama_kelas, ks.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, ks.semester " +
//                "FROM siswa s " +
//                "LEFT JOIN users u ON s.id_user = u.id_user " + // Bergabung dengan tabel users (bukan "USER")
//                "LEFT JOIN kelas_siswa ks ON s.nis = ks.nis " + // ASUMSI: ada tabel kelas_siswa
//                "LEFT JOIN kelas k ON ks.id_kelas = k.id_kelas " +
//                "LEFT JOIN tahun_ajaran ta ON ks.id_tahun_ajaran = ta.id_tahun_ajaran";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                // Perhatikan konstruktor Siswa yang digunakan harus sesuai dengan model Anda
//                siswaList.add(new Siswa(
//                        rs.getString("nis"),
//                        rs.getString("nama"),
//                        rs.getString("jenis_kelamin"),
//                        rs.getString("tempat_lahir"),
//                        rs.getDate("tanggal_lahir").toLocalDate(),
//                        rs.getString("alamat"),
//                        (Integer) rs.getObject("id_kelas"), // Bisa null
//                        rs.getString("nama_kelas"), // Bisa null
//                        (Integer) rs.getObject("id_tahun_ajaran"), // Bisa null
//                        rs.getString("tahun_ajaran_lengkap"), // Bisa null
//                        rs.getString("semester"), // Bisa null
//                        (Integer) rs.getObject("id_user"), // idUser bisa null
//                        rs.getString("username_user"), // username_user bisa null
//                        rs.getString("password_user") // password_user bisa null
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mengambil semua data siswa: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return siswaList;

        List<Siswa> siswaList = new ArrayList<>();
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "u.id_user, u.username AS username_user, u.password AS password_user, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester " +
                "FROM siswa s " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                siswaList.add(new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        rs.getDate("tanggal_lahir").toLocalDate(),
                        rs.getString("alamat"),
                        (Integer) rs.getObject("id_kelas"),
                        rs.getString("nama_kelas"),
                        (Integer) rs.getObject("id_tahun_ajaran"),
                        rs.getString("tahun_ajaran_lengkap"),
                        rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua data siswa: " + e.getMessage());
            e.printStackTrace();
        }
        return siswaList;
    }

    /**
     * Mencari siswa berdasarkan keyword di nama atau NIS.
     * @param keyword Kata kunci pencarian.
     * @return List objek Siswa yang cocok.
     */
    public List<Siswa> searchSiswa(String keyword) {
//        List<Siswa> siswaList = new ArrayList<>();
//        // Menggunakan nama tabel lowercase 'siswa' dan 'users'
//        // Memasukkan join ke tabel kelas_siswa, kelas, dan tahun_ajaran untuk mengambil info kelas
//        // Jika tabel kelas_siswa tidak ada di DDL Anda, bagian JOIN ini akan menyebabkan error
//        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
//                "u.id_user, u.username AS username_user, u.password AS password_user, " +
//                "ks.id_kelas, k.nama_kelas, ks.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, ks.semester " +
//                "FROM siswa s " +
//                "LEFT JOIN users u ON s.id_user = u.id_user " + // Bergabung dengan tabel users (bukan "USER")
//                "LEFT JOIN kelas_siswa ks ON s.nis = ks.nis " + // ASUMSI: ada tabel kelas_siswa
//                "LEFT JOIN kelas k ON ks.id_kelas = k.id_kelas " +
//                "LEFT JOIN tahun_ajaran ta ON ks.id_tahun_ajaran = ta.id_tahun_ajaran " +
//                "WHERE s.nama ILIKE ? OR s.nis ILIKE ?";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, "%" + keyword + "%");
//            stmt.setString(2, "%" + keyword + "%");
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                // Perhatikan konstruktor Siswa yang digunakan harus sesuai dengan model Anda
//                siswaList.add(new Siswa(
//                        rs.getString("nis"),
//                        rs.getString("nama"),
//                        rs.getString("jenis_kelamin"),
//                        rs.getString("tempat_lahir"),
//                        rs.getDate("tanggal_lahir").toLocalDate(),
//                        rs.getString("alamat"),
//                        (Integer) rs.getObject("id_kelas"), // Bisa null
//                        rs.getString("nama_kelas"), // Bisa null
//                        (Integer) rs.getObject("id_tahun_ajaran"), // Bisa null
//                        rs.getString("tahun_ajaran_lengkap"), // Bisa null
//                        rs.getString("semester"), // Bisa null
//                        (Integer) rs.getObject("id_user"), // idUser bisa null
//                        rs.getString("username_user"), // username_user bisa null
//                        rs.getString("password_user") // password_user bisa null
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mencari siswa: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return siswaList;

        List<Siswa> siswaList = new ArrayList<>();
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "u.id_user, u.username AS username_user, u.password AS password_user, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester " +
                "FROM siswa s " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "WHERE s.nama ILIKE ? OR s.nis ILIKE ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                siswaList.add(new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        rs.getDate("tanggal_lahir").toLocalDate(),
                        rs.getString("alamat"),
                        (Integer) rs.getObject("id_kelas"),
                        rs.getString("nama_kelas"),
                        (Integer) rs.getObject("id_tahun_ajaran"),
                        rs.getString("tahun_ajaran_lengkap"),
                        rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari siswa: " + e.getMessage());
            e.printStackTrace();
        }
        return siswaList;
    }

    /**
     * Menghapus data siswa dari database.
     * Ini juga akan menghapus user terkait jika ada.
     * @param nis NIS siswa yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteSiswa(String nis) {
//        Siswa siswaToDelete = getSiswaByNis(nis); // Ambil data siswa lengkap
//        if (siswaToDelete == null) {
//            System.err.println("Siswa dengan NIS " + nis + " tidak ditemukan untuk dihapus.");
//            return false;
//        }
//
//        // Hapus entri dari kelas_siswa terlebih dahulu (jika ada)
//        removeClassInfoFromStudent(nis);
//
//        // Hapus siswa dari tabel siswa
//        String sqlSiswa = "DELETE FROM siswa WHERE nis = ?";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmtSiswa = conn.prepareStatement(sqlSiswa)) {
//            stmtSiswa.setString(1, nis);
//            int rowsAffectedSiswa = stmtSiswa.executeUpdate();
//
//            if (rowsAffectedSiswa > 0) {
//                // Jika siswa berhasil dihapus, hapus juga user terkait jika ada
//                if (siswaToDelete.getIdUser() != null && siswaToDelete.getIdUser() != 0) {
//                    userDao.deleteUser(siswaToDelete.getIdUser());
//                }
//                return true;
//            }
//            return false;
//        } catch (SQLException e) {
//            System.err.println("Error saat menghapus siswa: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }

        Siswa siswaToDelete = getSiswaByNis(nis);
        if (siswaToDelete == null) {
            System.err.println("Siswa dengan NIS " + nis + " tidak ditemukan untuk dihapus.");
            return false;
        }

        // Hapus entri dari peserta_ekskul terkait
        String sqlDeletePesertaEkskul = "DELETE FROM peserta_ekskul WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmtPesertaEkskul = conn.prepareStatement(sqlDeletePesertaEkskul)) {
            stmtPesertaEkskul.setString(1, nis);
            stmtPesertaEkskul.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saat menghapus entri peserta_ekskul untuk siswa dengan NIS " + nis + ": " + e.getMessage());
            e.printStackTrace();
        }

        // Hapus siswa dari tabel siswa
        String sqlSiswa = "DELETE FROM siswa WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmtSiswa = conn.prepareStatement(sqlSiswa)) {
            stmtSiswa.setString(1, nis);
            int rowsAffectedSiswa = stmtSiswa.executeUpdate();

            if (rowsAffectedSiswa > 0) {
                if (siswaToDelete.getIdUser() != null && siswaToDelete.getIdUser() != 0) {
                    userDao.deleteUser(siswaToDelete.getIdUser());
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menetapkan siswa ke kelas. Ini akan menambahkan entri ke tabel `kelas_siswa`.
     * @param nis NIS siswa.
     * @param idKelas ID kelas.
     * @param idTahunAjaran ID tahun ajaran.
     * @param semester Semester.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean assignStudentToClass(String nis, int idKelas, int idTahunAjaran, String semester) {
        // ASUMSI: ada tabel 'kelas_siswa' untuk menyimpan assignment siswa ke kelas
        // Jika DDL Anda tidak memiliki tabel 'kelas_siswa', ini akan gagal.
//        String sql = "INSERT INTO kelas_siswa (nis, id_kelas, id_tahun_ajaran, semester) VALUES (?, ?, ?, ?) ON CONFLICT (nis) DO UPDATE SET id_kelas = EXCLUDED.id_kelas, id_tahun_ajaran = EXCLUDED.id_tahun_ajaran, semester = EXCLUDED.semester";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, nis);
//            stmt.setInt(2, idKelas);
//            stmt.setInt(3, idTahunAjaran);
//            stmt.setString(4, semester);
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.err.println("Error assigning student to class: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }

        // Langsung update kolom di tabel siswa
        String sql = "UPDATE siswa SET id_kelas = ?, id_tahun_ajaran = ?, semester = ? WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKelas);
            stmt.setInt(2, idTahunAjaran);
            stmt.setString(3, semester);
            stmt.setString(4, nis);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error assigning student to class: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil daftar siswa dalam kelas tertentu untuk tahun ajaran dan semester tertentu.
     * @param idKelas ID kelas.
     * @param idTahunAjaran ID tahun ajaran.
     * @param semester Semester.
     * @return List objek Siswa dalam kelas tersebut.
     */
    public List<Siswa> getStudentsInClass(int idKelas, int idTahunAjaran) {
//        List<Siswa> siswaList = new ArrayList<>();
//        // ASUMSI: ada tabel 'kelas_siswa'
//        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
//                "u.id_user, u.username AS username_user, u.password AS password_user, " +
//                "ks.id_kelas, k.nama_kelas, ks.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, ks.semester " +
//                "FROM siswa s " +
//                "JOIN kelas_siswa ks ON s.nis = ks.nis " + // Hanya ambil siswa yang ada di kelas_siswa
//                "LEFT JOIN users u ON s.id_user = u.id_user " +
//                "LEFT JOIN kelas k ON ks.id_kelas = k.id_kelas " +
//                "LEFT JOIN tahun_ajaran ta ON ks.id_tahun_ajaran = ta.id_tahun_ajaran " +
//                "WHERE ks.id_kelas = ? AND ks.id_tahun_ajaran = ? AND ks.semester = ?";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, idKelas);
//            stmt.setInt(2, idTahunAjaran);
//            stmt.setString(3, semester);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                siswaList.add(new Siswa(
//                        rs.getString("nis"),
//                        rs.getString("nama"),
//                        rs.getString("jenis_kelamin"),
//                        rs.getString("tempat_lahir"),
//                        rs.getDate("tanggal_lahir").toLocalDate(),
//                        rs.getString("alamat"),
//                        (Integer) rs.getObject("id_kelas"),
//                        rs.getString("nama_kelas"),
//                        (Integer) rs.getObject("id_tahun_ajaran"),
//                        rs.getString("tahun_ajaran_lengkap"),
//                        rs.getString("semester"),
//                        (Integer) rs.getObject("id_user"),
//                        rs.getString("username_user"),
//                        rs.getString("password_user")
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mengambil siswa dalam kelas: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return siswaList;

        List<Siswa> siswaList = new ArrayList<>();
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "u.id_user, u.username AS username_user, u.password AS password_user, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester " +
                "FROM siswa s " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "WHERE s.id_kelas = ? AND s.id_tahun_ajaran = ? AND s.semester = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKelas);
            stmt.setInt(2, idTahunAjaran);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                siswaList.add(new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        rs.getDate("tanggal_lahir").toLocalDate(),
                        rs.getString("alamat"),
                        (Integer) rs.getObject("id_kelas"),
                        rs.getString("nama_kelas"),
                        (Integer) rs.getObject("id_tahun_ajaran"),
                        rs.getString("tahun_ajaran_lengkap"),
                        rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil siswa dalam kelas: " + e.getMessage());
            e.printStackTrace();
        }
        return siswaList;
    }


    //private UserDAO userDao = new UserDAO(); // Digunakan untuk operasi terkait user

    /**
     * Menambahkan data siswa baru ke database.
     * @param siswa Objek Siswa yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    /*
    public boolean addSiswa(Siswa siswa) {
        String sql = "INSERT INTO siswa (nis, nama, jenis_kelamin, tempat_lahir, tanggal_lahir, alamat, id_kelas, id_tahun_ajaran, semester, id_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, siswa.getNis());
            stmt.setString(2, siswa.getNama());
            stmt.setString(3, siswa.getJenisKelamin());
            stmt.setString(4, siswa.getTempatLahir());
            stmt.setDate(5, Date.valueOf(siswa.getTanggalLahir()));
            stmt.setString(6, siswa.getAlamat());
            // Gunakan setInt atau setNull untuk Integer yang bisa null
            if (siswa.getIdKelas() != null) {
                stmt.setInt(7, siswa.getIdKelas());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }
            if (siswa.getIdTahunAjaran() != null) {
                stmt.setInt(8, siswa.getIdTahunAjaran());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }
            stmt.setString(9, siswa.getSemester()); // String bisa null
            if (siswa.getIdUser() != null) {
                stmt.setInt(10, siswa.getIdUser());
            } else {
                stmt.setNull(10, java.sql.Types.INTEGER);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan data siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

     */

    /**
     * Memperbarui data siswa di database.
     * @param siswa Objek Siswa dengan data terbaru.
     * @return true jika berhasil, false jika gagal.
     */
    /*
    public boolean updateSiswa(Siswa siswa) {
        String sql = "UPDATE siswa SET nama = ?, jenis_kelamin = ?, tempat_lahir = ?, tanggal_lahir = ?, alamat = ?, id_kelas = ?, id_tahun_ajaran = ?, semester = ?, id_user = ? WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, siswa.getNama());
            stmt.setString(2, siswa.getJenisKelamin());
            stmt.setString(3, siswa.getTempatLahir());
            stmt.setDate(4, Date.valueOf(siswa.getTanggalLahir()));
            stmt.setString(5, siswa.getAlamat());
            if (siswa.getIdKelas() != null) {
                stmt.setInt(6, siswa.getIdKelas());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }
            if (siswa.getIdTahunAjaran() != null) {
                stmt.setInt(7, siswa.getIdTahunAjaran());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }
            stmt.setString(8, siswa.getSemester());
            if (siswa.getIdUser() != null) {
                stmt.setInt(9, siswa.getIdUser());
            } else {
                stmt.setNull(9, java.sql.Types.INTEGER);
            }
            stmt.setString(10, siswa.getNis());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui data siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

     */

    /**
     * Mengambil data siswa berdasarkan NIS.
     * Menggabungkan data dari tabel 'siswa', 'users', 'kelas', dan 'tahun_ajaran'.
     * @param nis NIS siswa yang dicari.
     * @return Objek Siswa jika ditemukan, null jika tidak.
     */
    /*
    public Siswa getSiswaByNis(String nis) {
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester, " +
                "u.id_user, u.username AS username_user, u.password AS password_user " +
                "FROM siswa s " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "WHERE s.nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        rs.getDate("tanggal_lahir").toLocalDate(),
                        rs.getString("alamat"),
                        (Integer) rs.getObject("id_kelas"),
                        rs.getString("nama_kelas"),
                        (Integer) rs.getObject("id_tahun_ajaran"),
                        rs.getString("tahun_ajaran_lengkap"),
                        rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil siswa berdasarkan NIS: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

     */

    /**
     * Mengambil semua data siswa dari database.
     * Menggabungkan data dari tabel 'siswa', 'users', 'kelas', dan 'tahun_ajaran'.
     * @return List objek Siswa.
     */
    /*
    public List<Siswa> getAllSiswa() {
        List<Siswa> siswaList = new ArrayList<>();
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester, " +
                "u.id_user, u.username AS username_user, u.password AS password_user " +
                "FROM siswa s " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "ORDER BY s.nama"; // Tambahkan order by untuk tampilan lebih baik
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                siswaList.add(new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        rs.getDate("tanggal_lahir").toLocalDate(),
                        rs.getString("alamat"),
                        (Integer) rs.getObject("id_kelas"),
                        rs.getString("nama_kelas"),
                        (Integer) rs.getObject("id_tahun_ajaran"),
                        rs.getString("tahun_ajaran_lengkap"),
                        rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua data siswa: " + e.getMessage());
            e.printStackTrace();
        }
        return siswaList;
    }

     */

    /**
     * Mencari siswa berdasarkan keyword di nama atau NIS.
     * @param keyword Kata kunci pencarian.
     * @return List objek Siswa yang cocok.
     */
    /*
    public List<Siswa> searchSiswa(String keyword) {
        List<Siswa> siswaList = new ArrayList<>();
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester, " +
                "u.id_user, u.username AS username_user, u.password AS password_user " +
                "FROM siswa s " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "WHERE s.nama ILIKE ? OR s.nis ILIKE ? " +
                "ORDER BY s.nama"; // Tambahkan order by
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                siswaList.add(new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        rs.getDate("tanggal_lahir").toLocalDate(),
                        rs.getString("alamat"),
                        (Integer) rs.getObject("id_kelas"),
                        rs.getString("nama_kelas"),
                        (Integer) rs.getObject("id_tahun_ajaran"),
                        rs.getString("tahun_ajaran_lengkap"),
                        rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari siswa: " + e.getMessage());
            e.printStackTrace();
        }
        return siswaList;
    }

     */

    /**
     * Menghapus data siswa dari database.
     * Ini juga akan menghapus user terkait jika ada, dan entri terkait di PESERTA_EKSKUL, PRESENSI, NILAI_TUGAS, NILAI_UJIAN, RAPOR.
     * @param nis NIS siswa yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    /*
    public boolean deleteSiswa(String nis) {
        Siswa siswaToDelete = getSiswaByNis(nis);
        if (siswaToDelete == null) {
            System.err.println("Siswa dengan NIS " + nis + " tidak ditemukan untuk dihapus.");
            return false;
        }

        // Hapus entri terkait dari tabel-tabel lain yang memiliki FK ke SISWA
        // Urutan penting karena CASCADE DELETE di DDL mungkin tidak selalu cukup untuk semua skenario
        // atau jika ada tabel lain yang memiliki FK ke tabel ini tanpa CASCADE.
        // DDL Anda sudah memiliki ON DELETE CASCADE, jadi seharusnya penghapusan SISWA akan otomatis menghapus
        // di PRESENSI, NILAI_TUGAS, NILAI_UJIAN, RAPOR, PESERTA_EKSKUL.
        // Jadi kita hanya perlu menghapus SISWA itu sendiri.

        String sqlSiswa = "DELETE FROM siswa WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmtSiswa = conn.prepareStatement(sqlSiswa)) {
            stmtSiswa.setString(1, nis);
            int rowsAffectedSiswa = stmtSiswa.executeUpdate();

            if (rowsAffectedSiswa > 0) {
                // Jika siswa berhasil dihapus, hapus juga user terkait jika ada
                if (siswaToDelete.getIdUser() != null && siswaToDelete.getIdUser() != 0) {
                    userDao.deleteUser(siswaToDelete.getIdUser());
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

     */

    /**
     * Menetapkan siswa ke kelas. Ini akan memperbarui kolom id_kelas, id_tahun_ajaran, dan semester di tabel SISWA.
     * @param nis NIS siswa.
     * @param idKelas ID kelas.
     * @param idTahunAjaran ID tahun ajaran.
     * @param semester Semester.
     * @return true jika berhasil, false jika gagal.
     */
    /*
    public boolean assignStudentToClass(String nis, int idKelas, int idTahunAjaran, String semester) {
        String sql = "UPDATE siswa SET id_kelas = ?, id_tahun_ajaran = ?, semester = ? WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKelas);
            stmt.setInt(2, idTahunAjaran);
            stmt.setString(3, semester);
            stmt.setString(4, nis);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error assigning student to class: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

     */

    /**
     * Menghapus semua informasi kelas dari siswa (set id_kelas, id_tahun_ajaran, semester ke NULL).
     * @param nis NIS siswa yang akan direset informasi kelasnya.
     * @return true jika berhasil, false jika gagal.
     */
    /*
    public boolean removeClassInfoFromStudent(String nis) {
        String sql = "UPDATE siswa SET id_kelas = NULL, id_tahun_ajaran = NULL, semester = NULL WHERE nis = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus informasi kelas dari siswa: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

     */


    /**
     * Mengambil daftar siswa dalam kelas tertentu untuk tahun ajaran dan semester tertentu.
     * Query langsung tabel SISWA karena info kelas ada di sana.
     * @param idKelas ID kelas.
     * @param idTahunAjaran ID tahun ajaran.
     * @param semester Semester.
     * @return List objek Siswa dalam kelas tersebut.
     */
    /*
    public List<Siswa> getStudentsInClass(int idKelas, int idTahunAjaran, String semester) {
        List<Siswa> siswaList = new ArrayList<>();
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester, " +
                "u.id_user, u.username AS username_user, u.password AS password_user " +
                "FROM siswa s " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "WHERE s.id_kelas = ? AND s.id_tahun_ajaran = ? AND s.semester = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKelas);
            stmt.setInt(2, idTahunAjaran);
            stmt.setString(3, semester);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                siswaList.add(new Siswa(
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("tempat_lahir"),
                        rs.getDate("tanggal_lahir").toLocalDate(),
                        rs.getString("alamat"),
                        (Integer) rs.getObject("id_kelas"),
                        rs.getString("nama_kelas"),
                        (Integer) rs.getObject("id_tahun_ajaran"),
                        rs.getString("tahun_ajaran_lengkap"),
                        rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil siswa dalam kelas: " + e.getMessage());
            e.printStackTrace();
        }
        return siswaList;
    }

     */
}

