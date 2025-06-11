package com.example.projectdouble.controller;

public class GuruController {
}

        // Homeroom Attendance
        homeroomAttendanceDateCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        homeroomAttendanceStudentCol.setCellValueFactory(new PropertyValueFactory<>("namaSiswa"));
        homeroomAttendanceStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        homeroomAttendanceClassCol.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));

        // School Agenda
        agendaContentCol.setCellValueFactory(new PropertyValueFactory<>("judul"));
        agendaStartCol.setCellValueFactory(new PropertyValueFactory<>("tanggalMulai"));
        agendaEndCol.setCellValueFactory(new PropertyValueFactory<>("tanggalSelesai"));

        // Extracurricular Mentor - My Ekskul
        mentorExtraNameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        mentorExtraLevelCol.setCellValueFactory(new PropertyValueFactory<>("tingkat"));
        // Extracurricular Mentor - Student List
        mentorStudentNisCol.setCellValueFactory(new PropertyValueFactory<>("nis"));
        mentorStudentNameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        mentorStudentGenderCol.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        mentorStudentClassCol.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
    }

    private void populateStaticComboBoxes() {
        // ... (Kode populateStaticComboBoxes sama seperti jawaban sebelumnya) ...
        biodataGenderCombo.getItems().addAll("Laki-laki", "Perempuan");
        editBiodataGenderCombo.getItems().addAll("Laki-laki", "Perempuan");
        inputScoreExamTypeCombo.getItems().addAll("UAS", "UTS", "Kuis", "Tugas Harian");
        inputScoreSemesterCombo.getItems().addAll("Ganjil", "Genap");
        assignmentSemesterCombo.getItems().addAll("Ganjil", "Genap");
        agendaSemesterCombo.getItems().addAll("Ganjil", "Genap");
        mentorSemesterCombo.getItems().addAll("Ganjil", "Genap");
        homeroomAttendanceStatusCombo.getItems().addAll("Hadir", "Izin", "Sakit", "Alpa");
    }

    private void addListeners() {
        // ... (Kode addListeners sama seperti jawaban sebelumnya) ...
        inputScoreClassCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                List<Siswa> students = siswaDAO.getStudentsInClass(newVal.getIdKelas(), newVal.getIdTahunAjaran(), newVal.getSemester());
                inputScoreStudentCombo.setItems(FXCollections.observableArrayList(students));
            }
        });
        agendaSchoolYearCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> filterSchoolAgenda());
        agendaSemesterCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> filterSchoolAgenda());
        mentorExtraSelectCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                mentorStudentTable.setItems(FXCollections.observableArrayList(pesertaEkskulDAO.getStudentsByExtracurricular(n.getIdEkstrakurikuler())));
            }
        });
    }

    private void loadAllDataForTeacher() {
        // ... (Kode loadAllDataForTeacher sama seperti jawaban sebelumnya) ...
        // Load Biodata
        biodataNipField.setText(currentTeacher.getNip());
        biodataNameField.setText(currentTeacher.getNama());
        biodataGenderCombo.setValue(currentTeacher.getJenisKelamin());
        biodataEmailField.setText(currentTeacher.getEmail());
        biodataPhoneField.setText(currentTeacher.getNoHp());
        editBiodataNipField.setText(currentTeacher.getNip());
        editBiodataNameField.setText(currentTeacher.getNama());
        editBiodataGenderCombo.setValue(currentTeacher.getJenisKelamin());
        editBiodataEmailField.setText(currentTeacher.getEmail());
        editBiodataPhoneField.setText(currentTeacher.getNoHp());
        editPasswordUsernameField.setText(currentTeacher.getUsernameUser());

        // Load Announcements
        pengumumanList.setAll(pengumumanDAO.getAllPengumuman());
        announcementsTable.setItems(pengumumanList);

        // Load My Schedule
        List<JadwalKelas> allSchedules = jadwalKelasDAO.getAllJadwalKelasDetails();
        jadwalGuruList.setAll(allSchedules.stream()
                .filter(j -> j.getNipGuru().equals(currentTeacher.getNip()))
                .collect(Collectors.toList()));
        scheduleTable.setItems(jadwalGuruList);

        // Load data for ComboBoxes
        ObservableList<Kelas> allClasses = FXCollections.observableArrayList(kelasDAO.getAllKelas());
        ObservableList<MataPelajaran> allSubjects = FXCollections.observableArrayList(mataPelajaranDAO.getAllMataPelajaran());
        ObservableList<TahunAjaran> allSchoolYears = FXCollections.observableArrayList(tahunAjaranDAO.getAllTahunAjaran());

        inputScoreClassCombo.setItems(allClasses);
        inputScoreSubjectCombo.setItems(allSubjects);
        inputScoreSchoolYearCombo.setItems(allSchoolYears);
        assignmentClassCombo.setItems(allClasses);
        assignmentSubjectCombo.setItems(allSubjects);
        assignmentSchoolYearCombo.setItems(allSchoolYears);
        agendaSchoolYearCombo.setItems(allSchoolYears);
        homeroomSchoolYearCombo.setItems(allSchoolYears);
        mentorSchoolYearCombo.setItems(allSchoolYears);

        // Load other tables
        agendaSekolahList.setAll(agendaSekolahDAO.getAllAgendaSekolah());
        agendaTable.setItems(agendaSekolahList);
        tugasList.setAll(tugasDAO.getAllTugas());
        assignmentTable.setItems(tugasList);

        checkAndSetupRoleBasedTabs();
    }

    private void checkAndSetupRoleBasedTabs() {
        // ... (Kode checkAndSetupRoleBasedTabs sama seperti jawaban sebelumnya) ...
        // Homeroom Teacher Check
        Kelas homeroomClass = kelasDAO.getAllKelas().stream()
                .filter(k -> k.getNipWaliKelas() != null && k.getNipWaliKelas().equals(currentTeacher.getNip()))
                .findFirst().orElse(null);

        homeroomTab.setDisable(homeroomClass == null);
        if (homeroomClass != null) {
            homeroomClassField.setText(homeroomClass.toString());
            List<Siswa> students = siswaDAO.getStudentsInClass(homeroomClass.getIdKelas(), homeroomClass.getIdTahunAjaran(), homeroomClass.getSemester());
            homeroomStudentsTable.setItems(FXCollections.observableArrayList(students));
            homeroomAttendanceStudentCombo.setItems(FXCollections.observableArrayList(students));
            homeroomRaporStudentCombo.setItems(FXCollections.observableArrayList(students));
        }

        // Extracurricular Mentor Check
        List<Ekstrakurikuler> mentoredEkskul = pembinaDAO.getAllPembina().stream()
                .filter(p -> p.getNipGuru().equals(currentTeacher.getNip()))
                .map(p -> ekstrakurikulerDAO.getEkstrakurikulerById(p.getIdEkstrakurikuler()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        extracurricularTab.setDisable(mentoredEkskul.isEmpty());
        if (!mentoredEkskul.isEmpty()) {
            mentorExtracurricularTable.setItems(FXCollections.observableArrayList(mentoredEkskul));
            mentorExtraSelectCombo.setItems(FXCollections.observableArrayList(mentoredEkskul));
        }
    }
    //</editor-fold>

    //<editor-fold desc="Action Handlers with Full Logic">
    @FXML
    void handleLogoutButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Apakah Anda yakin ingin logout?", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Konfirmasi Logout");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ((Stage) logout.getScene().getWindow()).close();
        }
    }

    @FXML
    void handleUpdateBiodataButtonAction(ActionEvent event) {
        Guru updatedGuru = new Guru(
                currentTeacher.getNip(), currentTeacher.getIdUser(), editBiodataNameField.getText(),
                editBiodataGenderCombo.getValue(), editBiodataEmailField.getText(), editBiodataPhoneField.getText(),
                currentTeacher.getUsernameUser(), currentTeacher.getPasswordUser()
        );
        if (guruDAO.updateGuru(updatedGuru)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data biodata berhasil diperbarui.");
            currentTeacher = updatedGuru;
            loadAllDataForTeacher();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui data biodata.");
        }
    }

    @FXML
    void handleChangePasswordButtonAction(ActionEvent event) {
        String username = editPasswordUsernameField.getText();
        String oldPassword = editPasswordOldPassField.getText();
        String newPassword = editPasswordNewPassField.getText();

        if (username.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Semua field harus diisi.");
            return;
        }

        User user = userDAO.authenticateUser(username, oldPassword, "guru");
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Autentikasi Gagal", "Username atau password lama salah.");
            return;
        }

        // Karena UserDAO Anda tidak punya updatePassword, kita panggil updateUsername sebagai placeholder.
        // Anda HARUS menambahkan metode updatePassword di UserDAO. (Lihat Bagian 3 di bawah)
        boolean success = userDAO.updatePassword(user.getIdUser(), newPassword); // Ganti ini setelah Anda menambahkannya

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Password berhasil diubah.");
            editPasswordOldPassField.clear();
            editPasswordNewPassField.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal mengubah password di database.");
        }
    }

    @FXML
    void handleSubmitScoreButtonAction(ActionEvent event) {
        try {
            Kelas kelas = inputScoreClassCombo.getValue();
            MataPelajaran mapel = inputScoreSubjectCombo.getValue();
            Siswa siswa = inputScoreStudentCombo.getValue();
            String examType = inputScoreExamTypeCombo.getValue();

            if (kelas == null || mapel == null || siswa == null || examType == null || inputScoreField.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Tidak Valid", "Pastikan semua field terisi dengan benar.");
                return;
            }
            String semester = kelas.getSemester();
            BigDecimal score = new BigDecimal(inputScoreField.getText());
            if (score.doubleValue() < 0 || score.doubleValue() > 100) {
                showAlert(Alert.AlertType.WARNING, "Input Tidak Valid", "Nilai harus antara 0 dan 100.");
                return;
            }

            NilaiUjian newNilai = new NilaiUjian(0, examType, score, semester, mapel.getIdMapel(), siswa.getNis());
            if(nilaiUjianDAO.addNilaiUjian(newNilai)) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Nilai berhasil ditambahkan.");
                inputScoreField.clear();
                // Refresh table
                existingScoreTable.setItems(FXCollections.observableArrayList(nilaiUjianDAO.getNilaiUjianByGuru(currentTeacher.getNip())));
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menambahkan nilai ke database.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nilai harus berupa angka.");
        }
    }

    @FXML
    void handleAddAssignmentButtonAction(ActionEvent event) {
        if (assignmentTitleField.getText().isEmpty() || assignmentDescriptionArea.getText().isEmpty() || assignmentDeadlinePicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Judul, Deskripsi, dan Deadline harus diisi.");
            return;
        }
        Tugas newTugas = new Tugas(0, assignmentTitleField.getText(), assignmentDescriptionArea.getText(), assignmentDeadlinePicker.getValue());
        if (tugasDAO.addTugas(newTugas)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Tugas baru berhasil ditambahkan.");
            tugasList.setAll(tugasDAO.getAllTugas()); // Refresh
            assignmentTitleField.clear();
            assignmentDescriptionArea.clear();
            assignmentDeadlinePicker.setValue(null);
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menambahkan tugas ke database.");
        }
    }

    @FXML
    void handleRecordAttendanceButtonAction(ActionEvent event) {
        Siswa selectedSiswa = homeroomAttendanceStudentCombo.getValue();
        if (selectedSiswa == null || homeroomAttendanceDatePicker.getValue() == null || homeroomAttendanceStatusCombo.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Pilih siswa, tanggal, dan status.");
            return;
        }

        // Catatan: Presensi memerlukan id_jadwal_kelas. Logika ini sangat disederhanakan.
        // Asumsi: kita mengambil jadwal pertama untuk kelas wali di hari itu.
        int idJadwalKelasPlaceholder = 1; // Ganti dengan logika pencarian jadwal yang lebih baik jika perlu

        Presensi presensi = new Presensi(0, homeroomAttendanceDatePicker.getValue(), homeroomAttendanceStatusCombo.getValue(), selectedSiswa.getNis(), idJadwalKelasPlaceholder);
        if (presensiDAO.addPresensi(presensi)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Absensi untuk " + selectedSiswa.getNama() + " berhasil direkam.");
            homeroomAttendanceTable.setItems(FXCollections.observableArrayList(presensiDAO.getAllPresensi()));
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal merekam absensi ke database.");
        }
    }

    @FXML
    void handlePrintRaporButtonAction(ActionEvent event) {
        Siswa selectedSiswa = homeroomRaporStudentCombo.getValue();
        if(selectedSiswa == null) {
            showAlert(Alert.AlertType.WARNING, "Siswa Belum Dipilih", "Silakan pilih siswa untuk mencetak rapor.");
            return;
        }
        // Di sini Anda akan memanggil library atau class khusus untuk generate PDF/laporan
        // 1. Kumpulkan semua data yang diperlukan (biodata siswa, nilai, absensi, dll)
        // 2. Pass data tersebut ke service/class report generator Anda.
        showAlert(Alert.AlertType.INFORMATION, "Cetak Rapor", "Logika untuk mencetak rapor " + selectedSiswa.getNama() + " akan dijalankan di sini.\nData siap untuk diproses oleh JasperReports atau library lainnya.");
    }

    @FXML
    void handleRefreshScheduleButtonAction(ActionEvent event) {
        jadwalGuruList.setAll(jadwalKelasDAO.getAllJadwalKelasDetails().stream()
                .filter(j -> j.getNipGuru().equals(currentTeacher.getNip()))
                .collect(Collectors.toList()));
        showAlert(Alert.AlertType.INFORMATION, "Refreshed", "Jadwal telah dimuat ulang.");
    }

    @FXML
    void handleUpdateScoreButtonAction(ActionEvent event) {
        NilaiUjian selectedNilai = existingScoreTable.getSelectionModel().getSelectedItem();
        if (selectedNilai == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih nilai yang ingin diubah dari tabel.");
            return;
        }
        try {
            BigDecimal newScore = new BigDecimal(editScoreField.getText());
            selectedNilai.setNilai(newScore);
            if (nilaiUjianDAO.updateNilaiUjian(selectedNilai)) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Nilai berhasil diperbarui.");
                existingScoreTable.refresh();
                editScoreField.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui nilai di database.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nilai harus berupa angka.");
        }
    }

    //</editor-fold>

    private void filterSchoolAgenda() {
        // ... (Kode filterSchoolAgenda sama seperti jawaban sebelumnya) ...
        TahunAjaran ta = agendaSchoolYearCombo.getValue();
        String semester = agendaSemesterCombo.getValue();
        if(ta != null && semester != null && !semester.isEmpty()) {
            agendaTable.setItems(FXCollections.observableArrayList(agendaSekolahDAO.getAgendaByTahunAjaranAndSemester(ta.getIdTahunAjaran(), semester)));
        } else {
            agendaTable.setItems(FXCollections.observableArrayList(agendaSekolahDAO.getAllAgendaSekolah()));
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        // ... (Kode showAlert sama seperti jawaban sebelumnya) ...
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}