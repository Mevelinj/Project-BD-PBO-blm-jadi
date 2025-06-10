package com.example.projectdouble.model;

public class Pembina {
    private int idPembina;
    private String nip; // NIP Guru
    private int idEkstrakurikuler;

    // Untuk memudahkan tampilan
    private String namaGuru;
    private String namaEkstrakurikuler;

    public Pembina(int idPembina, String nip, int idEkstrakurikuler) {
        this.idPembina = idPembina;
        this.nip = nip;
        this.idEkstrakurikuler = idEkstrakurikuler;
    }

    // Constructor dengan nama-nama terkait
    public Pembina(int idPembina, String nip, String namaGuru, int idEkstrakurikuler, String namaEkstrakurikuler) {
        this.idPembina = idPembina;
        this.nip = nip;
        this.namaGuru = namaGuru;
        this.idEkstrakurikuler = idEkstrakurikuler;
        this.namaEkstrakurikuler = namaEkstrakurikuler;
    }

    // Getters and Setters
    public int getIdPembina() {
        return idPembina;
    }

    public void setIdPembina(int idPembina) {
        this.idPembina = idPembina;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public int getIdEkstrakurikuler() {
        return idEkstrakurikuler;
    }

    public void setIdEkstrakurikuler(int idEkstrakurikuler) {
        this.idEkstrakurikuler = idEkstrakurikuler;
    }

    public String getNamaGuru() {
        return namaGuru;
    }

    public void setNamaGuru(String namaGuru) {
        this.namaGuru = namaGuru;
    }

    public String getNamaEkstrakurikuler() {
        return namaEkstrakurikuler;
    }

    public void setNamaEkstrakurikuler(String namaEkstrakurikuler) {
        this.namaEkstrakurikuler = namaEkstrakurikuler;
    }
}
