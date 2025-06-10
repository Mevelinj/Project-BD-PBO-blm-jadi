package com.example.projectdouble.DAO;

import com.example.projectdouble.model.AgendaSekolah;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaSekolahDAO {
    /**
     * Menambahkan agenda sekolah baru ke database.
     * @param agenda Objek AgendaSekolah yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addAgendaSekolah(AgendaSekolah agenda) {
        String sql = "INSERT INTO agenda_sekolah (judul, deskripsi, tanggal) VALUES (?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, agenda.getJudul());
            stmt.setString(2, agenda.getDeskripsi());
            stmt.setDate(3, Date.valueOf(agenda.getTanggal()));
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        agenda.setIdAgendaSekolah(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan agenda sekolah: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data agenda sekolah di database.
     * @param agenda Objek AgendaSekolah dengan data yang diperbarui.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean updateAgendaSekolah(AgendaSekolah agenda) {
        String sql = "UPDATE agenda_sekolah SET judul = ?, deskripsi = ?, tanggal = ? WHERE id_agenda_sekolah = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agenda.getJudul());
            stmt.setString(2, agenda.getDeskripsi());
            stmt.setDate(3, Date.valueOf(agenda.getTanggal()));
            stmt.setInt(4, agenda.getIdAgendaSekolah());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui agenda sekolah: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus agenda sekolah dari database.
     * @param idAgendaSekolah ID agenda sekolah yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deleteAgendaSekolah(int idAgendaSekolah) {
        String sql = "DELETE FROM agenda_sekolah WHERE id_agenda_sekolah = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAgendaSekolah);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus agenda sekolah: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data agenda sekolah dari database.
     * @return List objek AgendaSekolah.
     */
    public List<AgendaSekolah> getAllAgendaSekolah() {
        List<AgendaSekolah> agendaList = new ArrayList<>();
        String sql = "SELECT id_agenda_sekolah, judul, deskripsi, tanggal FROM agenda_sekolah ORDER BY tanggal DESC";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                agendaList.add(new AgendaSekolah(
                        rs.getInt("id_agenda_sekolah"),
                        rs.getString("judul"),
                        rs.getString("deskripsi"),
                        rs.getDate("tanggal").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua agenda sekolah: " + e.getMessage());
            e.printStackTrace();
        }
        return agendaList;
    }

    /**
     * Mengambil data agenda sekolah berdasarkan ID.
     * @param idAgendaSekolah ID agenda sekolah yang dicari.
     * @return Objek AgendaSekolah jika ditemukan, null jika tidak.
     */
    public AgendaSekolah getAgendaSekolahById(int idAgendaSekolah) {
        String sql = "SELECT id_agenda_sekolah, judul, deskripsi, tanggal FROM agenda_sekolah WHERE id_agenda_sekolah = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAgendaSekolah);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AgendaSekolah(
                        rs.getInt("id_agenda_sekolah"),
                        rs.getString("judul"),
                        rs.getString("deskripsi"),
                        rs.getDate("tanggal").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil agenda sekolah berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil agenda sekolah berdasarkan tahun ajaran dan semester.
     * Catatan: DDL Anda tidak memiliki kolom tahun_ajaran atau semester di tabel agenda_sekolah.
     * Implementasi ini akan bersifat konseptual atau memerlukan modifikasi DDL.
     * Untuk saat ini, saya akan mengembalikan semua agenda.
     * @param idTahunAjaran ID Tahun Ajaran.
     * @param semester Semester (Ganjil/Genap).
     * @return List objek AgendaSekolah.
     */
    public List<AgendaSekolah> getAgendaByTahunAjaranAndSemester(int idTahunAjaran, String semester) {
        // Implementasi ini memerlukan perubahan DDL Anda jika Anda ingin menyaring berdasarkan tahun ajaran dan semester.
        // Misalnya, tambahkan kolom id_tahun_ajaran dan semester ke tabel agenda_sekolah.
        // Untuk saat ini, akan mengembalikan semua agenda, karena tidak ada kolom filter di tabel.
        System.out.println("Peringatan: Fungsi getAgendaByTahunAjaranAndSemester belum sepenuhnya diimplementasikan karena kurangnya kolom di DDL.");
        return getAllAgendaSekolah(); // Mengembalikan semua agenda sebagai fallback
    }

}
