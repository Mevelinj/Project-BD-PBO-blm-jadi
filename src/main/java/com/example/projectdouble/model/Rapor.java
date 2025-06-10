package com.example.projectdouble.model;

import java.time.LocalDate;

public class Rapor {
    private int idRapor;
    private String nis;
    private String semester;
    private LocalDate tanggalCetak;

    public Rapor(int idRapor, String nis, String semester, LocalDate tanggalCetak) {
        this.idRapor = idRapor;
        this.nis = nis;
        this.semester = semester;
        this.tanggalCetak = tanggalCetak;
    }

    // Getters and Setters
    public int getIdRapor() {
        return idRapor;
    }

    public void setIdRapor(int idRapor) {
        this.idRapor = idRapor;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public LocalDate getTanggalCetak() {
        return tanggalCetak;
    }

    public void setTanggalCetak(LocalDate tanggalCetak) {
        this.tanggalCetak = tanggalCetak;
    }
}
