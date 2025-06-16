package com.example.projectdouble.controller;

import com.example.projectdouble.DAO.*;
import com.example.projectdouble.model.*;
import com.example.projectdouble.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SiswaController implements Initializable {

    //<editor-fold desc="DAO Instances and FXML Declarations">
    // ... (Tidak ada perubahan) ...
    private final SiswaDAO siswaDAO = new SiswaDAO();
    private final PengumumanDAO pengumumanDAO = new PengumumanDAO();
    private final TugasDAO tugasDAO = new TugasDAO();
    private final EkstrakurikulerDAO ekstrakurikulerDAO = new EkstrakurikulerDAO();
    private final PesertaEkskulDAO pesertaEkskulDAO = new PesertaEkskulDAO();
    private final AgendaSekolahDAO agendaSekolahDAO = new AgendaSekolahDAO();
    private final JadwalKelasDAO jadwalKelasDAO = new JadwalKelasDAO();
    private final NilaiUjianDAO nilaiUjianDAO = new NilaiUjianDAO();
    private final MataPelajaranDAO mataPelajaranDAO = new MataPelajaranDAO();
    private final PresensiDAO presensiDAO = new PresensiDAO();
    private final TahunAjaranDAO tahunAjaranDAO = new TahunAjaranDAO();
    private final KelasDAO kelasDAO = new KelasDAO();

    @FXML private Label welcome;
    @FXML private Button logout;
    @FXML private TextField classHeaderField;
    @FXML private TextField homeroomTeacherHeaderField;
    @FXML private ComboBox<TahunAjaran> schoolYearHeaderCombo;
    @FXML private TextField biodataNisField, biodataNameField, biodataPobField, biodataDobField;
    @FXML private ComboBox<String> biodataGenderCombo;
    @FXML private TextArea biodataAddressArea;
    @FXML private TextField editNisField, editNameField, editPobField, editDobField;
    @FXML private ComboBox<String> editGenderCombo;
    @FXML private TextArea editAddressArea;
    @FXML private Button updateBiodataButton;
    @FXML private TableView<Pengumuman> announcementTable;
    @FXML private TableColumn<Pengumuman, String> announcementTitleCol, announcementDateCol, announcementContentCol, announcementAttachmentCol;
    @FXML private ComboBox<MataPelajaran> assignmentSubjectCombo;
    @FXML private TableView<Tugas> assignmentTable;
    @FXML private TableColumn<Tugas, String> assignmentTitleCol, assignmentDescCol, assignmentDeadlineCol;
    @FXML private ComboBox<TahunAjaran> extracurricularSchoolYearCombo;
    @FXML private TableView<Ekstrakurikuler> extracurricularTable;
    @FXML private TableColumn<Ekstrakurikuler, String> extracurricularNameCol, extracurricularLevelCol, extracurricularMentorCol;
    @FXML private ComboBox<TahunAjaran> agendaSchoolYearCombo;
    @FXML private TableView<AgendaSekolah> agendaTable;
    @FXML private TableColumn<AgendaSekolah, String> agendaContentCol, agendaStartCol, agendaEndCol;
    @FXML private TableView<JadwalKelas> scheduleTable;
    @FXML private TableColumn<JadwalKelas, String> scheduleDayCol, scheduleStartCol, scheduleEndCol, scheduleSubjectCol;
    @FXML private ComboBox<MataPelajaran> gradeSubjectCombo;
    @FXML private TableView<NilaiUjian> gradeTable;
    @FXML private TableColumn<NilaiUjian, BigDecimal> gradeGradeCol;
    @FXML private TextField reportClassField;
    @FXML private TableView<NilaiUjian> reportTable;
    @FXML private TableColumn<NilaiUjian, String> reportSubjectCol, reportExamCol;
    @FXML private TableColumn<NilaiUjian, BigDecimal> reportGradeCol;
    @FXML private TextField attendanceClassField;
    @FXML private TableView<Presensi> attendanceTable;
    @FXML private TableColumn<Presensi, String> attendanceDateCol, attendanceStatusCol;
    //</editor-fold>

    private Siswa currentSiswa;
    private final ObservableList<Presensi> masterAttendanceList = FXCollections.observableArrayList();
    private final ObservableList<NilaiUjian> masterReportList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupAllUIComponents();
        loadInitialData();

        User loggedInUser = SessionManager.getLoggedInUser();
        if (loggedInUser != null) {
            loadDataForSiswa(loggedInUser.getUsername());
        } else {
            welcome.setText("Welcome, Guest!");
            showAlert(Alert.AlertType.WARNING, "Sesi Tidak Ditemukan", "Tidak dapat menemukan data user yang login.");
        }
    }

    private void loadDataForSiswa(String nis) {
        this.currentSiswa = siswaDAO.getSiswaByNis(nis);

        if (this.currentSiswa != null) {
            welcome.setText("Welcome, " + currentSiswa.getNama() + "!");
            populateMyBiodata();
            loadAllTablesForSiswa();

            // PERBAIKAN: Memanggil metode baru untuk mengisi header
            updateHeaderInformation();

        } else {
            showAlert(Alert.AlertType.ERROR, "Data Error", "Data detail untuk siswa dengan NIS '" + nis + "' tidak ditemukan.");
            welcome.setText("Welcome, " + nis + "!");
        }
    }

    /**
     * PERBAIKAN: Metode baru untuk mengisi informasi di header dengan aman.
     * Metode ini akan selalu menampilkan data TERKINI dari siswa.
     */
    private void updateHeaderInformation() {
        if (currentSiswa == null) return;

        // Set Nama Kelas
        String currentClassName = currentSiswa.getNamaKelas();
        classHeaderField.setText(currentClassName != null ? currentClassName : "Belum ada kelas");
        reportClassField.setText(currentClassName != null ? currentClassName : "");
        attendanceClassField.setText(currentClassName != null ? currentClassName : "");

        // Set Wali Kelas
        if (currentSiswa.getIdKelas() != null) {
            Kelas currentKelas = kelasDAO.getKelasById(currentSiswa.getIdKelas());
            if (currentKelas != null && currentKelas.getNamaWaliKelas() != null) {
                homeroomTeacherHeaderField.setText(currentKelas.getNamaWaliKelas());
            } else {
                homeroomTeacherHeaderField.setText("N/A");
            }
        } else {
            homeroomTeacherHeaderField.setText("N/A");
        }

        // Set Tahun Ajaran
        if (currentSiswa.getIdTahunAjaran() != null) {
            TahunAjaran currentTahunAjaran = tahunAjaranDAO.getTahunAjaranById(currentSiswa.getIdTahunAjaran());
            if(currentTahunAjaran != null) {
                // Select item yang cocok di ComboBox
                for(TahunAjaran ta : schoolYearHeaderCombo.getItems()) {
                    if(ta.getIdTahunAjaran() == currentTahunAjaran.getIdTahunAjaran()) {
                        schoolYearHeaderCombo.getSelectionModel().select(ta);
                        break;
                    }
                }
            }
        }
    }

    // ... (Sisa kode tidak berubah dari jawaban sebelumnya)
    //<editor-fold defaultstate="collapsed" desc="Sisa Kode (Tidak Berubah)">
    private void setupAllUIComponents() {
        setupTableColumns();
        populateStaticComboBoxes();
    }
    private void setupTableColumns() {
        announcementTitleCol.setCellValueFactory(new PropertyValueFactory<>("judul"));
        announcementDateCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTanggal().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
        announcementContentCol.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        announcementAttachmentCol.setCellValueFactory(new PropertyValueFactory<>("lampiran"));
        assignmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("judul"));
        assignmentDescCol.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        assignmentDeadlineCol.setCellValueFactory(new PropertyValueFactory<>("tanggalDeadline"));
        extracurricularNameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        extracurricularLevelCol.setCellValueFactory(new PropertyValueFactory<>("tingkat"));
        extracurricularMentorCol.setCellValueFactory(new PropertyValueFactory<>("mentorNames"));
        agendaContentCol.setCellValueFactory(new PropertyValueFactory<>("judul"));
        agendaStartCol.setCellValueFactory(new PropertyValueFactory<>("tanggalMulai"));
        agendaEndCol.setCellValueFactory(new PropertyValueFactory<>("tanggalSelesai"));
        scheduleDayCol.setCellValueFactory(new PropertyValueFactory<>("hari"));
        scheduleStartCol.setCellValueFactory(new PropertyValueFactory<>("jamMulai"));
        scheduleEndCol.setCellValueFactory(new PropertyValueFactory<>("jamSelesai"));
        scheduleSubjectCol.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        gradeGradeCol.setCellValueFactory(new PropertyValueFactory<>("nilai"));
        reportSubjectCol.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        reportExamCol.setCellValueFactory(new PropertyValueFactory<>("jenisUjian"));
        reportGradeCol.setCellValueFactory(new PropertyValueFactory<>("nilai"));
        attendanceDateCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        attendanceStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void populateStaticComboBoxes() {
        editGenderCombo.getItems().addAll("Laki-laki", "Perempuan");
        ObservableList<MataPelajaran> allSubjects = FXCollections.observableArrayList(mataPelajaranDAO.getAllMataPelajaran());
        gradeSubjectCombo.setItems(allSubjects);
        assignmentSubjectCombo.setItems(allSubjects);
        ObservableList<TahunAjaran> allSchoolYears = FXCollections.observableArrayList(tahunAjaranDAO.getAllTahunAjaran());
        extracurricularSchoolYearCombo.setItems(allSchoolYears);
        agendaSchoolYearCombo.setItems(allSchoolYears);
        schoolYearHeaderCombo.setItems(allSchoolYears);
    }

    private void loadInitialData() {
        announcementTable.setItems(FXCollections.observableArrayList(pengumumanDAO.getAllPengumuman()));
        agendaTable.setItems(FXCollections.observableArrayList(agendaSekolahDAO.getAllAgendaSekolah()));
        assignmentTable.setItems(FXCollections.observableArrayList(tugasDAO.getAllTugas()));
    }
    private void loadAllTablesForSiswa() {
        if(currentSiswa.getIdKelas() != null) {
            scheduleTable.setItems(FXCollections.observableArrayList(
                    jadwalKelasDAO.getAllJadwalKelasDetails().stream()
                            .filter(j -> j.getIdKelas() == currentSiswa.getIdKelas())
                            .collect(Collectors.toList()))
            );
        }
        masterReportList.setAll(nilaiUjianDAO.getNilaiUjianByNis(currentSiswa.getNis()));
        gradeTable.setItems(masterReportList);
        reportTable.setItems(masterReportList);
        masterAttendanceList.setAll(presensiDAO.getPresensiByNis(currentSiswa.getNis()));
        attendanceTable.setItems(masterAttendanceList);
        List<PesertaEkskul> allPeserta = pesertaEkskulDAO.getAllPesertaEkskul();
        List<Ekstrakurikuler> myEkskul = allPeserta.stream()
                .filter(p -> p.getNis().equals(currentSiswa.getNis()))
                .map(p -> ekstrakurikulerDAO.getEkstrakurikulerById(p.getIdEkstrakurikuler()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        extracurricularTable.setItems(FXCollections.observableArrayList(myEkskul));
    }
    private void populateMyBiodata() {
        biodataNisField.setText(currentSiswa.getNis());
        biodataNameField.setText(currentSiswa.getNama());
        biodataGenderCombo.setValue(currentSiswa.getJenisKelamin());
        biodataPobField.setText(currentSiswa.getTempatLahir());
        biodataDobField.setText(currentSiswa.getTanggalLahir().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        biodataAddressArea.setText(currentSiswa.getAlamat());
        editNisField.setText(currentSiswa.getNis());
        editNameField.setText(currentSiswa.getNama());
        editGenderCombo.setValue(currentSiswa.getJenisKelamin());
        editPobField.setText(currentSiswa.getTempatLahir());
        editDobField.setText(currentSiswa.getTanggalLahir().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        editAddressArea.setText(currentSiswa.getAlamat());
    }
    @FXML void handleFilterGradeTable() {
        List<NilaiUjian> allNilai = nilaiUjianDAO.getNilaiUjianByNis(currentSiswa.getNis());
        MataPelajaran selectedSubject = gradeSubjectCombo.getValue();
        if (selectedSubject != null) {
            gradeTable.setItems(FXCollections.observableArrayList(
                    allNilai.stream().filter(n -> n.getIdMapel() == selectedSubject.getIdMapel()).collect(Collectors.toList())
            ));
        } else {
            gradeTable.setItems(FXCollections.observableArrayList(allNilai));
        }
    }

    @FXML void handleLogoutButtonAction(ActionEvent event) {
        try {
            SessionManager.clearSession();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectdouble/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat halaman login.");
        }
    }
    @FXML void handleUpdateBiodataButtonAction(ActionEvent event) {
        String nama = editNameField.getText();
        String tempatLahir = editPobField.getText();
        String tanggalLahirStr = editDobField.getText();
        String alamat = editAddressArea.getText();
        String jenisKelamin = editGenderCombo.getValue();
        if (nama.isEmpty() || tempatLahir.isEmpty() || tanggalLahirStr.isEmpty() || alamat.isEmpty() || jenisKelamin == null) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Semua field pada Edit Biodata harus diisi.");
            return;
        }
        if (!nama.matches("[a-zA-Z\\s']+")) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Nama hanya boleh mengandung huruf dan spasi.");
            return;
        }
        if (!tempatLahir.matches("[a-zA-Z\\s']+")) {
            showAlert(Alert.AlertType.ERROR, "Input Salah", "Tempat Lahir hanya boleh mengandung huruf dan spasi.");
            return;
        }
        LocalDate tanggalLahir;
        try {
            tanggalLahir = LocalDate.parse(tanggalLahirStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Format Salah", "Format Tanggal Lahir harus dd/mm/yyyy (contoh: 31/12/2005).");
            return;
        }
        currentSiswa.setNama(nama);
        currentSiswa.setJenisKelamin(jenisKelamin);
        currentSiswa.setTempatLahir(tempatLahir);
        currentSiswa.setTanggalLahir(tanggalLahir);
        currentSiswa.setAlamat(alamat);
        if (siswaDAO.updateSiswa(currentSiswa)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data biodata berhasil diperbarui.");
            loadDataForSiswa(currentSiswa.getNis());
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui data biodata di database.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //</editor-fold>
}