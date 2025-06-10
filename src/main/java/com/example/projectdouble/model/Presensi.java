package com.example.projectdouble.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Presensi {
    private int idPresensi;
    private LocalDate tanggal;
    private String status;
    private String nis;
    private int idJadwalKelas;

    // Untuk memudahkan tampilan
    private String namaSiswa;
    private String namaKelas;
    private String namaMapel;
    private String namaGuru;
    private String hariJadwal;
    private LocalTime jamMulaiJadwal;
    private LocalTime jamSelesaiJadwal;

    public Presensi(int idPresensi, LocalDate tanggal, String status, String nis, int idJadwalKelas) {
        this.idPresensi = idPresensi;
        this.tanggal = tanggal;
        this.status = status;
        this.nis = nis;
        this.idJadwalKelas = idJadwalKelas;
    }

    // Constructor dengan nama-nama terkait
    public Presensi(int idPresensi, LocalDate tanggal, String status, String nis, String namaSiswa,
                    int idJadwalKelas, String namaKelas, String namaMapel, String namaGuru, String hariJadwal,
                    LocalTime jamMulaiJadwal, LocalTime jamSelesaiJadwal) {
        this.idPresensi = idPresensi;
        this.tanggal = tanggal;
        this.status = status;
        this.nis = nis;
        this.idJadwalKelas = idJadwalKelas;
        this.namaSiswa = namaSiswa;
        this.namaKelas = namaKelas;
        this.namaMapel = namaMapel;
        this.namaGuru = namaGuru;
        this.hariJadwal = hariJadwal;
        this.jamMulaiJadwal = jamMulaiJadwal;
        this.jamSelesaiJadwal = jamSelesaiJadwal;
    }

    // Getters and Setters
    public int getIdPresensi() {
        return idPresensi;
    }

    public void setIdPresensi(int idPresensi) {
        this.idPresensi = idPresensi;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public int getIdJadwalKelas() {
        return idJadwalKelas;
    }

    public void setIdJadwalKelas(int idJadwalKelas) {
        this.idJadwalKelas = idJadwalKelas;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
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

    public String getHariJadwal() {
        return hariJadwal;
    }

    public void setHariJadwal(String hariJadwal) {
        this.hariJadwal = hariJadwal;
    }

    public LocalTime getJamMulaiJadwal() {
        return jamMulaiJadwal;
    }

    public void setJamMulaiJadwal(LocalTime jamMulaiJadwal) {
        this.jamMulaiJadwal = jamMulaiJadwal;
    }

    public LocalTime getJamSelesaiJadwal() {
        return jamSelesaiJadwal;
    }

    public void setJamSelesaiJadwal(LocalTime jamSelesaiJadwal) {
        this.jamSelesaiJadwal = jamSelesaiJadwal;
    }
}
