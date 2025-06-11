package com.example.projectdouble.model;

public class MataPelajaran {
    private int idMapel;
    private String namaMapel;
    private String jenjangKelas;

    public MataPelajaran(int idMapel, String namaMapel, String jenjangKelas) {
        this.idMapel = idMapel;
        this.namaMapel = namaMapel;
        this.jenjangKelas = jenjangKelas;
    }

    // Getters and Setters
    public int getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(int idMapel) {
        this.idMapel = idMapel;
    }

    public String getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }

    public String getJenjangKelas() {
        return jenjangKelas;
    }

    public void setJenjangKelas(String jenjangKelas) {
        this.jenjangKelas = jenjangKelas;
    }

    @Override
    public String toString() {
        return namaMapel + " (" + jenjangKelas + ")"; // Penting untuk ComboBox Mata Pelajaran
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MataPelajaran that = (MataPelajaran) obj;
        return idMapel == that.idMapel;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idMapel);
    }
}
