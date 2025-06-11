package com.example.projectdouble.model;

import com.example.projectdouble.DAO.GuruDAO;
import com.example.projectdouble.DAO.TahunAjaranDAO;

public class Kelas {
    private int idKelas;
    private String namaKelas;
    private String tingkat;
    private String nipWaliKelas; // Kunci asing ke Guru
    private String namaWaliKelas; // Untuk memudahkan tampilan
    private int idTahunAjaran; // Kunci asing ke TahunAjaran
    private String tahunAjaranLengkap; // Untuk memudahkan tampilan (misal: "2023/2024")
    private String semester; // 'Ganjil' atau 'Genap'

    public Kelas(int idKelas, String namaKelas, String tingkat, String nipWaliKelas, int idTahunAjaran) {
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.tingkat = tingkat;
        this.nipWaliKelas = nipWaliKelas;
        this.idTahunAjaran = idTahunAjaran;
    }

    public Kelas(int idKelas, String namaKelas, String tingkat, String nipWaliKelas, String namaWaliKelas, int idTahunAjaran, String tahunAjaranLengkap) {
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.tingkat = tingkat;
        this.nipWaliKelas = nipWaliKelas;
        this.namaWaliKelas = namaWaliKelas;
        this.idTahunAjaran = idTahunAjaran;
        this.tahunAjaranLengkap = tahunAjaranLengkap;
    }

    public Kelas(int idKelas, String namaKelas, String tingkat, String nipWaliKelas, String namaWaliKelas, int idTahunAjaran, String tahunAjaranLengkap, String semester) {
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.tingkat = tingkat;
        this.nipWaliKelas = nipWaliKelas;
        this.namaWaliKelas = namaWaliKelas;
        this.idTahunAjaran = idTahunAjaran;
        this.tahunAjaranLengkap = tahunAjaranLengkap;
        this.semester = semester;
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

    public String getNipWaliKelas() {
        return nipWaliKelas;
    }

    public void setNipWaliKelas(String nipWaliKelas) {
        this.nipWaliKelas = nipWaliKelas;
    }

    public String getNamaWaliKelas() {
        return namaWaliKelas;
    }

    public void setNamaWaliKelas(String namaWaliKelas) {
        this.namaWaliKelas = namaWaliKelas;
    }

    public int getIdTahunAjaran() {
        return idTahunAjaran;
    }

    public void setIdTahunAjaran(int idTahunAjaran) {
        this.idTahunAjaran = idTahunAjaran;
    }

    public String getTahunAjaranLengkap() {
        return tahunAjaranLengkap;
    }

    public void setTahunAjaranLengkap(String tahunAjaranLengkap) {
        this.tahunAjaranLengkap = tahunAjaranLengkap;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        // Untuk ComboBox di FXML, tampilkan nama kelas dan tahun ajaran
        if (namaKelas != null && tahunAjaranLengkap != null) {
            return namaKelas + " (" + tahunAjaranLengkap + ")";
        }
        return namaKelas; // Fallback
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Kelas kelas = (Kelas) obj;
        return idKelas == kelas.idKelas;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idKelas);
    }
}
