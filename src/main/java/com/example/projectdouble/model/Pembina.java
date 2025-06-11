package com.example.projectdouble.model;

public class Pembina {
    private int idPembina;
    private String nipGuru; // NIP Guru
    private int idEkstrakurikuler;

    // Field tambahan untuk kemudahan tampilan (diisi dari hasil JOIN di DAO)
    private String namaGuru;
    private String namaEkstrakurikuler;

    /**
     * Konstruktor untuk membuat objek Pembina baru (misalnya saat akan ditambahkan ke database).
     * id_pembina akan di-generate oleh database.
     * @param idPembina ID Pembina (gunakan 0 atau nilai placeholder jika auto-generated).
     * @param nipGuru NIP Guru yang menjadi pembina.
     * @param idEkstrakurikuler ID Ekstrakurikuler yang dibina.
     */
    public Pembina(int idPembina, String nipGuru, int idEkstrakurikuler) {
        this.idPembina = idPembina;
        this.nipGuru = nipGuru;
        this.idEkstrakurikuler = idEkstrakurikuler;
    }

    /**
     * Konstruktor lengkap untuk membuat objek Pembina (biasanya dari data yang diambil dari database).
     * @param idPembina ID Pembina.
     * @param nipGuru NIP Guru yang menjadi pembina.
     * @param namaGuru Nama Guru pembina (untuk tampilan).
     * @param idEkstrakurikuler ID Ekstrakurikuler yang dibina.
     * @param namaEkstrakurikuler Nama Ekstrakurikuler yang dibina (untuk tampilan).
     */
    public Pembina(int idPembina, String nipGuru, String namaGuru, int idEkstrakurikuler, String namaEkstrakurikuler) {
        this.idPembina = idPembina;
        this.nipGuru = nipGuru;
        this.namaGuru = namaGuru;
        this.idEkstrakurikuler = idEkstrakurikuler;
        this.namaEkstrakurikuler = namaEkstrakurikuler;
    }

    // --- Getters and Setters ---
    public int getIdPembina() {
        return idPembina;
    }

    public void setIdPembina(int idPembina) {
        this.idPembina = idPembina;
    }

    public String getNipGuru() {
        return nipGuru;
    }

    public void setNipGuru(String nipGuru) {
        this.nipGuru = nipGuru;
    }

    public int getIdEkstrakurikuler() {
        return idEkstrakurikuler;
    }

    public void setIdEkstrakurikuler(int idEkstrakurikuler) {
        this.idEkstrakurikuler = idEkstrakurikuler;
    }

    public String getNamaGuru() {
        return namaGuru;
    }

    public void setNamaGuru(String namaGuru) {
        this.namaGuru = namaGuru;
    }

    public String getNamaEkstrakurikuler() {
        return namaEkstrakurikuler;
    }

    public void setNamaEkstrakurikuler(String namaEkstrakurikuler) {
        this.namaEkstrakurikuler = namaEkstrakurikuler;
    }

    /**
     * Metode toString() untuk representasi string dari objek Pembina,
     * sangat berguna untuk menampilkan di ComboBox atau debug.
     */
    @Override
    public String toString() {
        if (namaGuru != null && namaEkstrakurikuler != null) {
            return "Pembina: " + namaGuru + " (Ekskul: " + namaEkstrakurikuler + ")";
        }
        return "ID Pembina: " + idPembina + ", NIP Guru: " + nipGuru + ", ID Ekskul: " + idEkstrakurikuler;
    }
}
