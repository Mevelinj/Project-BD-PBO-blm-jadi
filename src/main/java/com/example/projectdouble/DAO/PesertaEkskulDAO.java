package com.example.projectdouble.DAO;

import com.example.projectdouble.model.PesertaEkskul;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesertaEkskulDAO {
    /**
     * Menambahkan siswa ke ekstrakurikuler.
     * @param pesertaEkskul Objek PesertaEkskul yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addPesertaEkskul(PesertaEkskul pesertaEkskul) {
        String sql = "INSERT INTO peserta_ekskul (nis, id_ekstrakurikuler) VALUES (?, ?) ON CONFLICT (nis, id_ekstrakurikuler) DO NOTHING RETURNING id_peserta";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pesertaEkskul.getNis());
            stmt.setInt(2, pesertaEkskul.getIdEkstrakurikuler());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pesertaEkskul.setIdPeserta(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false; // Return false if ON CONFLICT DO NOTHING was triggered
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan peserta ekskul: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus siswa dari ekstrakurikuler.
     * @param idPeserta ID peserta ekskul yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deletePesertaEkskul(int idPeserta) {
        String sql = "DELETE FROM peserta_ekskul WHERE id_peserta = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPeserta);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus peserta ekskul: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data peserta ekstrakurikuler dari database, termasuk nama siswa dan nama ekstrakurikuler.
     * @return List objek PesertaEkskul.
     */
    public List<PesertaEkskul> getAllPesertaEkskul() {
        List<PesertaEkskul> pesertaList = new ArrayList<>();
        String sql = "SELECT pe.id_peserta, pe.nis, s.nama AS nama_siswa, " +
                "pe.id_ekstrakurikuler, e.nama AS nama_ekstrakurikuler " +
                "FROM peserta_ekskul pe " +
                "JOIN siswa s ON pe.nis = s.nis " +
                "JOIN ekstrakurikuler e ON pe.id_ekstrakurikuler = e.id_ekstrakurikuler " +
                "ORDER BY e.nama, s.nama";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pesertaList.add(new PesertaEkskul(
                        rs.getInt("id_peserta"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama_ekstrakurikuler")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua peserta ekskul: " + e.getMessage());
            e.printStackTrace();
        }
        return pesertaList;
    }

    /**
     * Mengambil daftar peserta untuk ekstrakurikuler tertentu.
     * @param idEkstrakurikuler ID ekstrakurikuler.
     * @return List objek PesertaEkskul.
     */
    public List<PesertaEkskul> getPesertaByEkstrakurikuler(int idEkstrakurikuler) {
        List<PesertaEkskul> pesertaList = new ArrayList<>();
        String sql = "SELECT pe.id_peserta, pe.nis, s.nama AS nama_siswa, " +
                "pe.id_ekstrakurikuler, e.nama AS nama_ekstrakurikuler " +
                "FROM peserta_ekskul pe " +
                "JOIN siswa s ON pe.nis = s.nis " +
                "JOIN ekstrakurikuler e ON pe.id_ekstrakurikuler = e.id_ekstrakurikuler " +
                "WHERE pe.id_ekstrakurikuler = ? " +
                "ORDER BY s.nama";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEkstrakurikuler);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pesertaList.add(new PesertaEkskul(
                        rs.getInt("id_peserta"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama_ekstrakurikuler")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil peserta berdasarkan ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
        }
        return pesertaList;
    }

    /**
     * Mengaitkan siswa ke kelas untuk tahun ajaran/semester tertentu.
     * CATATAN: Ini adalah implementasi placeholder. DDL Anda TIDAK memiliki tabel `peserta_kelas`.
     * Anda perlu membuat tabel `peserta_kelas` atau mengintegrasikan logika ini di tempat lain.
     * Asumsi: tabel `peserta_kelas` dengan kolom `nis`, `id_kelas`, `id_tahun_ajaran`, `semester`.
     * @param nis NIS siswa.
     * @param idKelas ID Kelas.
     * @param idTahunAjaran ID Tahun Ajaran.
     * @param semester Semester.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean assignStudentToClass(String nis, int idKelas, int idTahunAjaran, String semester) {
        // PERHATIAN: DDL Anda tidak memiliki tabel `peserta_kelas`
        // Saya akan menggunakan placeholder SQL untuk tabel `peserta_kelas`
        // Anda perlu menambahkan tabel `peserta_kelas` ke DDL Anda jika ini adalah tabel yang Anda inginkan
        String sql = "INSERT INTO peserta_kelas (nis, id_kelas, id_tahun_ajaran, semester) VALUES (?, ?, ?, ?) ON CONFLICT (nis, id_kelas, id_tahun_ajaran, semester) DO NOTHING";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            stmt.setInt(2, idKelas);
            stmt.setInt(3, idTahunAjaran);
            stmt.setString(4, semester);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menetapkan siswa ke kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus siswa dari kelas untuk tahun ajaran/semester tertentu.
     * CATATAN: Ini adalah implementasi placeholder. DDL Anda TIDAK memiliki tabel `peserta_kelas`.
     * @param nis NIS siswa.
     * @param idKelas ID Kelas.
     * @param idTahunAjaran ID Tahun Ajaran.
     * @param semester Semester.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean removeStudentFromClass(String nis, int idKelas, int idTahunAjaran, String semester) {
        String sql = "DELETE FROM peserta_kelas WHERE nis = ? AND id_kelas = ? AND id_tahun_ajaran = ? AND semester = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            stmt.setInt(2, idKelas);
            stmt.setInt(3, idTahunAjaran);
            stmt.setString(4, semester);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus siswa dari kelas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
