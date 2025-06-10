package com.example.projectdouble.model;

public class PesertaEkskul {
    private int idPeserta; // id_peserta
    private String nis; // NIS Siswa
    private int idEkstrakurikuler;

    // Untuk memudahkan tampilan
    private String namaSiswa;
    private String namaEkstrakurikuler;

    public PesertaEkskul(int idPeserta, String nis, int idEkstrakurikuler) {
        this.idPeserta = idPeserta;
        this.nis = nis;
        this.idEkstrakurikuler = idEkstrakurikuler;
    }

    // Constructor dengan nama-nama terkait
    public PesertaEkskul(int idPeserta, String nis, String namaSiswa, int idEkstrakurikuler, String namaEkstrakurikuler) {
        this.idPeserta = idPeserta;
        this.nis = nis;
        this.namaSiswa = namaSiswa;
        this.idEkstrakurikuler = idEkstrakurikuler;
        this.namaEkstrakurikuler = namaEkstrakurikuler;
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
}
