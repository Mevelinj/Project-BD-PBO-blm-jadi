package com.example.projectdouble.model;

public class TahunAjaran {
    private int idTahunAjaran;
    private int tahunMulai;
    private int tahunSelesai;
    private String tahunGanjilGenap; // Contoh: 'Ganjil', 'Genap'

    //hapus tahunganjilgenapnya
    public TahunAjaran(int idTahunAjaran, int tahunMulai, int tahunSelesai ) {
        this.idTahunAjaran = idTahunAjaran;
        this.tahunMulai = tahunMulai;
        this.tahunSelesai = tahunSelesai;
        //this.tahunGanjilGenap = tahunGanjilGenap;
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

//    public String getTahunGanjilGenap() {
//        return tahunGanjilGenap;
//    }

//    public void setTahunGanjilGenap(String tahunGanjilGenap) {
//        this.tahunGanjilGenap = tahunGanjilGenap;
//    }

    public String getTahunLengkap() {
        return tahunMulai + "/" + tahunSelesai;
    }

    @Override
    public String toString() {
        return tahunMulai + "/" + tahunSelesai ; // Penting untuk ComboBox
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TahunAjaran that = (TahunAjaran) obj;
        return idTahunAjaran == that.idTahunAjaran;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idTahunAjaran);
    }
}
