package com.example.projectdouble.model;

public class PesertaEkskul {
    private int idPeserta; // id_peserta
    private String nis; // NIS Siswa
    private int idEkstrakurikuler;

    // Untuk memudahkan tampilan
    private String namaSiswa;
    private String namaEkstrakurikuler; // Ini sudah ada di model awal, tapi saya tambahkan ulang untuk memastikan
    private String namaKelasSiswa; // Nama kelas siswa
    private String tahunAjaranSiswa; // Tahun ajaran lengkap siswa (ex: 2023/2024 - Ganjil)

    public PesertaEkskul(int idPeserta, String nis, int idEkstrakurikuler) {
        this.idPeserta = idPeserta;
        this.nis = nis;
        this.idEkstrakurikuler = idEkstrakurikuler;
    }

    // Constructor dengan nama-nama terkait (untuk memuat data ke tabel)
    public PesertaEkskul(int idPeserta, String nis, String namaSiswa, int idEkstrakurikuler, String namaEkstrakurikuler, String namaKelasSiswa, String tahunAjaranSiswa) {
        this.idPeserta = idPeserta;
        this.nis = nis;
        this.namaSiswa = namaSiswa;
        this.idEkstrakurikuler = idEkstrakurikuler;
        this.namaEkstrakurikuler = namaEkstrakurikuler;
        this.namaKelasSiswa = namaKelasSiswa;
        this.tahunAjaranSiswa = tahunAjaranSiswa;
    }

    // Getters and Setters
    public int getIdPeserta() {
        return idPeserta;
    }

    public void setIdPeserta(int idPeserta) {
        this.idPeserta = idPeserta;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public int getIdEkstrakurikuler() {
        return idEkstrakurikuler;
    }

    public void setIdEkstrakurikuler(int idEkstrakurikuler) {
        this.idEkstrakurikuler = idEkstrakurikuler;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }

    public String getNamaEkstrakurikuler() {
        return namaEkstrakurikuler;
    }

    public void setNamaEkstrakurikuler(String namaEkstrakurikuler) {
        this.namaEkstrakurikuler = namaEkstrakurikuler;
    }

    public String getNamaKelasSiswa() {
        return namaKelasSiswa;
    }

    public void setNamaKelasSiswa(String namaKelasSiswa) {
        this.namaKelasSiswa = namaKelasSiswa;
    }

    public String getTahunAjaranSiswa() {
        return tahunAjaranSiswa;
    }

    public void setTahunAjaranSiswa(String tahunAjaranSiswa) {
        this.tahunAjaranSiswa = tahunAjaranSiswa;
    }
}
