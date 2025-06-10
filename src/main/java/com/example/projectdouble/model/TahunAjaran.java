package com.example.projectdouble.model;

public class TahunAjaran {
    private int idTahunAjaran;
    private int tahunMulai;
    private int tahunSelesai;
    private String tahunGanjilGenap;

    public TahunAjaran(int idTahunAjaran, int tahunMulai, int tahunSelesai, String tahunGanjilGenap) {
        this.idTahunAjaran = idTahunAjaran;
        this.tahunMulai = tahunMulai;
        this.tahunSelesai = tahunSelesai;
        this.tahunGanjilGenap = tahunGanjilGenap;
    }

    // Getters and Setters
    public int getIdTahunAjaran() {
        return idTahunAjaran;
    }

    public void setIdTahunAjaran(int idTahunAjaran) {
        this.idTahunAjaran = idTahunAjaran;
    }

    public int getTahunMulai() {
        return tahunMulai;
    }

    public void setTahunMulai(int tahunMulai) {
        this.tahunMulai = tahunMulai;
    }

    public int getTahunSelesai() {
        return tahunSelesai;
    }

    public void setTahunSelesai(int tahunSelesai) {
        this.tahunSelesai = tahunSelesai;
    }

    public String getTahunGanjilGenap() {
        return tahunGanjilGenap;
    }

    public void setTahunGanjilGenap(String tahunGanjilGenap) {
        this.tahunGanjilGenap = tahunGanjilGenap;
    }

    @Override
    public String toString() {
        return tahunMulai + "/" + tahunSelesai + " - " + tahunGanjilGenap;
    }
}
