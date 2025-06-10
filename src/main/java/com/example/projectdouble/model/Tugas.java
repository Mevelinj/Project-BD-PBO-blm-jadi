package com.example.projectdouble.model;

import java.time.LocalDate;

public class Tugas {
    private int idTugas;
    private String judul;
    private String deskripsi;
    private LocalDate tanggalDeadline;

    public Tugas(int idTugas, String judul, String deskripsi, LocalDate tanggalDeadline) {
        this.idTugas = idTugas;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggalDeadline = tanggalDeadline;
    }

    // Getters and Setters
    public int getIdTugas() {
        return idTugas;
    }

    public void setIdTugas(int idTugas) {
        this.idTugas = idTugas;
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

    public LocalDate getTanggalDeadline() {
        return tanggalDeadline;
    }

    public void setTanggalDeadline(LocalDate tanggalDeadline) {
        this.tanggalDeadline = tanggalDeadline;
    }
}
