package com.example.projectdouble.model;

public class Ekstrakurikuler {
    private int idEkstrakurikuler;
    private String nama;
    private String tingkat;

    public Ekstrakurikuler(int idEkstrakurikuler, String nama, String tingkat) {
        this.idEkstrakurikuler = idEkstrakurikuler;
        this.nama = nama;
        this.tingkat = tingkat;
    }

    // Getters and Setters
    public int getIdEkstrakurikuler() {
        return idEkstrakurikuler;
    }

    public void setIdEkstrakurikuler(int idEkstrakurikuler) {
        this.idEkstrakurikuler = idEkstrakurikuler;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    @Override
    public String toString() {
        return nama + " (" + tingkat + ")";
    }
}
