package com.example.projectdouble.model;

public class Kelas {
    private int idKelas;
    private String namaKelas;
    private String tingkat;

    public Kelas(int idKelas, String namaKelas, String tingkat) {
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.tingkat = tingkat;
    }

    // Getters and Setters
    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    @Override
    public String toString() {
        return namaKelas + " (" + tingkat + ")";
    }
}
