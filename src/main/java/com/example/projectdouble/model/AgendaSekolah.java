package com.example.projectdouble.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AgendaSekolah {
    private int idAgendaSekolah;
    private String judul;
    private String deskripsi; // Mungkin tidak terpakai jika hanya ada judul, start, end
    private LocalDate tanggalMulai;
    private LocalDate tanggalSelesai;
    private Integer idTahunAjaran; // Integer agar bisa null
    private String semester;

    // Untuk memudahkan tampilan
    private String tahunAjaranLengkap;

    public AgendaSekolah(int idAgendaSekolah, String judul, String deskripsi, LocalDate tanggalMulai, LocalDate tanggalSelesai, Integer idTahunAjaran, String semester) {
        this.idAgendaSekolah = idAgendaSekolah;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.idTahunAjaran = idTahunAjaran;
        this.semester = semester;
    }

    // Constructor lengkap untuk mengambil dari DB (dengan join untuk tahunAjaranLengkap)
    public AgendaSekolah(int idAgendaSekolah, String judul, String deskripsi, LocalDate tanggalMulai, LocalDate tanggalSelesai, Integer idTahunAjaran, String tahunAjaranLengkap, String semester) {
        this.idAgendaSekolah = idAgendaSekolah;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.idTahunAjaran = idTahunAjaran;
        this.tahunAjaranLengkap = tahunAjaranLengkap;
        this.semester = semester;
    }


    // Getters and Setters
    public int getIdAgendaSekolah() { return idAgendaSekolah; }
    public void setIdAgendaSekolah(int idAgendaSekolah) { this.idAgendaSekolah = idAgendaSekolah; }
    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public LocalDate getTanggalMulai() { return tanggalMulai; }
    public void setTanggalMulai(LocalDate tanggalMulai) { this.tanggalMulai = tanggalMulai; }
    public LocalDate getTanggalSelesai() { return tanggalSelesai; }
    public void setTanggalSelesai(LocalDate tanggalSelesai) { this.tanggalSelesai = tanggalSelesai; }
    public Integer getIdTahunAjaran() { return idTahunAjaran; }
    public void setIdTahunAjaran(Integer idTahunAjaran) { this.idTahunAjaran = idTahunAjaran; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public String getTahunAjaranLengkap() { return tahunAjaranLengkap; }
    public void setTahunAjaranLengkap(String tahunAjaranLengkap) { this.tahunAjaranLengkap = tahunAjaranLengkap; }

    @Override
    public String toString() {
        // Format tampilan agenda, misalnya "Judul: [Tanggal Mulai] - [Tanggal Selesai]"
        return judul + " (" + tanggalMulai.format(DateTimeFormatter.ofPattern("dd MMM")) + " - " + tanggalSelesai.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + ")";
    }
}
