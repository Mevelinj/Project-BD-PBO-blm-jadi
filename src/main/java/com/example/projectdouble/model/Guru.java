package com.example.projectdouble.model;

import com.example.projectdouble.DAO.UserDAO;

public class Guru {
    private String nip;
    private Integer idUser; // Gunakan Integer agar bisa null
    private String nama;
    private String jenisKelamin;
    private String email;
    private String noHp;
    private String usernameUser; // Untuk memudahkan data terkait user
    private String passwordUser; // Untuk memudahkan data terkait user (meskipun tidak di-masking)

    public Guru(String nip, String nama, String jenisKelamin, String email, String noHp) {
        this.nip = nip;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.email = email;
        this.noHp = noHp;
        this.idUser = null;
        this.usernameUser = null;
        this.passwordUser = null;
    }

    public Guru(String nip, Integer idUser, String nama, String jenisKelamin, String email, String noHp, String usernameUser, String passwordUser) {
        this.nip = nip;
        this.idUser = idUser;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.email = email;
        this.noHp = noHp;
        this.usernameUser = usernameUser;
        this.passwordUser = passwordUser;
    }

    // Getters and Setters
    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
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

    @Override
    public String toString() {
        return nama + " (" + nip + ")"; // Berguna untuk ComboBox di FXML
    }
}
