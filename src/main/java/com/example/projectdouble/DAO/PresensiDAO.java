package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Presensi;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PresensiDAO {
    /**
     * Menambahkan data presensi baru ke database.
     * @param presensi Objek Presensi yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addPresensi(Presensi presensi) {
        String sql = "INSERT INTO presensi (tanggal, status, nis, id_jadwal_kelas) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(presensi.getTanggal()));
            stmt.setString(2, presensi.getStatus());
            stmt.setString(3, presensi.getNis());
            stmt.setInt(4, presensi.getIdJadwalKelas());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        presensi.setIdPresensi(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan presensi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data presensi di database.
     * @param presensi Objek Presensi dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updatePresensi(Presensi presensi) {
        String sql = "UPDATE presensi SET tanggal = ?, status = ?, nis = ?, id_jadwal_kelas = ? WHERE id_presensi = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(presensi.getTanggal()));
            stmt.setString(2, presensi.getStatus());
            stmt.setString(3, presensi.getNis());
            stmt.setInt(4, presensi.getIdJadwalKelas());
            stmt.setInt(5, presensi.getIdPresensi());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui presensi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus data presensi dari database.
     * @param idPresensi ID presensi yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deletePresensi(int idPresensi) {
        String sql = "DELETE FROM presensi WHERE id_presensi = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPresensi);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus presensi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data presensi dari database, termasuk informasi terkait siswa dan jadwal kelas.
     * @return List objek Presensi.
     */
    public List<Presensi> getAllPresensi() {
        List<Presensi> presensiList = new ArrayList<>();
        String sql = "SELECT p.id_presensi, p.tanggal, p.status, " +
                "p.nis, s.nama AS nama_siswa, " +
                "jk.id_jadwal_kelas, jk.hari, jk.jam_mulai, jk.jam_selesai, " +
                "k.nama_kelas, mp.nama_mapel, g.nama AS nama_guru " +
                "FROM presensi p " +
                "JOIN siswa s ON p.nis = s.nis " +
                "JOIN jadwal_kelas jk ON p.id_jadwal_kelas = jk.id_jadwal_kelas " +
                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
                "JOIN guru g ON jk.nip_guru = g.nip " +
                "ORDER BY p.tanggal DESC, s.nama";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                presensiList.add(new Presensi(
                        rs.getInt("id_presensi"),
                        rs.getDate("tanggal").toLocalDate(),
                        rs.getString("status"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_jadwal_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getString("nama_mapel"),
                        rs.getString("nama_guru"),
                        rs.getString("hari"),
                        rs.getTime("jam_mulai").toLocalTime(),
                        rs.getTime("jam_selesai").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua presensi: " + e.getMessage());
            e.printStackTrace();
        }
        return presensiList;
    }

    /**
     * Mengambil data presensi berdasarkan ID.
     * @param idPresensi ID presensi yang dicari.
     * @return Objek Presensi jika ditemukan, null jika tidak.
     */
    public Presensi getPresensiById(int idPresensi) {
        String sql = "SELECT p.id_presensi, p.tanggal, p.status, " +
                "p.nis, s.nama AS nama_siswa, " +
                "jk.id_jadwal_kelas, jk.hari, jk.jam_mulai, jk.jam_selesai, " +
                "k.nama_kelas, mp.nama_mapel, g.nama AS nama_guru " +
                "FROM presensi p " +
                "JOIN siswa s ON p.nis = s.nis " +
                "JOIN jadwal_kelas jk ON p.id_jadwal_kelas = jk.id_jadwal_kelas " +
                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
                "JOIN guru g ON jk.nip_guru = g.nip " +
                "WHERE p.id_presensi = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPresensi);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Presensi(
                        rs.getInt("id_presensi"),
                        rs.getDate("tanggal").toLocalDate(),
                        rs.getString("status"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_jadwal_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getString("nama_mapel"),
                        rs.getString("nama_guru"),
                        rs.getString("hari"),
                        rs.getTime("jam_mulai").toLocalTime(),
                        rs.getTime("jam_selesai").toLocalTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil presensi berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil daftar presensi untuk siswa tertentu.
     * @param nis NIS siswa.
     * @return List objek Presensi.
     */
    public List<Presensi> getPresensiByNis(String nis) {
        List<Presensi> presensiList = new ArrayList<>();
        String sql = "SELECT p.id_presensi, p.tanggal, p.status, " +
                "p.nis, s.nama AS nama_siswa, " +
                "jk.id_jadwal_kelas, jk.hari, jk.jam_mulai, jk.jam_selesai, " +
                "k.nama_kelas, mp.nama_mapel, g.nama AS nama_guru " +
                "FROM presensi p " +
                "JOIN siswa s ON p.nis = s.nis " +
                "JOIN jadwal_kelas jk ON p.id_jadwal_kelas = jk.id_jadwal_kelas " +
                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
                "JOIN guru g ON jk.nip_guru = g.nip " +
                "WHERE p.nis = ? " +
                "ORDER BY p.tanggal DESC, jk.hari, jk.jam_mulai";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                presensiList.add(new Presensi(
                        rs.getInt("id_presensi"),
                        rs.getDate("tanggal").toLocalDate(),
                        rs.getString("status"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_jadwal_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getString("nama_mapel"),
                        rs.getString("nama_guru"),
                        rs.getString("hari"),
                        rs.getTime("jam_mulai").toLocalTime(),
                        rs.getTime("jam_selesai").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil presensi berdasarkan NIS: " + e.getMessage());
            e.printStackTrace();
        }
        return presensiList;
    }

}
