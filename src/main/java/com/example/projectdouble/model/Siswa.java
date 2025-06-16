package com.example.projectdouble.model;

import com.example.projectdouble.DAO.UserDAO;

import java.time.LocalDate;

public class Siswa {
    private String nis;
    private Integer idUser; // Gunakan Integer agar bisa null
    private String nama;
    private String jenisKelamin;
    private String tempatLahir;
    private LocalDate tanggalLahir; // Menggunakan LocalDate untuk tanggal
    private String alamat;
    private String usernameUser; // Untuk memudahkan data terkait user
    private String passwordUser; // Untuk memudahkan data terkait user (meskipun tidak di-masking)

    // Foreign Keys untuk Kelas dan Tahun Ajaran
    private Integer idKelas; // Bisa null jika belum di-assign ke kelas
    private String namaKelas; // Untuk tampilan
    private Integer idTahunAjaran; // Bisa null jika belum di-assign ke kelas
    private String tahunAjaranLengkap; // Untuk tampilan (misal: "2023/2024 Ganjil")
    //private String semester; // Bisa null jika belum di-assign ke kelas


    // Konstruktor untuk data siswa dasar (saat input awal)
    public Siswa(String nis, String nama, String jenisKelamin, String tempatLahir, LocalDate tanggalLahir, String alamat) {
        this.nis = nis;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.idUser = null; // Default null saat pembuatan awal
        this.usernameUser = null;
        this.passwordUser = null;
        this.idKelas = null;
        this.namaKelas = null;
        this.idTahunAjaran = null;
        this.tahunAjaranLengkap = null;
        //this.semester = null;
    }

    // Konstruktor lengkap dengan semua field, termasuk FK dan user info
    public Siswa(String nis, String nama, String jenisKelamin, String tempatLahir, LocalDate tanggalLahir, String alamat,
                 Integer idKelas, String namaKelas, Integer idTahunAjaran, String tahunAjaranLengkap, // String semester,
                 Integer idUser, String usernameUser, String passwordUser) {
        this.nis = nis;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.idTahunAjaran = idTahunAjaran;
        this.tahunAjaranLengkap = tahunAjaranLengkap;
        //this.semester = semester;
        this.idUser = idUser;
        this.usernameUser = usernameUser;
        this.passwordUser = passwordUser;
    }

    // Konstruktor untuk DAO yang hanya mengambil sebagian data
    public Siswa(String nis, String nama, String jenisKelamin, String tempatLahir, LocalDate tanggalLahir, String alamat, Integer idUser, String usernameUser, String passwordUser) {
        this.nis = nis;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.idUser = idUser;
        this.usernameUser = usernameUser;
        this.passwordUser = passwordUser;
    }

    // Konstruktor untuk DAO yang hanya mengambil data SISWA dan KELAS
    public Siswa(String nis, String nama, String jenisKelamin, String tempatLahir, LocalDate tanggalLahir, String alamat,
                 Integer idKelas, String namaKelas, Integer idTahunAjaran, String tahunAjaranLengkap) {// String semester
        this.nis = nis;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.idTahunAjaran = idTahunAjaran;
        this.tahunAjaranLengkap = tahunAjaranLengkap;
        //this.semester = semester;
    }


    // Getters and Setters
    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        this.usernameUser = usernameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Integer getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(Integer idKelas) {
        this.idKelas = idKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public Integer getIdTahunAjaran() {
        return idTahunAjaran;
    }

    public void setIdTahunAjaran(Integer idTahunAjaran) {
        this.idTahunAjaran = idTahunAjaran;
    }

    public String getTahunAjaranLengkap() {
        return tahunAjaranLengkap;
    }

    public void setTahunAjaranLengkap(String tahunAjaranLengkap) {
        this.tahunAjaranLengkap = tahunAjaranLengkap;
    }

//    public String getSemester() {
//        return semester;
//    }

//    public void setSemester(String semester) {
//        this.semester = semester;
//    }

    @Override
    public String toString() {
        return nama + " (" + nis + ")"; // Penting untuk ComboBox Siswa
    }
}
