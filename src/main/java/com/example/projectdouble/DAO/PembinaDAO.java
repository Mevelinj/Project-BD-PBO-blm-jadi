package com.example.projectdouble.DAO;

import com.example.projectdouble.model.Pembina;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PembinaDAO {
    /**
     * Menambahkan pembina baru ke ekstrakurikuler.
     * @param pembina Objek Pembina yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addPembina(Pembina pembina) {
        String sql = "INSERT INTO pembina (nip, id_ekstrakurikuler) VALUES (?, ?) ON CONFLICT (nip, id_ekstrakurikuler) DO NOTHING RETURNING id_pembina"; // Menambahkan ON CONFLICT
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pembina.getNip());
            stmt.setInt(2, pembina.getIdEkstrakurikuler());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pembina.setIdPembina(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false; // Return false if ON CONFLICT DO NOTHING was triggered
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan pembina: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus pembina dari ekstrakurikuler.
     * @param idPembina ID pembina yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deletePembina(int idPembina) {
        String sql = "DELETE FROM pembina WHERE id_pembina = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPembina);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus pembina: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua data pembina dari database, termasuk nama guru dan nama ekstrakurikuler.
     * @return List objek Pembina.
     */
    public List<Pembina> getAllPembina() {
        List<Pembina> pembinaList = new ArrayList<>();
        String sql = "SELECT p.id_pembina, p.nip, g.nama AS nama_guru, " +
                "p.id_ekstrakurikuler, e.nama AS nama_ekstrakurikuler " +
                "FROM pembina p " +
                "JOIN guru g ON p.nip = g.nip " +
                "JOIN ekstrakurikuler e ON p.id_ekstrakurikuler = e.id_ekstrakurikuler " +
                "ORDER BY e.nama, g.nama";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pembinaList.add(new Pembina(
                        rs.getInt("id_pembina"),
                        rs.getString("nip"),
                        rs.getString("nama_guru"),
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama_ekstrakurikuler")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua pembina: " + e.getMessage());
            e.printStackTrace();
        }
        return pembinaList;
    }

    /**
     * Mengambil daftar pembina untuk ekstrakurikuler tertentu.
     * @param idEkstrakurikuler ID ekstrakurikuler.
     * @return List objek Pembina.
     */
    public List<Pembina> getPembinaByEkstrakurikuler(int idEkstrakurikuler) {
        List<Pembina> pembinaList = new ArrayList<>();
        String sql = "SELECT p.id_pembina, p.nip, g.nama AS nama_guru, " +
                "p.id_ekstrakurikuler, e.nama AS nama_ekstrakurikuler " +
                "FROM pembina p " +
                "JOIN guru g ON p.nip = g.nip " +
                "JOIN ekstrakurikuler e ON p.id_ekstrakurikuler = e.id_ekstrakurikuler " +
                "WHERE p.id_ekstrakurikuler = ? " +
                "ORDER BY g.nama";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEkstrakurikuler);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pembinaList.add(new Pembina(
                        rs.getInt("id_pembina"),
                        rs.getString("nip"),
                        rs.getString("nama_guru"),
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama_ekstrakurikuler")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil pembina berdasarkan ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
        }
        return pembinaList;
    }

}
