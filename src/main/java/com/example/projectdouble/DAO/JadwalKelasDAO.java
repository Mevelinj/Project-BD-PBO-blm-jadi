package com.example.projectdouble.DAO;

import com.example.projectdouble.model.JadwalKelas;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JadwalKelasDAO {

    /**
     * Menambahkan jadwal kelas baru ke database.
     * @param jadwalKelas Objek JadwalKelas yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addJadwalKelas(JadwalKelas jadwalKelas) {
        // UPDATED: Menambahkan kolom semester ke query INSERT
        String sql = "INSERT INTO jadwal_kelas (hari, jam_mulai, jam_selesai, id_kelas, id_mapel, nip_guru) VALUES (?, ?, ?, ?, ?, ?)";//, semester
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jadwalKelas.getHari());
            stmt.setTime(2, Time.valueOf(jadwalKelas.getJamMulai()));
            stmt.setTime(3, Time.valueOf(jadwalKelas.getJamSelesai()));
            stmt.setInt(4, jadwalKelas.getIdKelas());
            stmt.setInt(5, jadwalKelas.getIdMapel());
            stmt.setString(6, jadwalKelas.getNipGuru());
            //stmt.setString(7, jadwalKelas.getSemester()); // NEW: Set parameter semester
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan jadwal kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua jadwal kelas beserta detail lengkap (mapel, kelas, guru, tahun ajaran, semester).
     * @return List objek JadwalKelas dengan detail lengkap.
     */
    public List<JadwalKelas> getAllJadwalKelasDetails() {
        List<JadwalKelas> jadwalList = new ArrayList<>();
        // UPDATED: Memilih kolom semester dari jadwal_kelas (jk.semester) (DIHAPUS)
        String sql = "SELECT jk.id_jadwal_kelas, jk.hari, jk.jam_mulai, jk.jam_selesai, " +
                "mp.nama_mapel, " +
                "k.nama_kelas, ta.tahun_mulai, ta.tahun_selesai, " + // jk.semester DIHAPUS
                "g.nama " +
                "FROM jadwal_kelas jk " +
                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
                "JOIN guru g ON jk.nip_guru = g.nip " +
                "LEFT JOIN tahun_ajaran ta ON k.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "ORDER BY jk.hari, jk.jam_mulai";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idJadwalKelas = rs.getInt("id_jadwal_kelas");
                String hari = rs.getString("hari");
                LocalTime jamMulai = rs.getTime("jam_mulai").toLocalTime();
                LocalTime jamSelesai = rs.getTime("jam_selesai").toLocalTime();
                String namaMapel = rs.getString("nama_mapel");
                String namaKelas = rs.getString("nama_kelas");
                int tahunMulai = rs.getInt("tahun_mulai");
                int tahunSelesai = rs.getInt("tahun_selesai");
                String tahunAjaranLengkap = tahunMulai + "/" + tahunSelesai;
                // String semester = rs.getString("semester"); // DIHAPUS: Mengambil semester dari ResultSet
                String namaGuru = rs.getString("nama");

                // Konstruktor JadwalKelas harus disesuaikan agar tidak lagi menerima 'semester'
                jadwalList.add(new JadwalKelas(idJadwalKelas, hari, jamMulai, jamSelesai,
                        namaMapel, namaKelas, tahunAjaranLengkap, namaGuru)); // DIHAPUS: , semester
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua detail jadwal kelas: " + e.getMessage());
            e.printStackTrace();
        }
        return jadwalList;
    }

    /**
     * Menghapus jadwal kelas dari database berdasarkan ID.
     * @param idJadwalKelas ID jadwal kelas yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteJadwalKelas(int idJadwalKelas) {
        String sql = "DELETE FROM jadwal_kelas WHERE id_jadwal_kelas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idJadwalKelas);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus jadwal kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Anda juga mungkin memerlukan metode untuk mengupdate dan menghapus jadwal jika diperlukan
    // ...
}

//public class JadwalKelasDAO {
//
//    /**
//     * Menambahkan jadwal kelas baru ke database.
//     * @param jadwalKelas Objek JadwalKelas yang akan ditambahkan.
//     * @return true jika berhasil, false jika gagal.
//     */
//    public boolean addJadwalKelas(JadwalKelas jadwalKelas) {
//        String sql = "INSERT INTO jadwal_kelas (hari, jam_mulai, jam_selesai, id_kelas, id_mapel, nip_guru) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, jadwalKelas.getHari());
//            stmt.setTime(2, Time.valueOf(jadwalKelas.getJamMulai()));
//            stmt.setTime(3, Time.valueOf(jadwalKelas.getJamSelesai()));
//            stmt.setInt(4, jadwalKelas.getIdKelas());
//            stmt.setInt(5, jadwalKelas.getIdMapel());
//            stmt.setString(6, jadwalKelas.getNipGuru());
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.err.println("Error saat menambahkan jadwal kelas: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * Mengambil semua jadwal kelas beserta detail lengkap (mapel, kelas, guru, tahun ajaran, semester).
//     * @return List objek JadwalKelas dengan detail lengkap.
//     */
//    public List<JadwalKelas> getAllJadwalKelasDetails() {
//        List<JadwalKelas> jadwalList = new ArrayList<>();
//        String sql = "SELECT jk.id_jadwal_kelas, jk.hari, jk.jam_mulai, jk.jam_selesai, " +
//                "mp.nama_mapel, " +
//                "k.nama_kelas, ta.tahun_mulai, ta.tahun_selesai, k.semester, " + // Assuming k.semester now exists or is derived
//                "g.nama " +
//                "FROM jadwal_kelas jk " +
//                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
//                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
//                "JOIN guru g ON jk.nip_guru = g.nip " +
//                "LEFT JOIN tahun_ajaran ta ON k.id_tahun_ajaran = ta.id_tahun_ajrn " + // Join dengan tahun_ajaran dari kelas
//                "ORDER BY jk.hari, jk.jam_mulai";
//
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                int idJadwalKelas = rs.getInt("id_jadwal_kelas");
//                String hari = rs.getString("hari");
//                LocalTime jamMulai = rs.getTime("jam_mulai").toLocalTime();
//                LocalTime jamSelesai = rs.getTime("jam_selesai").toLocalTime();
//                String namaMapel = rs.getString("nama_mapel");
//                String namaKelas = rs.getString("nama_kelas");
//                int tahunMulai = rs.getInt("tahun_mulai");
//                int tahunSelesai = rs.getInt("tahun_selesai");
//                String tahunAjaranLengkap = tahunMulai + "/" + tahunSelesai;
//                String semester = rs.getString("semester"); // Ambil dari kolom kelas.semester atau sesuaikan
//                String namaGuru = rs.getString("nama");
//
//                jadwalList.add(new JadwalKelas(idJadwalKelas, hari, jamMulai, jamSelesai,
//                        namaMapel, namaKelas, tahunAjaranLengkap, semester, namaGuru));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mengambil semua detail jadwal kelas: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return jadwalList;
//    }
//}

//public class JadwalKelasDAO {
//
//    /**
//     * Menambahkan jadwal kelas baru ke database.
//     * @param jadwalKelas Objek JadwalKelas yang akan ditambahkan.
//     * @return true jika berhasil, false jika gagal.
//     */
//    public boolean addJadwalKelas(JadwalKelas jadwalKelas) {
//        // Menggunakan nama tabel lowercase 'jadwal_kelas'
//        String sql = "INSERT INTO jadwal_kelas (id_kelas, id_mapel, nip, hari, jam_mulai, jam_selesai) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_jadwal_kelas";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setInt(1, jadwalKelas.getIdKelas());
//            stmt.setInt(2, jadwalKelas.getIdMapel());
//            stmt.setString(3, jadwalKelas.getNipGuru());
//            stmt.setString(4, jadwalKelas.getHari());
//            stmt.setTime(5, Time.valueOf(jadwalKelas.getJamMulai())); // Konversi LocalTime ke java.sql.Time
//            stmt.setTime(6, Time.valueOf(jadwalKelas.getJamSelesai())); // Konversi LocalTime ke java.sql.Time
//            int rowsAffected = stmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        jadwalKelas.setIdJadwalKelas(generatedKeys.getInt(1));
//                    }
//                }
//                return true;
//            }
//            return false;
//        } catch (SQLException e) {
//            System.err.println("Error saat menambahkan jadwal kelas: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * Mengambil semua jadwal kelas dari database.
//     * @return List objek JadwalKelas.
//     */
//    public List<JadwalKelas> getAllJadwalKelas() {
//        List<JadwalKelas> jadwalKelasList = new ArrayList<>();
//        // Menggunakan nama tabel lowercase 'jadwal_kelas', 'kelas', 'mata_pelajaran', 'guru'
//        String sql = "SELECT jk.id_jadwal_kelas, jk.id_kelas, k.nama_kelas, jk.id_mapel, mp.nama_mapel, jk.nip, g.nama AS nama_guru, " +
//                "jk.hari, jk.jam_mulai, jk.jam_selesai " +
//                "FROM jadwal_kelas jk " +
//                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
//                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
//                "JOIN guru g ON jk.nip = g.nip ORDER BY jk.hari, jk.jam_mulai";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                jadwalKelasList.add(new JadwalKelas(
//                        rs.getInt("id_jadwal_kelas"),
//                        rs.getInt("id_kelas"),
//                        rs.getString("nama_kelas"),
//                        rs.getInt("id_mapel"),
//                        rs.getString("nama_mapel"),
//                        rs.getString("nip"),
//                        rs.getString("nama_guru"),
//                        rs.getString("hari"),
//                        rs.getTime("jam_mulai").toLocalTime(), // Konversi java.sql.Time ke LocalTime
//                        rs.getTime("jam_selesai").toLocalTime() // Konversi java.sql.Time ke LocalTime
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mengambil semua jadwal kelas: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return jadwalKelasList;
//    }
//
//    /**
//     * Mengambil jadwal kelas berdasarkan ID.
//     * @param idJadwalKelas ID jadwal kelas.
//     * @return Objek JadwalKelas jika ditemukan, null jika tidak.
//     */
//    public JadwalKelas getJadwalKelasById(int idJadwalKelas) {
//        // Menggunakan nama tabel lowercase 'jadwal_kelas', 'kelas', 'mata_pelajaran', 'guru'
//        String sql = "SELECT jk.id_jadwal_kelas, jk.id_kelas, k.nama_kelas, jk.id_mapel, mp.nama_mapel, jk.nip, g.nama AS nama_guru, " +
//                "jk.hari, jk.jam_mulai, jk.jam_selesai " +
//                "FROM jadwal_kelas jk " +
//                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
//                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
//                "JOIN guru g ON jk.nip = g.nip " +
//                "WHERE jk.id_jadwal_kelas = ?";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, idJadwalKelas);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return new JadwalKelas(
//                        rs.getInt("id_jadwal_kelas"),
//                        rs.getInt("id_kelas"),
//                        rs.getString("nama_kelas"),
//                        rs.getInt("id_mapel"),
//                        rs.getString("nama_mapel"),
//                        rs.getString("nip"),
//                        rs.getString("nama_guru"),
//                        rs.getString("hari"),
//                        rs.getTime("jam_mulai").toLocalTime(),
//                        rs.getTime("jam_selesai").toLocalTime()
//                );
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mengambil jadwal kelas berdasarkan ID: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * Memperbarui data jadwal kelas di database.
//     * @param jadwalKelas Objek JadwalKelas dengan data terbaru.
//     * @return true jika berhasil, false jika gagal.
//     */
//    public boolean updateJadwalKelas(JadwalKelas jadwalKelas) {
//        // Menggunakan nama tabel lowercase 'jadwal_kelas'
//        String sql = "UPDATE jadwal_kelas SET id_kelas = ?, id_mapel = ?, nip = ?, hari = ?, jam_mulai = ?, jam_selesai = ? WHERE id_jadwal_kelas = ?";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, jadwalKelas.getIdKelas());
//            stmt.setInt(2, jadwalKelas.getIdMapel());
//            stmt.setString(3, jadwalKelas.getNipGuru());
//            stmt.setString(4, jadwalKelas.getHari());
//            stmt.setTime(5, Time.valueOf(jadwalKelas.getJamMulai()));
//            stmt.setTime(6, Time.valueOf(jadwalKelas.getJamSelesai()));
//            stmt.setInt(7, jadwalKelas.getIdJadwalKelas());
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.err.println("Error saat memperbarui jadwal kelas: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * Menghapus jadwal kelas dari database berdasarkan ID.
//     * @param idJadwalKelas ID jadwal kelas yang akan dihapus.
//     * @return true jika berhasil, false jika gagal.
//     */
//    public boolean deleteJadwalKelas(int idJadwalKelas) {
//        // Menggunakan nama tabel lowercase 'jadwal_kelas'
//        String sql = "DELETE FROM jadwal_kelas WHERE id_jadwal_kelas = ?";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, idJadwalKelas);
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.err.println("Error saat menghapus jadwal kelas: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
