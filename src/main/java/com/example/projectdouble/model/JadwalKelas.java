package com.example.projectdouble.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JadwalKelas {
    private int idJadwalKelas;
    private String hari;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private int idKelas;
    private int idMapel;
    private String nipGuru;
    //private String semester; // NEW: Kolom semester

    // Tambahan untuk tampilan di tabel (bukan bagian dari kolom DB jadwal_kelas kecuali semester)
    private String namaMapel;
    private String namaKelas; // Nama kelas saja
    private String tahunAjaranLengkap; // Tahun ajaran seperti "2023/2024"
    private String namaGuru;

    // Constructor untuk data dari database (tanpa detail terkait) - Ini mungkin tidak lagi banyak digunakan
    public JadwalKelas(int idJadwalKelas, int idKelas, int idMapel, String nipGuru, String hari, LocalTime jamMulai, LocalTime jamSelesai) {//, String semester
        this.idJadwalKelas = idJadwalKelas;
        this.idKelas = idKelas;
        this.idMapel = idMapel;
        this.nipGuru = nipGuru;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        //this.semester = semester;
    }

    // Constructor baru untuk data yang lebih lengkap (misalnya dari JOIN query)
    public JadwalKelas(int idJadwalKelas, String hari, LocalTime jamMulai, LocalTime jamSelesai,
                       String namaMapel, String namaKelas, String tahunAjaranLengkap, String namaGuru) {//, String semester
        this.idJadwalKelas = idJadwalKelas;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.namaMapel = namaMapel;
        this.namaKelas = namaKelas;
        this.tahunAjaranLengkap = tahunAjaranLengkap;
        //this.semester = semester; // Mengisi semester dari hasil join
        this.namaGuru = namaGuru;
    }


    // Constructor for adding new schedule (if the order of arguments is like this)
    // Updated to include semester
    public JadwalKelas(int idJadwalKelas, String hari, LocalTime jamMulai, LocalTime jamSelesai, int idKelas, int idMapel, String nipGuru) {//, String semester
        this.idJadwalKelas = idJadwalKelas;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.idKelas = idKelas;
        this.idMapel = idMapel;
        this.nipGuru = nipGuru;
        //this.semester = semester; // Mengisi semester dari input
    }

    // Getters
    public int getIdJadwalKelas() {
        return idJadwalKelas;
    }

    public String getHari() {
        return hari;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }

    public int getIdKelas() {
        return idKelas;
    }

    public int getIdMapel() {
        return idMapel;
    }

    public String getNipGuru() {
        return nipGuru;
    }

//    public String getSemester() { // NEW: Getter untuk semester
//        return semester;
//    }

    // Getters untuk properti tampilan
    public String getNamaMapel() {
        return namaMapel;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public String getTahunAjaranLengkap() {
        return tahunAjaranLengkap;
    }

    // Perhatikan bahwa getSemester() sudah ada di atas
    // public String getSemester() { return semester; }

    public String getNamaGuru() {
        return namaGuru;
    }

    // Properti terhitung untuk "Time" (HH:MM - HH:MM)
    public String getTimeRange() {
        if (jamMulai != null && jamSelesai != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return jamMulai.format(formatter) + " - " + jamSelesai.format(formatter);
        }
        return "";
    }

    // Properti terhitung untuk "Class" (NamaKelas (TahunAjaran))
    public String getKelasDanTahunAjaran() {
        if (namaKelas != null && tahunAjaranLengkap != null) {
            return namaKelas + " (" + tahunAjaranLengkap + ")";
        } else if (namaKelas != null) {
            return namaKelas;
        }
        return "";
    }

    // Setters (jika diperlukan, tapi biasanya tidak untuk model yang dimuat dari DB)
    public void setIdJadwalKelas(int idJadwalKelas) {
        this.idJadwalKelas = idJadwalKelas;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public void setIdMapel(int idMapel) {
        this.idMapel = idMapel;
    }

    public void setNipGuru(String nipGuru) {
        this.nipGuru = nipGuru;
    }

//    public void setSemester(String semester) { // NEW: Setter untuk semester
//        this.semester = semester;
//    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public void setTahunAjaranLengkap(String tahunAjaranLengkap) {
        this.tahunAjaranLengkap = tahunAjaranLengkap;
    }

    public void setNamaGuru(String namaGuru) {
        this.namaGuru = namaGuru;
    }
}

//public class JadwalKelas {
//    private int idJadwalKelas;
//    private String hari;
//    private LocalTime jamMulai;
//    private LocalTime jamSelesai;
//    private int idKelas;
//    private int idMapel;
//    private String nipGuru;
//
//    // Tambahan untuk tampilan di tabel (bukan bagian dari kolom DB jadwal_kelas)
//    private String namaMapel;
//    private String namaKelas; // Nama kelas saja
//    private String tahunAjaranLengkap; // Tahun ajaran seperti "2023/2024"
//    private String semester;
//    private String namaGuru;
//
//    // Constructor untuk data dari database (tanpa detail terkait)
//    public JadwalKelas(int idJadwalKelas, int idKelas, int idMapel, String nipGuru, String hari, LocalTime jamMulai, LocalTime jamSelesai) {
//        this.idJadwalKelas = idJadwalKelas;
//        this.idKelas = idKelas;
//        this.idMapel = idMapel;
//        this.nipGuru = nipGuru;
//        this.hari = hari;
//        this.jamMulai = jamMulai;
//        this.jamSelesai = jamSelesai;
//    }
//
//    // Constructor baru untuk data yang lebih lengkap (misalnya dari JOIN query)
//    public JadwalKelas(int idJadwalKelas, String hari, LocalTime jamMulai, LocalTime jamSelesai,
//                       String namaMapel, String namaKelas, String tahunAjaranLengkap, String semester, String namaGuru) {
//        this.idJadwalKelas = idJadwalKelas;
//        this.hari = hari;
//        this.jamMulai = jamMulai;
//        this.jamSelesai = jamSelesai;
//        this.namaMapel = namaMapel;
//        this.namaKelas = namaKelas;
//        this.tahunAjaranLengkap = tahunAjaranLengkap;
//        this.semester = semester;
//        this.namaGuru = namaGuru;
//    }
//
//
//    // Constructor for adding new schedule (if the order of arguments is like this)
//    public JadwalKelas(int idJadwalKelas, String hari, LocalTime jamMulai, LocalTime jamSelesai, int idKelas, int idMapel, String nipGuru) {
//        this.idJadwalKelas = idJadwalKelas;
//        this.hari = hari;
//        this.jamMulai = jamMulai;
//        this.jamSelesai = jamSelesai;
//        this.idKelas = idKelas;
//        this.idMapel = idMapel;
//        this.nipGuru = nipGuru;
//    }
//
//    // Getters
//    public int getIdJadwalKelas() {
//        return idJadwalKelas;
//    }
//
//    public String getHari() {
//        return hari;
//    }
//
//    public LocalTime getJamMulai() {
//        return jamMulai;
//    }
//
//    public LocalTime getJamSelesai() {
//        return jamSelesai;
//    }
//
//    public int getIdKelas() {
//        return idKelas;
//    }
//
//    public int getIdMapel() {
//        return idMapel;
//    }
//
//    public String getNipGuru() {
//        return nipGuru;
//    }
//
//    // Getters untuk properti tampilan
//    public String getNamaMapel() {
//        return namaMapel;
//    }
//
//    public String getNamaKelas() {
//        return namaKelas;
//    }
//
//    public String getTahunAjaranLengkap() {
//        return tahunAjaranLengkap;
//    }
//
//    public String getSemester() {
//        return semester;
//    }
//
//    public String getNamaGuru() {
//        return namaGuru;
//    }
//
//    // Properti terhitung untuk "Time" (HH:MM - HH:MM)
//    public String getTimeRange() {
//        if (jamMulai != null && jamSelesai != null) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//            return jamMulai.format(formatter) + " - " + jamSelesai.format(formatter);
//        }
//        return "";
//    }
//
//    // Properti terhitung untuk "Class" (NamaKelas (TahunAjaran))
//    public String getKelasDanTahunAjaran() {
//        if (namaKelas != null && tahunAjaranLengkap != null) {
//            return namaKelas + " (" + tahunAjaranLengkap + ")";
//        } else if (namaKelas != null) {
//            return namaKelas;
//        }
//        return "";
//    }
//
//    // Setters (jika diperlukan, tapi biasanya tidak untuk model yang dimuat dari DB)
//    public void setIdJadwalKelas(int idJadwalKelas) {
//        this.idJadwalKelas = idJadwalKelas;
//    }
//
//    public void setHari(String hari) {
//        this.hari = hari;
//    }
//
//    public void setJamMulai(LocalTime jamMulai) {
//        this.jamMulai = jamMulai;
//    }
//
//    public void setJamSelesai(LocalTime jamSelesai) {
//        this.jamSelesai = jamSelesai;
//    }
//
//    public void setIdKelas(int idKelas) {
//        this.idKelas = idKelas;
//    }
//
//    public void setIdMapel(int idMapel) {
//        this.idMapel = idMapel;
//    }
//
//    public void setNipGuru(String nipGuru) {
//        this.nipGuru = nipGuru;
//    }
//
//    public void setNamaMapel(String namaMapel) {
//        this.namaMapel = namaMapel;
//    }
//
//    public void setNamaKelas(String namaKelas) {
//        this.namaKelas = namaKelas;
//    }
//
//    public void setTahunAjaranLengkap(String tahunAjaranLengkap) {
//        this.tahunAjaranLengkap = tahunAjaranLengkap;
//    }
//
//    public void setSemester(String semester) {
//        this.semester = semester;
//    }
//
//    public void setNamaGuru(String namaGuru) {
//        this.namaGuru = namaGuru;
//    }
//}

//public class JadwalKelas {
//    private int idJadwalKelas;
//    private int idKelas;
//    private String namaKelas; // Untuk memudahkan tampilan
//    private int idMapel;
//    private String namaMapel; // Untuk memudahkan tampilan
//    private String nipGuru;
//    private String namaGuru; // Untuk memudahkan tampilan
//    private String hari;
//    private LocalTime jamMulai; // Menggunakan LocalTime
//    private LocalTime jamSelesai; // Menggunakan LocalTime
//
//    public JadwalKelas(int idJadwalKelas, int idKelas, int idMapel, String nipGuru, String hari, LocalTime jamMulai, LocalTime jamSelesai) {
//        this.idJadwalKelas = idJadwalKelas;
//        this.idKelas = idKelas;
//        this.idMapel = idMapel;
//        this.nipGuru = nipGuru;
//        this.hari = hari;
//        this.jamMulai = jamMulai;
//        this.jamSelesai = jamSelesai;
//    }
//
//    public JadwalKelas(int idJadwalKelas, int idKelas, String namaKelas, int idMapel, String namaMapel, String nipGuru, String namaGuru, String hari, LocalTime jamMulai, LocalTime jamSelesai) {
//        this.idJadwalKelas = idJadwalKelas;
//        this.idKelas = idKelas;
//        this.namaKelas = namaKelas;
//        this.idMapel = idMapel;
//        this.namaMapel = namaMapel;
//        this.nipGuru = nipGuru;
//        this.namaGuru = namaGuru;
//        this.hari = hari;
//        this.jamMulai = jamMulai;
//        this.jamSelesai = jamSelesai;
//    }
//
//    // Getters and Setters
//    public int getIdJadwalKelas() {
//        return idJadwalKelas;
//    }
//
//    public void setIdJadwalKelas(int idJadwalKelas) {
//        this.idJadwalKelas = idJadwalKelas;
//    }
//
//    public int getIdKelas() {
//        return idKelas;
//    }
//
//    public void setIdKelas(int idKelas) {
//        this.idKelas = idKelas;
//    }
//
//    public String getNamaKelas() {
//        return namaKelas;
//    }
//
//    public void setNamaKelas(String namaKelas) {
//        this.namaKelas = namaKelas;
//    }
//
//    public int getIdMapel() {
//        return idMapel;
//    }
//
//    public void setIdMapel(int idMapel) {
//        this.idMapel = idMapel;
//    }
//
//    public String getNamaMapel() {
//        return namaMapel;
//    }
//
//    public void setNamaMapel(String namaMapel) {
//        this.namaMapel = namaMapel;
//    }
//
//    public String getNipGuru() {
//        return nipGuru;
//    }
//
//    public void setNipGuru(String nipGuru) {
//        this.nipGuru = nipGuru;
//    }
//
//    public String getNamaGuru() {
//        return namaGuru;
//    }
//
//    public void setNamaGuru(String namaGuru) {
//        this.namaGuru = namaGuru;
//    }
//
//    public String getHari() {
//        return hari;
//    }
//
//    public void setHari(String hari) {
//        this.hari = hari;
//    }
//
//    public LocalTime getJamMulai() {
//        return jamMulai;
//    }
//
//    public void setJamMulai(LocalTime jamMulai) {
//        this.jamMulai = jamMulai;
//    }
//
//    public LocalTime getJamSelesai() {
//        return jamSelesai;
//    }
//
//    public void setJamSelesai(LocalTime jamSelesai) {
//        this.jamSelesai = jamSelesai;
//    }
//}
