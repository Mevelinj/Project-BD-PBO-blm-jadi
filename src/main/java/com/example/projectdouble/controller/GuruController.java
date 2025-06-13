package com.example.projectdouble.controller;

import com.example.projectdouble.DAO.*;
import com.example.projectdouble.model.*;
import com.example.projectdouble.util.SessionManager; // Pastikan ini di-import
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GuruController implements Initializable {

    //<editor-fold desc="DAO Instances and FXML Declarations">
    // ... (Tidak ada perubahan) ...
    private final GuruDAO guruDAO = new GuruDAO();
    private final UserDAO userDAO = new UserDAO();
    private final PengumumanDAO pengumumanDAO = new PengumumanDAO();
    private final JadwalKelasDAO jadwalKelasDAO = new JadwalKelasDAO();
    private final AgendaSekolahDAO agendaSekolahDAO = new AgendaSekolahDAO();
    private final KelasDAO kelasDAO = new KelasDAO();
    private final MataPelajaranDAO mataPelajaranDAO = new MataPelajaranDAO();
    private final SiswaDAO siswaDAO = new SiswaDAO();
    private final NilaiUjianDAO nilaiUjianDAO = new NilaiUjianDAO();
    private final TugasDAO tugasDAO = new TugasDAO();
    private final TahunAjaranDAO tahunAjaranDAO = new TahunAjaranDAO();
    private final PembinaDAO pembinaDAO = new PembinaDAO();
    private final PesertaEkskulDAO pesertaEkskulDAO = new PesertaEkskulDAO();
    private final PresensiDAO presensiDAO = new PresensiDAO();
    private final EkstrakurikulerDAO ekstrakurikulerDAO = new EkstrakurikulerDAO();

    @FXML private Label welcome;
    @FXML private Button logout;
    @FXML private TabPane tabPane;
    @FXML private TableView<Pengumuman> announcementsTable;
    @FXML private TableColumn<Pengumuman, String> announcementsTitleCol, announcementsDateCol, announcementsContentCol, announcementsAttachmentCol;
    @FXML private TextField biodataNipField, biodataNameField, biodataEmailField, biodataPhoneField;
    @FXML private TextField editBiodataNipField, editBiodataNameField, editBiodataEmailField, editBiodataPhoneField;
    @FXML private ComboBox<String> biodataGenderCombo, editBiodataGenderCombo;
    @FXML private TextField editPasswordUsernameField;
    @FXML private TextField editPasswordOldPassField, editPasswordNewPassField;
    @FXML private Button updateBiodataButton, changePasswordButton;
    @FXML private TableView<JadwalKelas> scheduleTable;
    @FXML private TableColumn<JadwalKelas, String> scheduleDayCol, scheduleStartCol, scheduleEndCol, scheduleSubjectCol, scheduleClassCol;
    @FXML private ComboBox<Kelas> inputScoreClassCombo;
    @FXML private ComboBox<MataPelajaran> inputScoreSubjectCombo;
    @FXML private ComboBox<String> inputScoreExamTypeCombo;
    @FXML private ComboBox<Siswa> inputScoreStudentCombo;
    @FXML private ComboBox<TahunAjaran> inputScoreSchoolYearCombo;
    @FXML private ComboBox<String> inputScoreSemesterCombo;
    @FXML private TextField inputScoreField, editScoreField;
    @FXML private Button inputScoreSubmitButton, updateScoreButton;
    @FXML private TableView<NilaiUjian> existingScoreTable;
    @FXML private TableColumn<NilaiUjian, String> existingScoreStudentCol, existingScoreExamTypeCol;
    @FXML private TableColumn<NilaiUjian, BigDecimal> existingScoreCol;
    @FXML private ComboBox<Kelas> assignmentClassCombo;
    @FXML private ComboBox<MataPelajaran> assignmentSubjectCombo;
    @FXML private ComboBox<TahunAjaran> assignmentSchoolYearCombo;
    @FXML private ComboBox<String> assignmentSemesterCombo;
    @FXML private TextField assignmentTitleField;
    @FXML private TextArea assignmentDescriptionArea;
    @FXML private DatePicker assignmentDeadlinePicker;
    @FXML private Button addAssignmentButton;
    @FXML private TableView<Tugas> assignmentTable;
    @FXML private TableColumn<Tugas, String> assignmentTitleCol, assignmentDescCol, assignmentDeadlineCol;
    @FXML private Tab homeroomTab;
    @FXML private ComboBox<TahunAjaran> homeroomSchoolYearCombo;
    @FXML private TextField homeroomClassField;
    @FXML private TableView<Siswa> homeroomStudentsTable;
    @FXML private TableColumn<Siswa, String> homeroomNisCol, homeroomStudentNameCol, homeroomGenderCol;
    @FXML private ComboBox<Siswa> homeroomAttendanceStudentCombo, homeroomRaporStudentCombo;
    @FXML private DatePicker homeroomAttendanceDatePicker;
    @FXML private ComboBox<String> homeroomAttendanceStatusCombo;
    @FXML private Button recordAttendanceButton;
    @FXML private TableView<Presensi> homeroomAttendanceTable;
    @FXML private TableColumn<Presensi, String> homeroomAttendanceDateCol, homeroomAttendanceStudentCol, homeroomAttendanceStatusCol, homeroomAttendanceClassCol;
    @FXML private Button homeroomPrintRaporButton;
    @FXML private ComboBox<TahunAjaran> agendaSchoolYearCombo;
    @FXML private ComboBox<String> agendaSemesterCombo;
    @FXML private TableView<AgendaSekolah> agendaTable;
    @FXML private TableColumn<AgendaSekolah, String> agendaContentCol, agendaStartCol, agendaEndCol;
    @FXML private Tab extracurricularTab;
    @FXML private TableView<Ekstrakurikuler> mentorExtracurricularTable;
    @FXML private TableColumn<Ekstrakurikuler, String> mentorExtraNameCol, mentorExtraLevelCol;
    @FXML private ComboBox<Ekstrakurikuler> mentorExtraSelectCombo;
    @FXML private Label mentorLevelLabel;
    @FXML private ComboBox<String> mentorLevelSelectCombo;
    @FXML private ComboBox<TahunAjaran> mentorSchoolYearCombo;
    @FXML private ComboBox<String> mentorSemesterCombo;
    @FXML private TableView<Siswa> mentorStudentTable;
    @FXML private TableColumn<Siswa, String> mentorStudentNisCol, mentorStudentNameCol, mentorStudentGenderCol, mentorStudentClassCol;
    //</editor-fold>

    private Guru currentTeacher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // PERBAIKAN: Menggunakan pola SessionManager untuk memuat data secara mandiri
        setupAllUIComponents();
        loadInitialData(); // Muat data umum terlebih dahulu

        User loggedInUser = SessionManager.getLoggedInUser();
        if (loggedInUser != null) {
            // Ambil username (NIP) dari sesi
            String username = loggedInUser.getUsername();

            // Cari detail guru berdasarkan NIP
            this.currentTeacher = guruDAO.getGuruByNip(username);

            if (this.currentTeacher != null) {
                // Set welcome text dengan NAMA LENGKAP guru
                welcome.setText("Welcome, " + currentTeacher.getNama() + "!");
                // Muat semua data yang spesifik untuk guru ini
                loadDataForGuru();
            } else {
                // Fallback jika NIP dari sesi tidak ditemukan di tabel guru
                showAlert(Alert.AlertType.ERROR, "Data Error", "Data detail untuk guru dengan NIP '" + username + "' tidak ditemukan.");
                welcome.setText("Welcome, " + username + "!");
            }
        } else {
            // Fallback jika tidak ada sesi (seharusnya tidak terjadi jika alur login benar)
            welcome.setText("Welcome, Guest!");
            showAlert(Alert.AlertType.WARNING, "Sesi Tidak Ditemukan", "Tidak dapat menemukan data user yang login.");
        }
    }

    // ... (Sisa kode sama persis dengan jawaban sebelumnya, sudah mencakup semua perbaikan)
    //<editor-fold defaultstate="collapsed" desc="Sisa Kode (Tidak Berubah)">
    private void loadDataForGuru() {
        if (currentTeacher == null) return;
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
        editPasswordOldPassField.setDisable(false);
        editPasswordNewPassField.setDisable(false);
        scheduleTable.setItems(FXCollections.observableArrayList(jadwalKelasDAO.getAllJadwalKelasDetails().stream().filter(j -> j.getNipGuru().equals(currentTeacher.getNip())).collect(Collectors.toList())));
        checkAndSetupConditionalTabs();
    }
    private void setupAllUIComponents() {
        setupTableColumns();
        populateStaticComboBoxes();
        addListeners();
    }
    private void setupTableColumns() {
        announcementsTitleCol.setCellValueFactory(new PropertyValueFactory<>("judul"));
        announcementsDateCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTanggal().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
        announcementsContentCol.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        announcementsAttachmentCol.setCellValueFactory(new PropertyValueFactory<>("lampiran"));
        scheduleDayCol.setCellValueFactory(new PropertyValueFactory<>("hari"));
        scheduleStartCol.setCellValueFactory(new PropertyValueFactory<>("jamMulai"));
        scheduleEndCol.setCellValueFactory(new PropertyValueFactory<>("jamSelesai"));
        scheduleSubjectCol.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        scheduleClassCol.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
        existingScoreStudentCol.setCellValueFactory(new PropertyValueFactory<>("namaSiswa"));
        existingScoreExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("jenisUjian"));
        existingScoreCol.setCellValueFactory(new PropertyValueFactory<>("nilai"));
        assignmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("judul"));
        assignmentDescCol.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        assignmentDeadlineCol.setCellValueFactory(new PropertyValueFactory<>("tanggalDeadline"));
        homeroomNisCol.setCellValueFactory(new PropertyValueFactory<>("nis"));
        homeroomStudentNameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        homeroomGenderCol.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        homeroomAttendanceDateCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        homeroomAttendanceStudentCol.setCellValueFactory(new PropertyValueFactory<>("namaSiswa"));
        homeroomAttendanceStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        homeroomAttendanceClassCol.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
        agendaContentCol.setCellValueFactory(new PropertyValueFactory<>("judul"));
        agendaStartCol.setCellValueFactory(new PropertyValueFactory<>("tanggalMulai"));
        agendaEndCol.setCellValueFactory(new PropertyValueFactory<>("tanggalSelesai"));
        mentorExtraNameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        mentorExtraLevelCol.setCellValueFactory(new PropertyValueFactory<>("tingkat"));
        mentorStudentNisCol.setCellValueFactory(new PropertyValueFactory<>("nis"));
        mentorStudentNameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        mentorStudentGenderCol.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        mentorStudentClassCol.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
    }
    private void populateStaticComboBoxes() {
        biodataGenderCombo.getItems().addAll("Laki-laki", "Perempuan");
        editBiodataGenderCombo.getItems().addAll("Laki-laki", "Perempuan");
        inputScoreExamTypeCombo.getItems().addAll("UAS", "UTS", "Kuis", "Tugas Harian");
        inputScoreSemesterCombo.getItems().addAll("Ganjil", "Genap");
        assignmentSemesterCombo.getItems().addAll("Ganjil", "Genap");
        agendaSemesterCombo.getItems().addAll("Ganjil", "Genap");
        mentorSemesterCombo.getItems().addAll("Ganjil", "Genap");
        homeroomAttendanceStatusCombo.getItems().addAll("Hadir", "Izin", "Sakit", "Alpa");
        mentorLevelSelectCombo.getItems().addAll("Siaga", "Penggalang");
        mentorLevelSelectCombo.setPromptText("Semua Level");
    }
    private void addListeners() {
        inputScoreClassCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                inputScoreStudentCombo.setItems(FXCollections.observableArrayList(siswaDAO.getStudentsInClass(newVal.getIdKelas(), newVal.getIdTahunAjaran(), newVal.getSemester())));
            }
        });
        agendaSchoolYearCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> filterSchoolAgenda());
        agendaSemesterCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> filterSchoolAgenda());
        mentorExtraSelectCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            boolean isPramuka = n != null && n.getNama().equalsIgnoreCase("Pramuka");
            mentorLevelLabel.setVisible(isPramuka);
            mentorLevelSelectCombo.setVisible(isPramuka);
            mentorLevelSelectCombo.setDisable(!isPramuka);
            if (!isPramuka) {
                mentorLevelSelectCombo.getSelectionModel().clearSelection();
            }
            filterMentorStudents();
        });
        mentorLevelSelectCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> filterMentorStudents());

        // Listener untuk validasi input skor secara real-time
        addNumericOnlyListener(inputScoreField);
        addNumericOnlyListener(editScoreField);
    }
    private void loadInitialData() {
        announcementsTable.setItems(FXCollections.observableArrayList(pengumumanDAO.getAllPengumuman()));
        agendaTable.setItems(FXCollections.observableArrayList(agendaSekolahDAO.getAllAgendaSekolah()));
        assignmentTable.setItems(FXCollections.observableArrayList(tugasDAO.getAllTugas()));
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
    }
    private void checkAndSetupConditionalTabs() {
        Kelas homeroomClass = kelasDAO.getAllKelas().stream().filter(k -> Objects.equals(k.getNipWaliKelas(), currentTeacher.getNip())).findFirst().orElse(null);
        homeroomTab.setDisable(homeroomClass == null);
        if (homeroomClass != null) {
            homeroomClassField.setText(homeroomClass.toString());
            List<Siswa> students = siswaDAO.getStudentsInClass(homeroomClass.getIdKelas(), homeroomClass.getIdTahunAjaran(), homeroomClass.getSemester());
            ObservableList<Siswa> studentList = FXCollections.observableArrayList(students);
            homeroomStudentsTable.setItems(studentList);
            homeroomAttendanceStudentCombo.setItems(studentList);
            homeroomRaporStudentCombo.setItems(studentList);
        }
        List<Ekstrakurikuler> mentoredEkskul = pembinaDAO.getAllPembina().stream().filter(p -> p.getNipGuru().equals(currentTeacher.getNip())).map(p -> ekstrakurikulerDAO.getEkstrakurikulerById(p.getIdEkstrakurikuler())).filter(Objects::nonNull).collect(Collectors.toList());
        extracurricularTab.setDisable(mentoredEkskul.isEmpty());
        if (!mentoredEkskul.isEmpty()) {
            mentorExtracurricularTable.setItems(FXCollections.observableArrayList(mentoredEkskul));
            mentorExtraSelectCombo.setItems(FXCollections.observableArrayList(mentoredEkskul));
        }
        mentorLevelLabel.setVisible(false);
        mentorLevelSelectCombo.setVisible(false);
        mentorLevelSelectCombo.setDisable(true);
    }
    private void filterMentorStudents() {
        Ekstrakurikuler selectedEkskul = mentorExtraSelectCombo.getValue();
        String selectedPramukaLevel = mentorLevelSelectCombo.getValue();
        if (selectedEkskul == null) {
            mentorStudentTable.getItems().clear();
            return;
        }
        List<Siswa> studentsInEkskul = pesertaEkskulDAO.getStudentsByExtracurricular(selectedEkskul.getIdEkstrakurikuler());
        if (selectedEkskul.getNama().equalsIgnoreCase("Pramuka") && selectedPramukaLevel != null) {
            final List<String> siagaGrades = Arrays.asList("1", "2", "3", "4");
            final List<String> penggalangGrades = Arrays.asList("5", "6", "7", "8", "9");
            List<Siswa> filteredStudents = studentsInEkskul.stream()
                    .filter(siswa -> {
                        if (siswa.getIdKelas() == null) return false;
                        Kelas kelasSiswa = kelasDAO.getKelasById(siswa.getIdKelas());
                        if (kelasSiswa == null || kelasSiswa.getTingkat() == null) return false;
                        String siswaTingkat = kelasSiswa.getTingkat();
                        if ("Siaga".equalsIgnoreCase(selectedPramukaLevel)) {
                            return siagaGrades.contains(siswaTingkat);
                        } else if ("Penggalang".equalsIgnoreCase(selectedPramukaLevel)) {
                            return penggalangGrades.contains(siswaTingkat);
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
            mentorStudentTable.setItems(FXCollections.observableArrayList(filteredStudents));
        } else {
            mentorStudentTable.setItems(FXCollections.observableArrayList(studentsInEkskul));
        }
    }
    private void filterSchoolAgenda() {
        TahunAjaran ta = agendaSchoolYearCombo.getValue();
        String semester = agendaSemesterCombo.getValue();
        if(ta != null && semester != null && !semester.isEmpty()) {
            agendaTable.setItems(FXCollections.observableArrayList(agendaSekolahDAO.getAgendaByTahunAjaranAndSemester(ta.getIdTahunAjaran(), semester)));
        } else {
            agendaTable.setItems(FXCollections.observableArrayList(agendaSekolahDAO.getAllAgendaSekolah()));
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void addNumericOnlyListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
    }
    @FXML void handleLogoutButtonAction(ActionEvent event) {
        try {
            SessionManager.clearSession();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectdouble/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logout.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat halaman login.");
        }
    }
    @FXML void handleUpdateBiodataButtonAction(ActionEvent event) {
        String nama = editBiodataNameField.getText();
        String noHp = editBiodataPhoneField.getText();
        if (nama.isEmpty() || editBiodataGenderCombo.getValue() == null || editBiodataEmailField.getText().isEmpty() || noHp.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Semua field pada Edit Biodata harus diisi.");
            return;
        }
        if (!nama.matches("[a-zA-Z\\s']+")) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nama hanya boleh mengandung huruf dan spasi.");
            return;
        }
        if (!noHp.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nomor telepon hanya boleh mengandung angka.");
            return;
        }
        Guru updatedGuru = new Guru(currentTeacher.getNip(), currentTeacher.getIdUser(), nama, editBiodataGenderCombo.getValue(), editBiodataEmailField.getText(), noHp, currentTeacher.getUsernameUser(), currentTeacher.getPasswordUser());
        if (guruDAO.updateGuru(updatedGuru)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data biodata berhasil diperbarui.");
            currentTeacher = updatedGuru;
            loadDataForGuru();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui data biodata di database.");
        }
    }
    @FXML void handleChangePasswordButtonAction(ActionEvent event) {
        showAlert(Alert.AlertType.WARNING, "Fitur Tidak Tersedia", "Fungsionalitas ganti password tidak dapat digunakan karena metode untuk mengubah password tidak ditemukan di UserDAO.");
    }
    @FXML void handleAddAssignmentButtonAction(ActionEvent event) {
        if (assignmentTitleField.getText().isEmpty() || assignmentDescriptionArea.getText().isEmpty() || assignmentDeadlinePicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Judul, Deskripsi, dan Deadline harus diisi.");
            return;
        }
        Tugas newTugas = new Tugas(0, assignmentTitleField.getText(), assignmentDescriptionArea.getText(), assignmentDeadlinePicker.getValue());
        if (tugasDAO.addTugas(newTugas)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Tugas baru berhasil ditambahkan (tanpa info kelas/mapel).");
            assignmentTable.setItems(FXCollections.observableArrayList(tugasDAO.getAllTugas()));
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menambahkan tugas ke database.");
        }
    }
    @FXML void handleRecordAttendanceButtonAction(ActionEvent event) {
        showAlert(Alert.AlertType.WARNING, "Fitur Tidak Tersedia", "Tidak bisa merekam absensi karena tidak ada cara untuk menentukan Jadwal Kelas spesifik dari UI ini. Diperlukan modifikasi pada UI atau Model Presensi.");
    }
    @FXML void handleSubmitScoreButtonAction(ActionEvent event) {
        String scoreText = inputScoreField.getText();
        if (scoreText.isEmpty() || !scoreText.matches("\\d*\\.?\\d*")) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nilai harus berupa angka yang valid.");
            return;
        }
        try {
            Kelas kelas = inputScoreClassCombo.getValue();
            MataPelajaran mapel = inputScoreSubjectCombo.getValue();
            Siswa siswa = inputScoreStudentCombo.getValue();
            String examType = inputScoreExamTypeCombo.getValue();
            if (kelas == null || mapel == null || siswa == null || examType == null) {
                showAlert(Alert.AlertType.WARNING, "Input Tidak Valid", "Pastikan semua field terisi dengan benar.");
                return;
            }
            BigDecimal score = new BigDecimal(scoreText);
            if (score.compareTo(BigDecimal.ZERO) < 0 || score.compareTo(new BigDecimal("100")) > 0) {
                showAlert(Alert.AlertType.WARNING, "Input Tidak Valid", "Nilai harus di antara 0 dan 100.");
                return;
            }
            NilaiUjian newNilai = new NilaiUjian(0, examType, score, kelas.getSemester(), mapel.getIdMapel(), siswa.getNis());
            if(nilaiUjianDAO.addNilaiUjian(newNilai)) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Nilai berhasil ditambahkan.");
                existingScoreTable.setItems(FXCollections.observableArrayList(nilaiUjianDAO.getAllNilaiUjian()));
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menambahkan nilai.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nilai yang dimasukkan bukan angka yang valid.");
        }
    }
    @FXML void handleUpdateScoreButtonAction(ActionEvent event) {
        NilaiUjian selectedNilai = existingScoreTable.getSelectionModel().getSelectedItem();
        if (selectedNilai == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih nilai dari tabel yang ingin diubah.");
            return;
        }
        String scoreText = editScoreField.getText();
        if (scoreText.isEmpty() || !scoreText.matches("\\d*\\.?\\d*")) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nilai harus berupa angka yang valid.");
            return;
        }
        try {
            BigDecimal newScore = new BigDecimal(scoreText);
            if (newScore.compareTo(BigDecimal.ZERO) < 0 || newScore.compareTo(new BigDecimal("100")) > 0) {
                showAlert(Alert.AlertType.WARNING, "Input Tidak Valid", "Nilai harus di antara 0 dan 100.");
                return;
            }
            selectedNilai.setNilai(newScore);
            if (nilaiUjianDAO.updateNilaiUjian(selectedNilai)) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Nilai berhasil diperbarui.");
                existingScoreTable.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui nilai.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nilai yang dimasukkan bukan angka yang valid.");
        }
    }
    @FXML void handleRefreshScheduleButtonAction(ActionEvent event) {
        scheduleTable.setItems(FXCollections.observableArrayList(jadwalKelasDAO.getAllJadwalKelasDetails().stream().filter(j -> j.getNipGuru().equals(currentTeacher.getNip())).collect(Collectors.toList())));
        showAlert(Alert.AlertType.INFORMATION, "Refreshed", "Jadwal telah dimuat ulang.");
    }
    @FXML void handlePrintRaporButtonAction(ActionEvent event) {
        if(homeroomRaporStudentCombo.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Siswa Belum Dipilih", "Silakan pilih siswa untuk mencetak rapor."); return;
        }
        showAlert(Alert.AlertType.INFORMATION, "Cetak Rapor", "Logika untuk mencetak rapor " + homeroomRaporStudentCombo.getValue().getNama() + " akan dijalankan di sini.");
    }
}