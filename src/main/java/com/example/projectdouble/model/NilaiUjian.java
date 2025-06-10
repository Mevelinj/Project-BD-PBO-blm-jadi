package com.example.projectdouble.model;

import java.math.BigDecimal;

public class NilaiUjian {
    private int idNilaiUjian;
    private String jenisUjian;
    private BigDecimal nilai; // Menggunakan BigDecimal untuk NUMERIC
    private String semester;
    private int idMapel;
    private String nis;

    // Untuk memudahkan tampilan
    private String namaMapel;
    private String namaSiswa;

    public NilaiUjian(int idNilaiUjian, String jenisUjian, BigDecimal nilai, String semester, int idMapel, String nis) {
        this.idNilaiUjian = idNilaiUjian;
        this.jenisUjian = jenisUjian;
        this.nilai = nilai;
        this.semester = semester;
        this.idMapel = idMapel;
        this.nis = nis;
    }

    // Constructor dengan nama-nama terkait
    public NilaiUjian(int idNilaiUjian, String jenisUjian, BigDecimal nilai, String semester, int idMapel, String nis, String namaMapel, String namaSiswa) {
        this.idNilaiUjian = idNilaiUjian;
        this.jenisUjian = jenisUjian;
        this.nilai = nilai;
        this.semester = semester;
        this.idMapel = idMapel;
        this.nis = nis;
        this.namaMapel = namaMapel;
        this.namaSiswa = namaSiswa;
    }

    // Getters and Setters
    public int getIdNilaiUjian() {
        return idNilaiUjian;
    }

    public void setIdNilaiUjian(int idNilaiUjian) {
        this.idNilaiUjian = idNilaiUjian;
    }

    public String getJenisUjian() {
        return jenisUjian;
    }

    public void setJenisUjian(String jenisUjian) {
        this.jenisUjian = jenisUjian;
    }

    public BigDecimal getNilai() {
        return nilai;
    }

    public void setNilai(BigDecimal nilai) {
        this.nilai = nilai;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(int idMapel) {
        this.idMapel = idMapel;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }
}
