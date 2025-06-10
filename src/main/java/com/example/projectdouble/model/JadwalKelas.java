package com.example.projectdouble.model;

import java.time.LocalTime;

public class JadwalKelas {
    private int idJadwalKelas;
    private String hari;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private int idKelas;
    private int idMapel;
    private String nipGuru;

    // Untuk memudahkan tampilan atau joins
    private String namaKelas;
    private String namaMapel;
    private String namaGuru;

    public JadwalKelas(int idJadwalKelas, String hari, LocalTime jamMulai, LocalTime jamSelesai, int idKelas, int idMapel, String nipGuru) {
        this.idJadwalKelas = idJadwalKelas;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.idKelas = idKelas;
        this.idMapel = idMapel;
        this.nipGuru = nipGuru;
    }

    // Constructor dengan nama-nama terkait
    public JadwalKelas(int idJadwalKelas, String hari, LocalTime jamMulai, LocalTime jamSelesai, int idKelas, String namaKelas, int idMapel, String namaMapel, String nipGuru, String namaGuru) {
        this.idJadwalKelas = idJadwalKelas;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.idMapel = idMapel;
        this.namaMapel = namaMapel;
        this.nipGuru = nipGuru;
        this.namaGuru = namaGuru;
    }

    // Getters and Setters
    public int getIdJadwalKelas() {
        return idJadwalKelas;
    }

    public void setIdJadwalKelas(int idJadwalKelas) {
        this.idJadwalKelas = idJadwalKelas;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public int getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(int idMapel) {
        this.idMapel = idMapel;
    }

    public String getNipGuru() {
        return nipGuru;
    }

    public void setNipGuru(String nipGuru) {
        this.nipGuru = nipGuru;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }

    public String getNamaGuru() {
        return namaGuru;
    }

    public void setNamaGuru(String namaGuru) {
        this.namaGuru = namaGuru;
    }
}
