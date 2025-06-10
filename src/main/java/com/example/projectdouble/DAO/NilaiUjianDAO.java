package com.example.projectdouble.DAO;

import com.example.projectdouble.model.NilaiUjian;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NilaiUjianDAO {
    /**
     * Menambahkan nilai ujian baru ke database.
     * @param nilaiUjian Objek NilaiUjian yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addNilaiUjian(NilaiUjian nilaiUjian) {
        String sql = "INSERT INTO nilai_ujian (jenis_ujian, nilai, semester, id_mapel, nis) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nilaiUjian.getJenisUjian());
            stmt.setBigDecimal(2, nilaiUjian.getNilai()); // Menggunakan setBigDecimal
            stmt.setString(3, nilaiUjian.getSemester());
            stmt.setInt(4, nilaiUjian.getIdMapel());
            stmt.setString(5, nilaiUjian.getNis());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        nilaiUjian.setIdNilaiUjian(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan nilai ujian: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data nilai ujian di database.
     * @param nilaiUjian Objek NilaiUjian dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateNilaiUjian(NilaiUjian nilaiUjian) {
        String sql = "UPDATE nilai_ujian SET jenis_ujian = ?, nilai = ?, semester = ?, id_mapel = ?, nis = ? WHERE id_nilai_ujian = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nilaiUjian.getJenisUjian());
            stmt.setBigDecimal(2, nilaiUjian.getNilai());
            stmt.setString(3, nilaiUjian.getSemester());
            stmt.setInt(4, nilaiUjian.getIdMapel());
            stmt.setString(5, nilaiUjian.getNis());
            stmt.setInt(6, nilaiUjian.getIdNilaiUjian());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui nilai ujian: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus nilai ujian dari database.
     * @param idNilaiUjian ID nilai ujian yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteNilaiUjian(int idNilaiUjian) {
        String sql = "DELETE FROM nilai_ujian WHERE id_nilai_ujian = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idNilaiUjian);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus nilai ujian: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data nilai ujian dari database, termasuk nama mapel dan siswa.
     * @return List objek NilaiUjian.
     */
    public List<NilaiUjian> getAllNilaiUjian() {
        List<NilaiUjian> nilaiList = new ArrayList<>();
        String sql = "SELECT nu.id_nilai_ujian, nu.jenis_ujian, nu.nilai, nu.semester, " +
                "nu.id_mapel, mp.nama_mapel, nu.nis, s.nama AS nama_siswa " +
                "FROM nilai_ujian nu " +
                "JOIN mata_pelajaran mp ON nu.id_mapel = mp.id_mapel " +
                "JOIN siswa s ON nu.nis = s.nis " +
                "ORDER BY s.nama, nu.semester, mp.nama_mapel, nu.jenis_ujian";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nilaiList.add(new NilaiUjian(
                        rs.getInt("id_nilai_ujian"),
                        rs.getString("jenis_ujian"),
                        rs.getBigDecimal("nilai"),
                        rs.getString("semester"),
                        rs.getInt("id_mapel"),
                        rs.getString("nis"),
                        rs.getString("nama_mapel"),
                        rs.getString("nama_siswa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua nilai ujian: " + e.getMessage());
            e.printStackTrace();
        }
        return nilaiList;
    }

    /**
     * Mengambil data nilai ujian berdasarkan ID.
     * @param idNilaiUjian ID nilai ujian yang dicari.
     * @return Objek NilaiUjian jika ditemukan, null jika tidak.
     */
    public NilaiUjian getNilaiUjianById(int idNilaiUjian) {
        String sql = "SELECT nu.id_nilai_ujian, nu.jenis_ujian, nu.nilai, nu.semester, " +
                "nu.id_mapel, mp.nama_mapel, nu.nis, s.nama AS nama_siswa " +
                "FROM nilai_ujian nu " +
                "JOIN mata_pelajaran mp ON nu.id_mapel = mp.id_mapel " +
                "JOIN siswa s ON nu.nis = s.nis " +
                "WHERE nu.id_nilai_ujian = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idNilaiUjian);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NilaiUjian(
                        rs.getInt("id_nilai_ujian"),
                        rs.getString("jenis_ujian"),
                        rs.getBigDecimal("nilai"),
                        rs.getString("semester"),
                        rs.getInt("id_mapel"),
                        rs.getString("nis"),
                        rs.getString("nama_mapel"),
                        rs.getString("nama_siswa")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil nilai ujian berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil daftar nilai ujian untuk siswa tertentu.
     * @param nis NIS siswa.
     * @return List objek NilaiUjian.
     */
    public List<NilaiUjian> getNilaiUjianByNis(String nis) {
        List<NilaiUjian> nilaiList = new ArrayList<>();
        String sql = "SELECT nu.id_nilai_ujian, nu.jenis_ujian, nu.nilai, nu.semester, " +
                "nu.id_mapel, mp.nama_mapel, nu.nis, s.nama AS nama_siswa " +
                "FROM nilai_ujian nu " +
                "JOIN mata_pelajaran mp ON nu.id_mapel = mp.id_mapel " +
                "JOIN siswa s ON nu.nis = s.nis " +
                "WHERE nu.nis = ? " +
                "ORDER BY nu.semester, mp.nama_mapel, nu.jenis_ujian";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nilaiList.add(new NilaiUjian(
                        rs.getInt("id_nilai_ujian"),
                        rs.getString("jenis_ujian"),
                        rs.getBigDecimal("nilai"),
                        rs.getString("semester"),
                        rs.getInt("id_mapel"),
                        rs.getString("nis"),
                        rs.getString("nama_mapel"),
                        rs.getString("nama_siswa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil nilai ujian berdasarkan NIS: " + e.getMessage());
            e.printStackTrace();
        }
        return nilaiList;
    }

    /**
     * Mengambil daftar nilai ujian untuk guru tertentu (mata pelajaran yang diajar).
     * @param nipGuru NIP guru.
     * @return List objek NilaiUjian.
     */
    public List<NilaiUjian> getNilaiUjianByGuru(String nipGuru) {
        List<NilaiUjian> nilaiList = new ArrayList<>();
        String sql = "SELECT nu.id_nilai_ujian, nu.jenis_ujian, nu.nilai, nu.semester, " +
                "nu.id_mapel, mp.nama_mapel, nu.nis, s.nama AS nama_siswa " +
                "FROM nilai_ujian nu " +
                "JOIN mata_pelajaran mp ON nu.id_mapel = mp.id_mapel " +
                "JOIN jadwal_kelas jk ON mp.id_mapel = jk.id_mapel " +
                "JOIN guru g ON jk.nip_guru = g.nip " +
                "JOIN siswa s ON nu.nis = s.nis " +
                "WHERE g.nip = ? " +
                "GROUP BY nu.id_nilai_ujian, nu.jenis_ujian, nu.nilai, nu.semester, nu.id_mapel, mp.nama_mapel, nu.nis, s.nama " +
                "ORDER BY s.nama, nu.semester, mp.nama_mapel, nu.jenis_ujian";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nipGuru);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nilaiList.add(new NilaiUjian(
                        rs.getInt("id_nilai_ujian"),
                        rs.getString("jenis_ujian"),
                        rs.getBigDecimal("nilai"),
                        rs.getString("semester"),
                        rs.getInt("id_mapel"),
                        rs.getString("nis"),
                        rs.getString("nama_mapel"),
                        rs.getString("nama_siswa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil nilai ujian berdasarkan guru: " + e.getMessage());
            e.printStackTrace();
        }
        return nilaiList;
    }

}
