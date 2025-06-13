package com.example.projectdouble.model;

public class User {
    private int idUser;
    private String username;
    private String password; // Perhatian: untuk produksi, simpan hash password, bukan plainteks
    private Role role; // Objek Role untuk menyimpan id_role dan nama_role
    private String displayName;

    public User(int idUser, String username, String password, Role role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
        this.displayName=username;
    }

    // Konstruktor tanpa idUser (untuk saat membuat user baru sebelum id dihasilkan database)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    // Setters
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return username; // Digunakan misalnya jika objek User ditampilkan di ComboBox
    }
}
