package com.example.projectdouble.model;

public class Ekstrakurikuler {
    private int idEkstrakurikuler;
    private String nama;
    private String tingkat; // Contoh: 'SD', 'SMP', 'SMA', 'Umum'
    private String mentorNames; // Untuk menampung nama-nama pembina (untuk tampilan)

    public Ekstrakurikuler(int idEkstrakurikuler, String nama, String tingkat) {
        this.idEkstrakurikuler = idEkstrakurikuler;
        this.nama = nama;
        this.tingkat = tingkat;
    }

    public Ekstrakurikuler(int idEkstrakurikuler, String nama, String tingkat, String mentorNames) {
        this.idEkstrakurikuler = idEkstrakurikuler;
        this.nama = nama;
        this.tingkat = tingkat;
        this.mentorNames = mentorNames;
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

    public String getMentorNames() {
        return mentorNames;
    }

    public void setMentorNames(String mentorNames) {
        this.mentorNames = mentorNames;
    }

    @Override
    public String toString() {
        return nama + " (" + tingkat + ")"; // Penting untuk ComboBox Ekstrakurikuler
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ekstrakurikuler that = (Ekstrakurikuler) obj;
        return idEkstrakurikuler == that.idEkstrakurikuler;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idEkstrakurikuler);
    }
}
