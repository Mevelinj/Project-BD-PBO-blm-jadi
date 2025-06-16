package com.example.projectdouble.DAO;

import com.example.projectdouble.model.PesertaEkskul;
import com.example.projectdouble.model.Siswa;
import com.example.projectdouble.util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesertaEkskulDAO {



    /**
     * Menambahkan peserta ekstrakurikuler baru ke database.
     * @param pesertaEkskul Objek PesertaEkskul yang akan ditambahkan.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean addPesertaEkskul(PesertaEkskul pesertaEkskul) {
//        // Menggunakan nama tabel lowercase 'peserta_ekskul'
//        String sql = "INSERT INTO peserta_ekskul (nis, id_ekstrakurikuler) VALUES (?, ?) RETURNING id_peserta";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, pesertaEkskul.getNis()); // DDL Anda menggunakan 'nis' bukan 'nis_siswa'
//            stmt.setInt(2, pesertaEkskul.getIdEkstrakurikuler());
//            int rowsAffected = stmt.executeUpdate();
//
//            if (rowsAffected > 0) {
//                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        pesertaEkskul.setIdPeserta(generatedKeys.getInt(1));
//                    }
//                }
//                return true;
//            }
//            return false;
//        } catch (SQLException e) {
//            System.err.println("Error saat menambahkan peserta ekstrakurikuler: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }

        // Cek duplikasi sebelum menambahkan
        if (isPesertaEkskulExist(pesertaEkskul.getNis(), pesertaEkskul.getIdEkstrakurikuler())) {
            System.err.println("Error: Siswa dengan NIS " + pesertaEkskul.getNis() + " sudah terdaftar di ekstrakurikuler ini.");
            return false;
        }

        String sql = "INSERT INTO peserta_ekskul (nis, id_ekstrakurikuler) VALUES (?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pesertaEkskul.getNis());
            stmt.setInt(2, pesertaEkskul.getIdEkstrakurikuler());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan peserta ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memeriksa apakah siswa sudah terdaftar di ekstrakurikuler tertentu.
     * @param nis NIS siswa.
     * @param idEkstrakurikuler ID ekstrakurikuler.
     * @return true jika sudah ada, false jika tidak.
     */
    public boolean isPesertaEkskulExist(String nis, int idEkstrakurikuler) {
        String sql = "SELECT COUNT(*) FROM peserta_ekskul WHERE nis = ? AND id_ekstrakurikuler = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            stmt.setInt(2, idEkstrakurikuler);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error saat memeriksa keberadaan peserta ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mengambil daftar peserta ekstrakurikuler berdasarkan ID ekstrakurikuler.
     * Menggabungkan dengan tabel Siswa dan Kelas untuk mendapatkan nama siswa dan nama kelas.
     * @param idEkstrakurikuler ID Ekstrakurikuler yang dicari.
     * @return List objek PesertaEkskul yang cocok.
     */
    public List<PesertaEkskul> getPesertaEkskulByEkstrakurikulerId(int idEkstrakurikuler) {
//        List<PesertaEkskul> pesertaList = new ArrayList<>();
//        String sql = "SELECT pe.id_peserta, pe.nis, pe.id_ekstrakurikuler, " +
//                "s.nama AS nama_siswa, e.nama AS nama_ekstrakurikuler, k.nama_kelas " +
//                "FROM peserta_ekskul pe " +
//                "JOIN siswa s ON pe.nis = s.nis " +
//                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " + // Gunakan LEFT JOIN karena siswa mungkin belum punya kelas
//                "JOIN ekstrakurikuler e ON pe.id_ekstrakurikuler = e.id_ekstrakurikuler " +
//                "WHERE pe.id_ekstrakurikuler = ? ORDER BY s.nama";
//
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, idEkstrakurikuler);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                pesertaList.add(new PesertaEkskul(
//                        rs.getInt("id_peserta"),
//                        rs.getString("nis"),
//                        rs.getString("nama_siswa"),
//                        rs.getInt("id_ekstrakurikuler"),
//                        rs.getString("nama_ekstrakurikuler"),
//                        rs.getString("nama_kelas") // Mendapatkan nama_kelas dari hasil join
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error getting peserta ekskul by extracurricular ID: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return pesertaList;

        List<PesertaEkskul> pesertaList = new ArrayList<>();
        String sql = "SELECT pe.id_peserta, pe.nis, pe.id_ekstrakurikuler, " +
                "s.nama AS nama_siswa, " +
                "e.nama AS nama_ekskul, " +
                "k.nama_kelas AS nama_kelas_siswa, " +
                "ta.tahun_mulai, ta.tahun_selesai, ta.tahun_ganjil_genap AS semester_tahun_ajaran_siswa " +
                "FROM peserta_ekskul pe " +
                "JOIN siswa s ON pe.nis = s.nis " +
                "JOIN ekstrakurikuler e ON pe.id_ekstrakurikuler = e.id_ekstrakurikuler " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "WHERE pe.id_ekstrakurikuler = ? ORDER BY s.nama";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEkstrakurikuler);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tahunAjaranSiswa = null;
                if (rs.getObject("tahun_mulai") != null && rs.getObject("tahun_selesai") != null && rs.getString("semester_tahun_ajaran_siswa") != null) {
                    tahunAjaranSiswa = rs.getInt("tahun_mulai") + "/" + rs.getInt("tahun_selesai") + " - " + rs.getString("semester_tahun_ajaran_siswa");
                }
                pesertaList.add(new PesertaEkskul(
                        rs.getInt("id_peserta"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama_ekskul"),
                        rs.getString("nama_kelas_siswa"),
                        tahunAjaranSiswa
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil peserta ekstrakurikuler berdasarkan ID ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
        }
        return pesertaList;
    }

    /**
     * Mengambil semua peserta ekstrakurikuler dari database.
     * @return List objek PesertaEkskul.
     */

    public List<PesertaEkskul> getAllPesertaEkskul() {
//        List<PesertaEkskul> pesertaList = new ArrayList<>();
//        // Menggunakan nama tabel lowercase 'peserta_ekskul', 'siswa', 'ekstrakurikuler'
//        String sql = "SELECT pe.id_peserta, pe.nis, s.nama AS nama_siswa, pe.id_ekstrakurikuler, e.nama AS nama_ekstrakurikuler " +
//                "FROM peserta_ekskul pe " +
//                "JOIN siswa s ON pe.nis = s.nis " +
//                "JOIN ekstrakurikuler e ON pe.id_ekstrakurikuler = e.id_ekstrakurikuler ORDER BY s.nama";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                pesertaList.add(new PesertaEkskul(
//                        rs.getInt("id_peserta"),
//                        rs.getString("nis"),
//                        rs.getString("nama_siswa"),
//                        rs.getInt("id_ekstrakurikuler"),
//                        rs.getString("nama_ekstrakurikuler")
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mengambil semua peserta ekstrakurikuler: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return pesertaList;

        List<PesertaEkskul> pesertaList = new ArrayList<>();
        String sql = "SELECT pe.id_peserta, pe.nis, pe.id_ekstrakurikuler, " +
                "s.nama AS nama_siswa, " +
                "e.nama AS nama_ekskul, " +
                "k.nama_kelas AS nama_kelas_siswa, " +
                "ta.tahun_mulai, ta.tahun_selesai, ta.tahun_ganjil_genap AS semester_tahun_ajaran_siswa " +
                "FROM peserta_ekskul pe " +
                "JOIN siswa s ON pe.nis = s.nis " +
                "JOIN ekstrakurikuler e ON pe.id_ekstrakurikuler = e.id_ekstrakurikuler " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "ORDER BY s.nama";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String tahunAjaranSiswa = null;
                if (rs.getObject("tahun_mulai") != null && rs.getObject("tahun_selesai") != null && rs.getString("semester_tahun_ajaran_siswa") != null) {
                    tahunAjaranSiswa = rs.getInt("tahun_mulai") + "/" + rs.getInt("tahun_selesai") + " - " + rs.getString("semester_tahun_ajaran_siswa");
                }
                pesertaList.add(new PesertaEkskul(
                        rs.getInt("id_peserta"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama_ekskul"),
                        rs.getString("nama_kelas_siswa"),
                        tahunAjaranSiswa
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil semua peserta ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
        }
        return pesertaList;
    }

    /**
     * Mengambil peserta ekstrakurikuler berdasarkan ID.
     * @param idPeserta ID peserta ekstrakurikuler.
     * @return Objek PesertaEkskul jika ditemukan, null jika tidak.
     */
    /*
    public PesertaEkskul getPesertaEkskulById(int idPeserta) {
        // Menggunakan nama tabel lowercase 'peserta_ekskul', 'siswa', 'ekstrakurikuler'
        String sql = "SELECT pe.id_peserta, pe.nis, s.nama AS nama_siswa, pe.id_ekstrakurikuler, e.nama AS nama_ekstrakurikuler " +
                "FROM peserta_ekskul pe " +
                "JOIN siswa s ON pe.nis = s.nis " +
                "JOIN ekstrakurikuler e ON pe.id_ekstrakurikuler = e.id_ekstrakurikuler " +
                "WHERE pe.id_peserta = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPeserta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PesertaEkskul(
                        rs.getInt("id_peserta"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_ekstrakurikuler"),
                        rs.getString("nama_ekstrakurikuler")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil peserta ekstrakurikuler berdasarkan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

     */

    /**
     * Mengambil daftar siswa yang terdaftar dalam ekstrakurikuler tertentu.
     * Mengembalikan objek Siswa dengan informasi user (jika ada).
     * @param idEkstrakurikuler ID ekstrakurikuler.
     * @return List objek Siswa.
     */
    public List<Siswa> getStudentsByExtracurricular(int idEkstrakurikuler) {
//        List<Siswa> studentList = new ArrayList<>();
//        // Query disesuaikan agar hanya mengambil kolom yang ada di tabel SISWA dan USER
//        // DDL Anda tidak memiliki kolom kelas di tabel SISWA, jadi tidak bisa mengambil nama_kelas/tahun_ajaran langsung dari SISWA
//        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
//                "u.id_user, u.username AS username_user, u.password AS password_user " + // Password disertakan jika dibutuhkan
//                "FROM peserta_ekskul pe " +
//                "JOIN siswa s ON pe.nis = s.nis " +
//                "LEFT JOIN users u ON s.id_user = u.id_user " + // Menggunakan nama tabel lowercase 'users'
//                "WHERE pe.id_ekstrakurikuler = ?";
//        try (Connection conn = DBConnect.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, idEkstrakurikuler);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                // Perhatikan konstruktor Siswa yang digunakan harus sesuai dengan model Anda
//                // Karena informasi kelas tidak ada di tabel Siswa (berdasarkan DDL Anda),
//                // maka parameter untuk idKelas, namaKelas, dll. akan null.
//                studentList.add(new Siswa(
//                        rs.getString("nis"),
//                        rs.getString("nama"),
//                        rs.getString("jenis_kelamin"),
//                        rs.getString("tempat_lahir"),
//                        rs.getDate("tanggal_lahir").toLocalDate(),
//                        rs.getString("alamat"),
//                        null, // idKelas (tidak ada di tabel siswa)
//                        null, // namaKelas (tidak ada di tabel siswa)
//                        null, // idTahunAjaran (tidak ada di tabel siswa)
//                        null, // tahunAjaranLengkap (tidak ada di tabel siswa)
//                        null, // semester (tidak ada di tabel siswa)
//                        (Integer) rs.getObject("id_user"), // idUser bisa null
//                        rs.getString("username_user"), // username_user bisa null
//                        rs.getString("password_user") // password_user bisa null
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("Error saat mengambil siswa berdasarkan ekstrakurikuler: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return studentList;

        List<Siswa> studentList = new ArrayList<>();
        // Query disesuaikan untuk join ke tabel kelas dan tahun_ajaran melalui kolom langsung di tabel siswa
        String sql = "SELECT s.nis, s.nama, s.jenis_kelamin, s.tempat_lahir, s.tanggal_lahir, s.alamat, " +
                "u.id_user, u.username AS username_user, u.password AS password_user, " +
                "s.id_kelas, k.nama_kelas, s.id_tahun_ajaran, (ta.tahun_mulai || '/' || ta.tahun_selesai) AS tahun_ajaran_lengkap, s.semester " +
                "FROM peserta_ekskul pe " +
                "JOIN siswa s ON pe.nis = s.nis " +
                "LEFT JOIN users u ON s.id_user = u.id_user " +
                "LEFT JOIN kelas k ON s.id_kelas = k.id_kelas " +
                "LEFT JOIN tahun_ajaran ta ON s.id_tahun_ajaran = ta.id_tahun_ajaran " +
                "WHERE pe.id_ekstrakurikuler = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEkstrakurikuler);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                studentList.add(new Siswa(
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
                        //rs.getString("semester"),
                        (Integer) rs.getObject("id_user"),
                        rs.getString("username_user"),
                        rs.getString("password_user")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil siswa berdasarkan ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
        }
        return studentList;
    }


    /**
     * Menghapus peserta ekstrakurikuler dari database berdasarkan ID.
     * @param idPeserta ID peserta yang akan dihapus.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deletePesertaEkskul(int idPeserta) {
        // Menggunakan nama tabel lowercase 'peserta_ekskul'
        String sql = "DELETE FROM peserta_ekskul WHERE id_peserta = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPeserta);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus peserta ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus seorang siswa dari ekstrakurikuler tertentu berdasarkan NIS dan ID Ekstrakurikuler.
     * @param nis NIS siswa.
     * @param idEkstrakurikuler ID ekstrakurikuler.
     * @return true jika berhasil, false jika gagal.
     */
    public boolean deletePesertaEkskulByNisAndEkskulId(String nis, int idEkstrakurikuler) {
        // Menggunakan nama tabel lowercase 'peserta_ekskul'
        String sql = "DELETE FROM peserta_ekskul WHERE nis = ? AND id_ekstrakurikuler = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nis);
            stmt.setInt(2, idEkstrakurikuler);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus peserta ekstrakurikuler berdasarkan NIS dan ID Ekstrakurikuler: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
