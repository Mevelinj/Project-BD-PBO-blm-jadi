package com.example.projectdouble.model;

import java.time.LocalDate;

public class Pengumuman {
    private int idPengumuman;
    private String judul;
    private String deskripsi;
    private LocalDate tanggal;
    private String lampiran;

    public Pengumuman(int idPengumuman, String judul, String deskripsi, LocalDate tanggal, String lampiran) {
        this.idPengumuman = idPengumuman;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.lampiran = lampiran;
    }

    // Getters and Setters
    public int getIdPengumuman() {
        return idPengumuman;
    }

    public void setIdPengumuman(int idPengumuman) {
        this.idPengumuman = idPengumuman;
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

    public String getLampiran() {
        return lampiran;
    }

    public void setLampiran(String lampiran) {
        this.lampiran = lampiran;
    }
}
