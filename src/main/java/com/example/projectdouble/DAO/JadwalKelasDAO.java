package com.example.projectdouble.DAO;

import com.example.projectdouble.model.JadwalKelas;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalKelasDAO {
    /**
     * Menambahkan jadwal kelas baru ke database.
     * @param jadwal Objek JadwalKelas yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addJadwalKelas(JadwalKelas jadwal) {
        String sql = "INSERT INTO jadwal_kelas (hari, jam_mulai, jam_selesai, id_kelas, id_mapel, nip_guru) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, jadwal.getHari());
            stmt.setTime(2, Time.valueOf(jadwal.getJamMulai())); // Konversi LocalTime ke java.sql.Time
            stmt.setTime(3, Time.valueOf(jadwal.getJamSelesai()));
            stmt.setInt(4, jadwal.getIdKelas());
            stmt.setInt(5, jadwal.getIdMapel());
            stmt.setString(6, jadwal.getNipGuru());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        jadwal.setIdJadwalKelas(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan jadwal kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data jadwal kelas di database.
     * @param jadwal Objek JadwalKelas dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateJadwalKelas(JadwalKelas jadwal) {
        String sql = "UPDATE jadwal_kelas SET hari = ?, jam_mulai = ?, jam_selesai = ?, id_kelas = ?, id_mapel = ?, nip_guru = ? WHERE id_jadwal_kelas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jadwal.getHari());
            stmt.setTime(2, Time.valueOf(jadwal.getJamMulai()));
            stmt.setTime(3, Time.valueOf(jadwal.getJamSelesai()));
            stmt.setInt(4, jadwal.getIdKelas());
            stmt.setInt(5, jadwal.getIdMapel());
            stmt.setString(6, jadwal.getNipGuru());
            stmt.setInt(7, jadwal.getIdJadwalKelas());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui jadwal kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus jadwal kelas dari database.
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

    /**
     * Mengambil semua data jadwal kelas dari database, termasuk nama kelas, mata pelajaran, dan guru.
     * @return List objek JadwalKelas.
     */
    public List<JadwalKelas> getAllJadwalKelas() {
        List<JadwalKelas> jadwalList = new ArrayList<>();
        String sql = "SELECT jk.id_jadwal_kelas, jk.hari, jk.jam_mulai, jk.jam_selesai, " +
                "k.id_kelas, k.nama_kelas, mp.id_mapel, mp.nama_mapel, g.nip, g.nama AS nama_guru " +
                "FROM jadwal_kelas jk " +
                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
                "JOIN guru g ON jk.nip_guru = g.nip " +
                "ORDER BY jk.hari, jk.jam_mulai";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                jadwalList.add(new JadwalKelas(
                        rs.getInt("id_jadwal_kelas"),
                        rs.getString("hari"),
                        rs.getTime("jam_mulai").toLocalTime(), // Konversi java.sql.Time ke LocalTime
                        rs.getTime("jam_selesai").toLocalTime(),
                        rs.getInt("id_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getInt("id_mapel"),
                        rs.getString("nama_mapel"),
                        rs.getString("nip"),
                        rs.getString("nama_guru")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua jadwal kelas: " + e.getMessage());
            e.printStackTrace();
        }
        return jadwalList;
    }

    /**
     * Mengambil data jadwal kelas berdasarkan ID.
     * @param idJadwalKelas ID jadwal kelas yang dicari.
     * @return Objek JadwalKelas jika ditemukan, null jika tidak.
     */
    public JadwalKelas getJadwalKelasById(int idJadwalKelas) {
        String sql = "SELECT jk.id_jadwal_kelas, jk.hari, jk.jam_mulai, jk.jam_selesai, " +
                "k.id_kelas, k.nama_kelas, mp.id_mapel, mp.nama_mapel, g.nip, g.nama AS nama_guru " +
                "FROM jadwal_kelas jk " +
                "JOIN kelas k ON jk.id_kelas = k.id_kelas " +
                "JOIN mata_pelajaran mp ON jk.id_mapel = mp.id_mapel " +
                "JOIN guru g ON jk.nip_guru = g.nip " +
                "WHERE jk.id_jadwal_kelas = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idJadwalKelas);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new JadwalKelas(
                        rs.getInt("id_jadwal_kelas"),
                        rs.getString("hari"),
                        rs.getTime("jam_mulai").toLocalTime(),
                        rs.getTime("jam_selesai").toLocalTime(),
                        rs.getInt("id_kelas"),
                        rs.getString("nama_kelas"),
                        rs.getInt("id_mapel"),
                        rs.getString("nama_mapel"),
                        rs.getString("nip"),
                        rs.getString("nama_guru")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil jadwal kelas berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
