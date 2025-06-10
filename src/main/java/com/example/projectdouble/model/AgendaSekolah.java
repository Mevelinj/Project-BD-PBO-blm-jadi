package com.example.projectdouble.model;

import java.time.LocalDate;

public class AgendaSekolah {
    private int idAgendaSekolah;
    private String judul; // Menggunakan judul sebagai 'content' di FXML
    private String deskripsi; // Menggunakan deskripsi sebagai 'start' dan 'end' digabungkan
    private LocalDate tanggal; // Menggunakan tanggal sebagai tanggal utama (misal: tanggal mulai)

    public AgendaSekolah(int idAgendaSekolah, String judul, String deskripsi, LocalDate tanggal) {
        this.idAgendaSekolah = idAgendaSekolah;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
    }

    // Getters and Setters
    public int getIdAgendaSekolah() {
        return idAgendaSekolah;
    }

    public void setIdAgendaSekolah(int idAgendaSekolah) {
        this.idAgendaSekolah = idAgendaSekolah;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }
}
