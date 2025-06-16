package com.example.projectdouble.controller;

import com.example.projectdouble.DAO.*;
import com.example.projectdouble.model.*;
import com.example.projectdouble.util.SessionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private Button logout;
    @FXML
    private Label welcome;

    // Tab Pane (for switching tabs)
    @FXML
    private TabPane mainTabPane; // Tambahkan ini jika belum ada di FXML Anda
    @FXML
    private Tab entryStudentDataTab; // Tambahkan ini jika belum ada di FXML Anda

    // --- Components for Announcements Tab ---
    @FXML
    private TextField announcementTitleField;
    @FXML
    private TextArea announcementContentArea;
    @FXML
    private TextField announcementAttachmentField;
    @FXML
    private Button publishAnnouncementButton;
    @FXML
    private Button updateAnnouncementButton;
    @FXML
    private Button deleteAnnouncementButton;
    @FXML
    private TableView<Pengumuman> announcementTable;
    @FXML
    private TableColumn<Pengumuman, String> colAnnouncementTitle;
    @FXML
    private TableColumn<Pengumuman, LocalDateTime> colAnnouncementDate;
    @FXML
    private TableColumn<Pengumuman, String> colAnnouncementContent;
    @FXML
    private TableColumn<Pengumuman, String> colAnnouncementAttachment;
    @FXML
    private Button chooseFileButton;

    private PengumumanDAO pengumumanDAO;
    private ObservableList<Pengumuman> announcementList;

    // --- Components for Entry Student Data Tab ---
    @FXML
    private TextField nisInputField;
    @FXML
    private TextField studentNameInputField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField placeOfBirthInputField;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private TextArea addressTextArea;
    @FXML
    private Button addStudentDataButton;

    @FXML
    private TextField studentUsernameInputField;
    @FXML
    private TextField studentPasswordInputField;
    @FXML
    private Button addStudentUserButton;

    @FXML
    private ComboBox<Siswa> selectNisEditComboBox;
    @FXML
    private TextField nisEditField;
    @FXML
    private TextField nameEditField;
    @FXML
    private ComboBox<String> genderEditComboBox;
    @FXML
    private TextField placeOfBirthEditField;
    @FXML
    private DatePicker dateOfBirthEditPicker;
    @FXML
    private TextArea addressEditArea;
    @FXML
    private Label usernameEditLabel;
    @FXML
    private Label passwordEditLabel;
    @FXML
    private Button updateStudentDataButton;
    @FXML
    private Button deleteStudentDataButton;

    private SiswaDAO siswaDao;
    private UserDAO userDao;


    // --- Components for View Students Tab ---
    @FXML
    private TextField searchStudentField;
    @FXML
    private TableView<Siswa> studentTable;
    @FXML
    private TableColumn<Siswa, String> colStudentNis;
    @FXML
    private TableColumn<Siswa, String> colStudentName;
    @FXML
    private TableColumn<Siswa, String> colStudentGender;
    @FXML
    private TableColumn<Siswa, String> colStudentPlaceOfBirth;
    @FXML
    private TableColumn<Siswa, LocalDate> colStudentDateOfBirth;
    @FXML
    private TableColumn<Siswa, String> colStudentAddress;
    @FXML
    private TableColumn<Siswa, String> colStudentClass; // New column for class
    @FXML
    private TableColumn<Siswa, String> colStudentSchoolYear; // New column for school year and semester
    private ObservableList<Siswa> studentList;


    // --- Components for View Teachers Tab ---
    @FXML
    private TextField searchTeacherField;
    @FXML
    private TableView<Guru> teacherTable;
    @FXML
    private TableColumn<Guru, String> colTeacherNip;
    @FXML
    private TableColumn<Guru, String> colTeacherPassword;
    @FXML
    private TableColumn<Guru, String> colTeacherName;
    @FXML
    private TableColumn<Guru, String> colTeacherGender;
    @FXML
    private TableColumn<Guru, String> colTeacherEmail;
    @FXML
    private TableColumn<Guru, String> colTeacherPhone;
    @FXML
    private Button deleteTeacherButton;
    private GuruDAO guruDao;
    private ObservableList<Guru> teacherList;


    // --- Components for Manage Schedules Tab ---
    @FXML
    private ChoiceBox<String> dayChoiceBox;
    @FXML
    private TextField startTimeField;
    @FXML
    private TextField endTimeField;
    @FXML
    private ChoiceBox<MataPelajaran> subjectChoiceBox;
    @FXML
    private ChoiceBox<Kelas> classChoiceBox;
    @FXML
    private ChoiceBox<Guru> teacherChoiceBox;
    @FXML
    private ComboBox<TahunAjaran> schoolYearScheduleComboBox;
    @FXML
    private ComboBox<String> semesterScheduleComboBox;
    @FXML
    private Button addScheduleButton;

    // NEW FXML for schedule table
    @FXML
    private TableView<JadwalKelas> scheduleTable;
    @FXML
    private TableColumn<JadwalKelas, String> colScheduleDay;
    @FXML
    private TableColumn<JadwalKelas, String> colScheduleTime;
    @FXML
    private TableColumn<JadwalKelas, String> colScheduleSubject;
    @FXML
    private TableColumn<JadwalKelas, String> colScheduleClass;
    @FXML
    private TableColumn<JadwalKelas, String> colScheduleTeacher;
    @FXML
    private TableColumn<JadwalKelas, String> colScheduleSemester;
    // NEW: Delete Schedule Button
    @FXML private Button deleteScheduleButton;

    private ObservableList<JadwalKelas> scheduleList;


    private JadwalKelasDAO jadwalKelasDao;
    private MataPelajaranDAO mataPelajaranDao;
    private KelasDAO kelasDao;
    private TahunAjaranDAO tahunAjaranDao;


    // --- Components for Manage Classes Tab ---
    // CREATE NEW CLASS
    @FXML
    private TextField classNameInputField;
    @FXML
    private ChoiceBox<Guru> homeroomTeacherChoiceBox;
    @FXML
    private ComboBox<TahunAjaran> schoolYearCreateClassComboBox;
    @FXML
    private ComboBox<String> semesterCreateClassComboBox;
    @FXML
    private Button createClassButton;

    // EDIT/DELETE CLASS
    @FXML
    private ComboBox<Kelas> classNameEditComboBox;
    @FXML
    private ComboBox<Guru> homeroomTeacherEditComboBox;
    @FXML
    private ComboBox<TahunAjaran> schoolYearEditClassComboBox;
    @FXML
    private ComboBox<String> semesterEditClassComboBox;
    @FXML
    private Button updateClassButton;
    @FXML
    private Button deleteClassButton;

    // ASSIGN STUDENT TO CLASS
    @FXML
    private TextField searchStudentAssignClassField;
    @FXML
    private ComboBox<Kelas> classAssignComboBox;
    @FXML
    private ComboBox<TahunAjaran> schoolYearAssignComboBox;
    @FXML
    private ComboBox<String> semesterAssignComboBox;
    @FXML
    private TableView<Siswa> studentsToAssignTable;
    @FXML
    private TableColumn<Siswa, String> colAssignNis;
    @FXML
    private TableColumn<Siswa, String> colAssignName;
    @FXML
    private Button assignStudentButton;

    // STUDENT IN SELECTED CLASS
    @FXML
    private ComboBox<Kelas> classStudentsInSelectedClassComboBox;
    @FXML
    private ComboBox<TahunAjaran> schoolYearStudentsInSelectedClassComboBox;
    @FXML
    private ComboBox<String> semesterStudentsInSelectedClassComboBox;
    @FXML
    private TableView<Siswa> studentsInSelectedClassTable;
    @FXML
    private TableColumn<Siswa, String> colInClassNis;
    @FXML
    private TableColumn<Siswa, String> colInClassName;
    @FXML
    private Button editSelectedStudentInClassButton;
    @FXML
    private Button removeStudentFromClassButton;


    // --- Components for Manage School Agenda Tab ---
    @FXML
    private TextField agendaContentField;
    @FXML
    private DatePicker agendaStartDatePicker;
    @FXML
    private DatePicker agendaEndDatePicker;
    @FXML
    private ComboBox<TahunAjaran> schoolYearAgendaAddComboBox;
    @FXML
    private ComboBox<String> semesterAgendaAddComboBox;
    @FXML
    private Button addAgendaButton;

    @FXML
    private ComboBox<TahunAjaran> schoolYearAgendaViewComboBox;
    @FXML
    private ComboBox<String> semesterAgendaViewComboBox;
    @FXML
    private TableView<AgendaSekolah> agendaTable;
    @FXML
    private TableColumn<AgendaSekolah, String> colAgendaContent;
    @FXML
    private TableColumn<AgendaSekolah, LocalDate> colAgendaStart;
    @FXML
    private TableColumn<AgendaSekolah, LocalDate> colAgendaEnd;
    @FXML
    private Button deleteAgendaButton;
    private AgendaSekolahDAO agendaSekolahDao;
    private ObservableList<AgendaSekolah> agendaList;


    // --- Components for Extracurricular Tab ---
    // INPUT MENTOR
    @FXML
    private ComboBox<Ekstrakurikuler> extracurricularInputMentorComboBox;
    @FXML
    private ComboBox<Guru> mentor1ComboBox;
    @FXML
    private ComboBox<Guru> mentor2ComboBox;
    @FXML
    private ComboBox<Guru> mentor3ComboBox;
    @FXML
    private ComboBox<Guru> mentor4ComboBox;
    @FXML
    private ComboBox<String> levelInputMentorComboBox;
    @FXML
    private Button addMentorButton;

    // EXTRACURRICULAR Table
    @FXML
    private TableView<Ekstrakurikuler> extracurricularTable;
    @FXML
    private TableColumn<Ekstrakurikuler, String> colExtracurricularName;
    @FXML
    private TableColumn<Ekstrakurikuler, String> colExtracurricularLevel;
    @FXML
    private TableColumn<Ekstrakurikuler, String> colExtracurricularMentor;
    @FXML
    private Button deleteExtracurricularButton;


    // INPUT SISWA to Extracurricular
    @FXML
    private ComboBox<Kelas> classInputSiswaEkskulComboBox;
    @FXML
    private ComboBox<TahunAjaran> schoolYearInputSiswaEkskulComboBox;
    @FXML
    private ComboBox<String> semesterInputSiswaEkskulComboBox;
    @FXML
    private ComboBox<Ekstrakurikuler> extracurricularInputSiswaEkskulComboBox;
    @FXML
    private ComboBox<String> levelInputSiswaEkskulComboBox;
    @FXML
    private ComboBox<Siswa> studentInputSiswaEkskulComboBox;
    @FXML
    private Button addStudentToExtracurricularButton;

    @FXML
    private TableView<Siswa> studentsInExtracurricularTable; // Renamed for clarity
    @FXML
    private TableColumn<Siswa, String> colExtracurricularStudentNis;
    @FXML
    private TableColumn<Siswa, String> colExtracurricularStudentName;
    @FXML
    private TableColumn<Siswa, String> colExtracurricularStudentClass;
    @FXML
    private Button removeStudentFromExtracurricularButton;
    private ObservableList<Siswa> studentsInSelectedExtracurricularList;
    private EkstrakurikulerDAO ekstrakurikulerDao;
    private PembinaDAO pembinaDao;
    private PesertaEkskulDAO pesertaEkskulDao;


    // DAOs
//    private UserDAO userDao;
//    private SiswaDAO siswaDao;
//    private TahunAjaranDAO tahunAjaranDao;
//    private KelasDAO kelasDao;
//    private GuruDAO guruDao;
//    private MataPelajaranDAO mataPelajaranDao;
//    private JadwalKelasDAO jadwalKelasDao;
//    private PengumumanDAO pengumumanDao;
//    private AgendaSekolahDAO agendaSekolahDao;
//    private EkstrakurikulerDAO ekstrakurikulerDao;
//    private PembinaDAO pembinaDao;
//    private PesertaEkskulDAO pesertaEkskulDao;

    public void setUsername(String user){
        welcome.setText("Welcome, " + user + "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi DAO
        pengumumanDAO = new PengumumanDAO();
        siswaDao = new SiswaDAO();
        userDao = new UserDAO();
        guruDao = new GuruDAO();
        jadwalKelasDao = new JadwalKelasDAO();
        mataPelajaranDao = new MataPelajaranDAO();
        kelasDao = new KelasDAO();
        tahunAjaranDao = new TahunAjaranDAO();
        agendaSekolahDao = new AgendaSekolahDAO();
        ekstrakurikulerDao = new EkstrakurikulerDAO();
        pembinaDao = new PembinaDAO();
        pesertaEkskulDao = new PesertaEkskulDAO();


        // Set welcome message
        User loggedInUser = SessionManager.getLoggedInUser();
        if (loggedInUser != null) {
            welcome.setText("Welcome, " + loggedInUser.getDisplayName() + "!");
        } else {
            welcome.setText("Welcome, Guest!");
        }

        // Initialize UI components for each tab
        setupAnnouncementTab();
        setupStudentEntryTab();
        setupViewStudentsTab();
        setupViewTeachersTab();
        setupManageSchedulesTab();
        setupManageClassesTab();
        setupManageSchoolAgendaTab();
        setupExtracurricularTab();

        // Load initial data
        loadAnnouncements();
        loadAllStudents();
        loadAllTeachers();
        loadTeachersIntoComboBoxes(); // For Kelas, Jadwal, Pembina
        loadTahunAjaranIntoComboBoxes(); // For Kelas, Jadwal, Agenda
        loadKelasIntoComboBoxes(); // For Jadwal, Assign Student, Siswa Ekskul
        loadMataPelajaranIntoChoiceBoxes(); // For Jadwal
        loadEkstrakurikulerIntoComboBoxes(); // For Pembina and Peserta Ekskul
        //loadStudentsIntoComboBoxes(); // For Peserta Ekskul input
        loadSchedules(); // Panggil untuk memuat jadwal saat inisialisasi

        // Listeners for changes in selection to populate edit forms/tables
        setupListeners();
    }

    // --- Logout Action ---
    @FXML
    public void actionLogout(ActionEvent actionEvent) {
        try {
            SessionManager.clearSession();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectdouble/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metode helper untuk perataan tengah kolom
    private <S, T> void centerAlignColumn(TableColumn<S, T> column) {
        column.setCellFactory(col -> {
            return new TableCell<S, T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null); // Penting juga untuk mengosongkan graphic jika ada
                    } else {
                        setText(item.toString());
                        setGraphic(null); // Penting juga untuk mengosongkan graphic jika ada
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
        });
    }

    // Metode helper untuk text wrapping saja (rata kiri)
    private <S, T> void wrapTextColumn(TableColumn<S, T> column) {
        column.setCellFactory(col -> {
            return new TableCell<S, T>() {
                private final javafx.scene.text.Text text;

                {
                    text = new Text();
                    text.wrappingWidthProperty().bind(column.widthProperty().subtract(5)); // Kurangi sedikit untuk padding
                    setGraphic(text);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setAlignment(Pos.CENTER_LEFT); // Default rata kiri jika hanya wrapping
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                }

                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText(null);
                        setGraphic(null);
                    } else {
                        text.setText(item.toString());
                        setGraphic(text);
                    }
                }
            };
        });
    }


    // --- Helper Methods to setup Tabs ---

    private void setupAnnouncementTab() {
        colAnnouncementTitle.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colAnnouncementDate.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colAnnouncementContent.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        colAnnouncementAttachment.setCellValueFactory(new PropertyValueFactory<>("lampiran"));

        // Format tanggal untuk tampilan yang lebih baik (Opsional, tergantung kebutuhan)
//        colAnnouncementDate.setCellFactory(column -> new TableCell<Pengumuman, LocalDateTime>() {
//            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//            @Override
//            protected void updateItem(LocalDateTime item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    setText(null);
//                } else {
//                    setText(formatter.format(item));
//                }
//            }
//        });

        // Listener untuk memilih pengumuman dan mengisi form edit
        announcementTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateAnnouncementFields(newSelection);
            } else {
                clearAnnouncementFields();
            }
        });

        wrapTextColumn(colAnnouncementTitle);
        wrapTextColumn(colAnnouncementDate);
        wrapTextColumn(colAgendaContent);
        wrapTextColumn(colAnnouncementAttachment);
        centerAlignColumn(colAnnouncementDate);
    }

    private void setupStudentEntryTab() {
        genderComboBox.setItems(FXCollections.observableArrayList("Laki-laki", "Perempuan"));
        genderEditComboBox.setItems(FXCollections.observableArrayList("Laki-laki", "Perempuan"));
        // Listener untuk selectNisEditComboBox
        selectNisEditComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateEditStudentFields(newSelection);
            } else {
                clearEditStudentFields();
            }
        });
    }

    private void setupViewStudentsTab() {
        colStudentNis.setCellValueFactory(new PropertyValueFactory<>("nis"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colStudentGender.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        colStudentPlaceOfBirth.setCellValueFactory(new PropertyValueFactory<>("tempatLahir"));
        colStudentDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("tanggalLahir"));
        colStudentAddress.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colStudentClass.setCellValueFactory(new PropertyValueFactory<>("namaKelas")); // From Siswa model's namaKelas
        colStudentSchoolYear.setCellValueFactory(cellData -> {
            Siswa siswa = cellData.getValue();
            String tahunAjaran = siswa.getTahunAjaranLengkap();
            String semester = siswa.getSemester();
            if (tahunAjaran != null && semester != null) {
                return new SimpleStringProperty(tahunAjaran + " " + semester);
            }
            return new SimpleStringProperty("");
        });

        // Format tanggal lahir untuk tampilan
        colStudentDateOfBirth.setCellFactory(column -> new TableCell<Siswa, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        // NEW: Terapkan perataan tengah untuk kolom-kolom siswa yang diinginkan
        centerAlignColumn(colStudentNis);
        // centerAlignColumn(colStudentName); // Nama mungkin lebih baik rata kiri
        centerAlignColumn(colStudentGender);
        centerAlignColumn(colStudentPlaceOfBirth);
        centerAlignColumn(colStudentDateOfBirth); // Sudah ditangani di cell factory di atas
        // centerAlignColumn(colStudentAddress); // Alamat mungkin lebih baik rata kiri
        centerAlignColumn(colStudentClass);
        centerAlignColumn(colStudentSchoolYear);
        wrapTextColumn(colStudentAddress);
    }

    private void setupViewTeachersTab() {
        colTeacherNip.setCellValueFactory(new PropertyValueFactory<>("nip"));
        colTeacherPassword.setCellValueFactory(new PropertyValueFactory<>("passwordUser")); // Display password (for demo, not for production)
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colTeacherGender.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        colTeacherEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTeacherPhone.setCellValueFactory(new PropertyValueFactory<>("noHp"));

        centerAlignColumn(colTeacherNip);
        centerAlignColumn(colTeacherPassword);
        centerAlignColumn(colTeacherEmail);
        centerAlignColumn(colTeacherGender);
        centerAlignColumn(colTeacherPhone);
    }

    private void setupManageSchedulesTab() {
        dayChoiceBox.setItems(FXCollections.observableArrayList("Senin", "Selasa", "Rabu", "Kamis", "Jumat"));
        semesterScheduleComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));

        // Setup Schedule Table Columns
        colScheduleDay.setCellValueFactory(new PropertyValueFactory<>("hari"));
        colScheduleTime.setCellValueFactory(new PropertyValueFactory<>("timeRange")); // Uses computed property
        colScheduleSubject.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        colScheduleClass.setCellValueFactory(new PropertyValueFactory<>("kelasDanTahunAjaran")); // Uses computed property
        colScheduleTeacher.setCellValueFactory(new PropertyValueFactory<>("namaGuru"));
        colScheduleSemester.setCellValueFactory(new PropertyValueFactory<>("semester"));

        // NEW: Terapkan perataan tengah untuk semua kolom jadwal
        centerAlignColumn(colScheduleDay);
        centerAlignColumn(colScheduleTime);
        centerAlignColumn(colScheduleSubject);
        centerAlignColumn(colScheduleClass);
        centerAlignColumn(colScheduleTeacher);
        centerAlignColumn(colScheduleSemester);
    }

    private void setupManageClassesTab() {
        // Assign Student to Class
        colAssignNis.setCellValueFactory(new PropertyValueFactory<>("nis"));
        colAssignName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        // Student in Selected Class
        colInClassNis.setCellValueFactory(new PropertyValueFactory<>("nis"));
        colInClassName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        centerAlignColumn(colAssignNis);
        centerAlignColumn(colAssignName);
        centerAlignColumn(colInClassName);
        centerAlignColumn(colInClassNis);

        // Listeners for classes and school years combo boxes to filter student lists
        classStudentsInSelectedClassComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadStudentsInSelectedClass());
        schoolYearStudentsInSelectedClassComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadStudentsInSelectedClass());
        //semesterStudentsInSelectedClassComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadStudentsInSelectedClass());

        classNameEditComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateEditClassFields(newSelection);
            } else {
                clearEditClassFields();
            }
        });
    }

    private void setupManageSchoolAgendaTab() {
        colAgendaContent.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colAgendaStart.setCellValueFactory(new PropertyValueFactory<>("tanggalMulai"));
        colAgendaEnd.setCellValueFactory(new PropertyValueFactory<>("tanggalSelesai"));

        centerAlignColumn(colAgendaContent);

        colAgendaStart.setCellFactory(column -> new TableCell<AgendaSekolah, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        colAgendaEnd.setCellFactory(column -> new TableCell<AgendaSekolah, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
        centerAlignColumn(colAgendaStart);
        centerAlignColumn(colAgendaEnd);

        semesterAgendaAddComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));
        semesterAgendaViewComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));

        agendaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Not implemented: no edit agenda in FXML, only add/delete/view
            // If editing was desired, you'd populate fields here
        });

        // Add listeners for filtering agenda table
        schoolYearAgendaViewComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadSchoolAgenda());
        semesterAgendaViewComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadSchoolAgenda());
    }


    private void setupExtracurricularTab() {
        levelInputMentorComboBox.setItems(FXCollections.observableArrayList("Siaga", "Penggalang", "Penegak", "Umum")); // Example levels
        levelInputSiswaEkskulComboBox.setItems(FXCollections.observableArrayList("Siaga", "Penggalang", "Penegak", "Umum")); // Example levels

        colExtracurricularName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colExtracurricularLevel.setCellValueFactory(new PropertyValueFactory<>("tingkat"));
        colExtracurricularMentor.setCellValueFactory(new PropertyValueFactory<>("mentorNames")); // Bind to the derived property

        centerAlignColumn(colExtracurricularName);
        centerAlignColumn(colExtracurricularLevel);
        wrapTextColumn(colExtracurricularMentor);

        extracurricularTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // No edit fields for extracurricular in FXML, only delete.
            // If editing was desired, you'd populate fields here.
        });

        // NEW: Setup columns for studentsInExtracurricularTable
        colExtracurricularStudentNis.setCellValueFactory(new PropertyValueFactory<>("nis"));
        colExtracurricularStudentName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colExtracurricularStudentClass.setCellValueFactory(new PropertyValueFactory<>("namaKelas")); // Assuming Siswa model has namaKelas

        centerAlignColumn(colExtracurricularStudentNis);
        centerAlignColumn(colExtracurricularStudentName);

        // Listener for extracurricularInputMentorComboBox to select existing ekskul for adding mentor
        extracurricularInputMentorComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Populate levelInputMentorComboBox based on selected extracurricular's level
                levelInputMentorComboBox.getSelectionModel().select(newVal.getTingkat());
            } else {
                levelInputMentorComboBox.getSelectionModel().clearSelection();
            }
        });

        // NEW: Listener for schoolYearInputSiswaEkskulComboBox to auto-set semester
        schoolYearInputSiswaEkskulComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                semesterInputSiswaEkskulComboBox.getSelectionModel().select("Ganjil"); // Auto-set to Ganjil
            } else {
                semesterInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
            }
            // Trigger student list load as school year selection changes
            loadStudentsForExtracurricularAssignment();
        });

        // NEW: Listeners for classInputSiswaEkskulComboBox and semesterInputSiswaEkskulComboBox
        classInputSiswaEkskulComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadStudentsForExtracurricularAssignment());
        semesterInputSiswaEkskulComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadStudentsForExtracurricularAssignment());

        // NEW: Listener for extracurricularInputSiswaEkskulComboBox to load students in its table
        extracurricularInputSiswaEkskulComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            loadStudentsInExtracurricularTable(); // Load students when extracurricular is selected
        });
    }

    private void setupListeners() {
        // Listener for searchStudentField on View Students tab
        searchStudentField.textProperty().addListener((observable, oldValue, newValue) -> handleSearchStudent(null));
        // Listener for searchTeacherField on View Teachers tab
        searchTeacherField.textProperty().addListener((observable, oldValue, newValue) -> handleSearchTeacher(null));
        // Listener for searchStudentAssignClassField on Manage Classes tab
        searchStudentAssignClassField.textProperty().addListener((observable, oldValue, newValue) -> handleSearchStudentToAssign());
    }


    // --- Load Data Methods ---

    private void loadAnnouncements() {
        announcementList = FXCollections.observableArrayList(pengumumanDAO.getAllPengumuman());
        announcementTable.setItems(announcementList);
    }

    private void loadAllStudents() {
        studentList = FXCollections.observableArrayList(siswaDao.getAllSiswa());
        studentTable.setItems(studentList);
        selectNisEditComboBox.setItems(studentList); // Populate NIS for editing
        studentsToAssignTable.setItems(studentList); // Populate table for assigning
    }

    private void loadAllTeachers() {
        teacherList = FXCollections.observableArrayList(guruDao.getAllGuru());
        teacherTable.setItems(teacherList);
    }

    private void loadTeachersIntoComboBoxes() {
        ObservableList<Guru> teachers = FXCollections.observableArrayList(guruDao.getAllGuru());
        homeroomTeacherChoiceBox.setItems(teachers);
        homeroomTeacherEditComboBox.setItems(teachers);
        teacherChoiceBox.setItems(teachers);
        mentor1ComboBox.setItems(teachers);
        mentor2ComboBox.setItems(teachers);
        mentor3ComboBox.setItems(teachers);
        mentor4ComboBox.setItems(teachers);
    }

    private void loadTahunAjaranIntoComboBoxes() {
        ObservableList<TahunAjaran> tahunAjaranList = FXCollections.observableArrayList(tahunAjaranDao.getAllTahunAjaran());
        schoolYearScheduleComboBox.setItems(tahunAjaranList);
        schoolYearCreateClassComboBox.setItems(tahunAjaranList);
        schoolYearEditClassComboBox.setItems(tahunAjaranList);
        schoolYearAssignComboBox.setItems(tahunAjaranList);
        schoolYearStudentsInSelectedClassComboBox.setItems(tahunAjaranList);
        schoolYearAgendaAddComboBox.setItems(tahunAjaranList);
        schoolYearAgendaViewComboBox.setItems(tahunAjaranList);
        schoolYearInputSiswaEkskulComboBox.setItems(tahunAjaranList);
    }

    private void loadMataPelajaranIntoChoiceBoxes() {
        ObservableList<MataPelajaran> mapelList = FXCollections.observableArrayList(mataPelajaranDao.getAllMataPelajaran());
        subjectChoiceBox.setItems(mapelList);
    }

    private void loadKelasIntoComboBoxes() {
        ObservableList<Kelas> kelasList = FXCollections.observableArrayList(kelasDao.getAllKelas());
        classChoiceBox.setItems(kelasList);
        classNameEditComboBox.setItems(kelasList);
        classAssignComboBox.setItems(kelasList);
        classStudentsInSelectedClassComboBox.setItems(kelasList);
        classInputSiswaEkskulComboBox.setItems(kelasList);
    }

    private void loadEkstrakurikulerIntoComboBoxes() {
        ObservableList<Ekstrakurikuler> ekskulList = FXCollections.observableArrayList(ekstrakurikulerDao.getAllEkstrakurikulerWithMentors());
        extracurricularTable.setItems(ekskulList);
        extracurricularInputMentorComboBox.setItems(ekskulList);
        extracurricularInputSiswaEkskulComboBox.setItems(ekskulList);
    }

//    private void loadStudentsIntoComboBoxes() {
//        ObservableList<Siswa> siswaList = FXCollections.observableArrayList(siswaDao.getAllSiswa());
//        studentInputSiswaEkskulComboBox.setItems(siswaList);
//    }

    private void loadStudentsForExtracurricularAssignment() {
        Kelas selectedClass = classInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedTahunAjaran = schoolYearInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();

        if (selectedClass != null && selectedTahunAjaran != null) {
            // Call DAO to get students based on class and school year
            ObservableList<Siswa> filteredStudents = FXCollections.observableArrayList(
                    siswaDao.getStudentsInClass(selectedClass.getIdKelas(), selectedTahunAjaran.getIdTahunAjaran()));
            studentInputSiswaEkskulComboBox.setItems(filteredStudents);
        } else {
            // Clear the ComboBox if filters are not complete
            studentInputSiswaEkskulComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void loadSchoolAgenda() {
        TahunAjaran selectedTahunAjaran = schoolYearAgendaViewComboBox.getSelectionModel().getSelectedItem();

        if (selectedTahunAjaran != null) {
            // Load agenda items for the selected school year
            agendaList = FXCollections.observableArrayList(agendaSekolahDao.getAgendaByTahunAjaran(selectedTahunAjaran.getIdTahunAjaran()));
        } else {
            // Load all agenda items if no school year is selected
            agendaList = FXCollections.observableArrayList(agendaSekolahDao.getAllAgendaSekolah());
        }
        agendaTable.setItems(agendaList);
    }

    private void loadStudentsInSelectedClass() {
        Kelas selectedKelas = classStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedTahunAjaran = schoolYearStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = "Ganjil";

        if (selectedKelas != null && selectedTahunAjaran != null) {
            ObservableList<Siswa> students = FXCollections.observableArrayList(
                    siswaDao.getStudentsInClass(selectedKelas.getIdKelas(), selectedTahunAjaran.getIdTahunAjaran(), selectedSemester));
            studentsInSelectedClassTable.setItems(students);
        } else {
            studentsInSelectedClassTable.setItems(FXCollections.observableArrayList());
        }
    }

    // NEW: Load students enrolled in extracurriculars into the table
    private void loadStudentsInExtracurricularTable() {
        Ekstrakurikuler selectedEkskul = extracurricularInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();
        if (selectedEkskul != null) {
            // Assume PesertaEkskulDao has a method to get Siswa by extracurricular ID
            ObservableList<Siswa> students = FXCollections.observableArrayList(pesertaEkskulDao.getStudentsByExtracurricular(selectedEkskul.getIdEkstrakurikuler()));
            studentsInExtracurricularTable.setItems(students);
        } else {
            studentsInExtracurricularTable.setItems(FXCollections.observableArrayList()); // Clear table if no ekskul selected
        }
    }

    // NEW: Load all schedules into the table
    private void loadSchedules() {
        scheduleList = FXCollections.observableArrayList(jadwalKelasDao.getAllJadwalKelasDetails());
        scheduleTable.setItems(scheduleList);
    }


    // --- Populate / Clear Fields Methods ---

    private void populateAnnouncementFields(Pengumuman p) {
        announcementTitleField.setText(p.getJudul());
        announcementContentArea.setText(p.getDeskripsi());
        announcementAttachmentField.setText(p.getLampiran());
    }

    private void clearAnnouncementFields() {
        announcementTitleField.clear();
        announcementContentArea.clear();
        announcementAttachmentField.clear();
        announcementTable.getSelectionModel().clearSelection();
    }

    private void populateEditStudentFields(Siswa siswa) {
        nisEditField.setText(siswa.getNis());
        nameEditField.setText(siswa.getNama());
        genderEditComboBox.getSelectionModel().select(siswa.getJenisKelamin());
        placeOfBirthEditField.setText(siswa.getTempatLahir());
        dateOfBirthEditPicker.setValue(siswa.getTanggalLahir());
        addressEditArea.setText(siswa.getAlamat());
        usernameEditLabel.setText(siswa.getUsernameUser() != null ? siswa.getUsernameUser() : "N/A");
        passwordEditLabel.setText(siswa.getPasswordUser() != null ? siswa.getPasswordUser() : "N/A"); // For demo only
    }

    private void clearAddStudentFields() {
        nisInputField.clear();
        studentNameInputField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        placeOfBirthInputField.clear();
        dateOfBirthPicker.setValue(null);
        addressTextArea.clear();
    }

    private void clearAddStudentUserFields() {
        studentUsernameInputField.clear();
        studentPasswordInputField.clear();
    }

    private void clearEditStudentFields() {
        selectNisEditComboBox.getSelectionModel().clearSelection();
        nisEditField.clear();
        nameEditField.clear();
        genderEditComboBox.getSelectionModel().clearSelection();
        placeOfBirthEditField.clear();
        dateOfBirthEditPicker.setValue(null);
        addressEditArea.clear();
        usernameEditLabel.setText("");
        passwordEditLabel.setText("");
    }

    private void populateEditClassFields(Kelas kelas) {
        // Find the Kelas object in the combo box items by its ID to ensure selection works correctly
        classNameEditComboBox.getSelectionModel().select(kelas);
        homeroomTeacherEditComboBox.getSelectionModel().select(kelas.getNipWaliKelas() != null ? guruDao.getGuruByNip(kelas.getNipWaliKelas()) : null);
        schoolYearEditClassComboBox.getSelectionModel().select(kelas.getIdTahunAjaran() != 0 ? tahunAjaranDao.getTahunAjaranById(kelas.getIdTahunAjaran()) : null);
    }

    private void clearCreateClassFields() {
        classNameInputField.clear();
        homeroomTeacherChoiceBox.getSelectionModel().clearSelection();
        schoolYearCreateClassComboBox.getSelectionModel().clearSelection();
    }

    private void clearEditClassFields() {
        classNameEditComboBox.getSelectionModel().clearSelection();
        homeroomTeacherEditComboBox.getSelectionModel().clearSelection();
        schoolYearEditClassComboBox.getSelectionModel().clearSelection();
    }

    private void clearAddScheduleFields() {
        dayChoiceBox.getSelectionModel().clearSelection();
        startTimeField.clear();
        endTimeField.clear();
        subjectChoiceBox.getSelectionModel().clearSelection();
        classChoiceBox.getSelectionModel().clearSelection();
        teacherChoiceBox.getSelectionModel().clearSelection();
        schoolYearScheduleComboBox.getSelectionModel().clearSelection();
        semesterScheduleComboBox.getSelectionModel().clearSelection();
    }

    private void clearAddSchoolAgendaFields() {
        agendaContentField.clear();
        agendaStartDatePicker.setValue(null);
        agendaEndDatePicker.setValue(null);
        schoolYearAgendaAddComboBox.getSelectionModel().clearSelection();
        semesterAgendaAddComboBox.getSelectionModel().clearSelection();
    }

    private void clearAddMentorFields() {
        extracurricularInputMentorComboBox.getSelectionModel().clearSelection();
        mentor1ComboBox.getSelectionModel().clearSelection();
        mentor2ComboBox.getSelectionModel().clearSelection();
        mentor3ComboBox.getSelectionModel().clearSelection();
        mentor4ComboBox.getSelectionModel().clearSelection();
        levelInputMentorComboBox.getSelectionModel().clearSelection();
    }

    // NEW: Clear fields for "Add Student to Extracurricular" form
    private void clearAddStudentToExtracurricularFields() {
        classInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        schoolYearInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        semesterInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        extracurricularInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        levelInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        studentInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        studentInputSiswaEkskulComboBox.setItems(FXCollections.observableArrayList()); // Clear filtered students in the student combo box
    }

    // --- Action Event Handlers ---

    @FXML
    private void handleChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Lampiran");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            announcementAttachmentField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handlePublishAnnouncement(ActionEvent event) {
        String judul = announcementTitleField.getText();
        String deskripsi = announcementContentArea.getText();
        String lampiran = announcementAttachmentField.getText();
        LocalDateTime tanggal = LocalDateTime.now(); // Tanggal dan waktu saat ini

        if (judul.isEmpty() || deskripsi.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Judul dan konten pengumuman tidak boleh kosong.");
            return;
        }

        Pengumuman newPengumuman = new Pengumuman(0, judul, deskripsi, tanggal, lampiran); // ID 0 karena auto-generated
        if (pengumumanDAO.addPengumuman(newPengumuman)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Pengumuman berhasil diterbitkan!");
            loadAnnouncements();
            clearAnnouncementFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal menerbitkan pengumuman.");
        }
    }

    @FXML
    private void handleUpdateSelectedAnnouncement(ActionEvent event) {
        Pengumuman selectedPengumuman = announcementTable.getSelectionModel().getSelectedItem();
        if (selectedPengumuman == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih pengumuman yang akan diperbarui.");
            return;
        }

        String judul = announcementTitleField.getText();
        String deskripsi = announcementContentArea.getText();
        String lampiran = announcementAttachmentField.getText();
        LocalDateTime tanggal = LocalDateTime.now(); // Update tanggal ke waktu sekarang jika ingin

        if (judul.isEmpty() || deskripsi.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Judul dan konten pengumuman tidak boleh kosong.");
            return;
        }

        selectedPengumuman.setJudul(judul);
        selectedPengumuman.setDeskripsi(deskripsi);
        selectedPengumuman.setLampiran(lampiran);
        selectedPengumuman.setTanggal(tanggal); // Perbarui tanggal

        if (pengumumanDAO.updatePengumuman(selectedPengumuman)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Pengumuman berhasil diperbarui!");
            loadAnnouncements();
            clearAnnouncementFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memperbarui pengumuman.");
        }
    }

    @FXML
    private void handleDeleteSelectedAnnouncement(ActionEvent event) {
        Pengumuman selectedPengumuman = announcementTable.getSelectionModel().getSelectedItem();
        if (selectedPengumuman == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih pengumuman yang akan dihapus.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus pengumuman ini?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (pengumumanDAO.deletePengumuman(selectedPengumuman.getIdPengumuman())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Pengumuman berhasil dihapus!");
                loadAnnouncements();
                clearAnnouncementFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus pengumuman.");
            }
        }
    }

    @FXML
    private void handleAddStudentData(ActionEvent event) {
        String nis = nisInputField.getText();
        String nama = studentNameInputField.getText();
        String jenisKelamin = genderComboBox.getSelectionModel().getSelectedItem();
        String tempatLahir = placeOfBirthInputField.getText();
        LocalDate tanggalLahir = dateOfBirthPicker.getValue();
        String alamat = addressTextArea.getText();

        if (nis.isEmpty() || nama.isEmpty() || jenisKelamin == null || tempatLahir.isEmpty() || tanggalLahir == null || alamat.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua field data siswa harus diisi.");
            return;
        }

        Siswa newSiswa = new Siswa(nis, nama, jenisKelamin, tempatLahir, tanggalLahir, alamat);
        if (siswaDao.addSiswa(newSiswa)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data siswa berhasil ditambahkan!");
            clearAddStudentFields();
            loadAllStudents(); // Refresh student list

            // NEW: Auto-fill INPUT STUDENT USER fields
            studentUsernameInputField.setText(nis);
            if (tanggalLahir != null) {
                // Format tanggal lahir ke DD/MM/YYYY
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                studentPasswordInputField.setText(tanggalLahir.format(formatter));
            } else {
                studentPasswordInputField.clear();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal menambahkan data siswa. NIS mungkin sudah ada.");
        }
    }

    @FXML
    private void handleAddStudentUser(ActionEvent event) {
        String username = studentUsernameInputField.getText();
        String password = studentPasswordInputField.getText(); // In a real app, hash this!
        int roleId = 3; // Role ID for Siswa (assuming 3 is siswa)

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Username dan password tidak boleh kosong.");
            return;
        }

        // Cek apakah NIS siswa sudah ada di database sebelum membuat user
        Siswa existingSiswa = siswaDao.getSiswaByNis(username); // Username dianggap NIS
        if (existingSiswa == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Siswa dengan NIS tersebut tidak ditemukan.");
            return;
        }
        if (existingSiswa.getIdUser() != null && existingSiswa.getIdUser() != 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Siswa ini sudah memiliki akun pengguna.");
            return;
        }

        int newUserId = userDao.createNewUser(username, password, roleId);
        if (newUserId != -1) {
            // Update siswa dengan id_user yang baru
            existingSiswa.setIdUser(newUserId);
            if (siswaDao.updateSiswaUser(existingSiswa)) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Akun pengguna siswa berhasil ditambahkan!");
                clearAddStudentUserFields();
                loadAllStudents(); // Refresh student list to show user info
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal mengaitkan akun pengguna ke siswa.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal membuat akun pengguna. Username mungkin sudah ada.");
        }
    }

    @FXML
    private void handleUpdateStudentData(ActionEvent event) {
        Siswa selectedSiswa = selectNisEditComboBox.getSelectionModel().getSelectedItem();
        if (selectedSiswa == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih siswa yang akan diperbarui.");
            return;
        }

        String nis = nisEditField.getText(); // NIS tidak boleh berubah
        String nama = nameEditField.getText();
        String jenisKelamin = genderEditComboBox.getSelectionModel().getSelectedItem();
        String tempatLahir = placeOfBirthEditField.getText();
        LocalDate tanggalLahir = dateOfBirthEditPicker.getValue();
        String alamat = addressEditArea.getText();

        if (nama.isEmpty() || jenisKelamin == null || tempatLahir.isEmpty() || tanggalLahir == null || alamat.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua field data siswa harus diisi.");
            return;
        }

        selectedSiswa.setNama(nama);
        selectedSiswa.setJenisKelamin(jenisKelamin);
        selectedSiswa.setTempatLahir(tempatLahir);
        selectedSiswa.setTanggalLahir(tanggalLahir);
        selectedSiswa.setAlamat(alamat);

        if (siswaDao.updateSiswa(selectedSiswa)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data siswa berhasil diperbarui!");
            loadAllStudents();
            clearEditStudentFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memperbarui data siswa.");
        }
    }

    @FXML
    private void handleDeleteStudentData(ActionEvent event) {
        Siswa selectedSiswa = selectNisEditComboBox.getSelectionModel().getSelectedItem();
        if (selectedSiswa == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih siswa yang akan dihapus.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus data siswa ini?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (siswaDao.deleteSiswa(selectedSiswa.getNis())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data siswa berhasil dihapus!");
                loadAllStudents();
                clearEditStudentFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus data siswa.");
            }
        }
    }

    @FXML
    private void handleSearchStudent(ActionEvent event) {
        String keyword = searchStudentField.getText();
        if (keyword.isEmpty()) {
            loadAllStudents();
        } else {
            studentList.setAll(siswaDao.searchSiswa(keyword));
        }
    }

    @FXML
    private void handleSearchTeacher(ActionEvent event) {
        String keyword = searchTeacherField.getText();
        if (keyword.isEmpty()) {
            loadAllTeachers();
        } else {
            teacherList.setAll(guruDao.searchGuru(keyword));
        }
    }

    @FXML
    private void handleDeleteSelectedTeacher(ActionEvent event) {
        Guru selectedGuru = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedGuru == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih guru yang akan dihapus.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus data guru ini? Ini juga akan menghapus akun pengguna terkait.");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (guruDao.deleteGuru(selectedGuru.getNip())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data guru berhasil dihapus!");
                loadAllTeachers();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus data guru.");
            }
        }
    }

    // NEW: Handle Delete Schedule
    @FXML
    private void handleDeleteSchedule(ActionEvent event) {
        JadwalKelas selectedJadwal = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedJadwal == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih jadwal yang akan dihapus dari tabel.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus jadwal ini?\n" +
                "Hari: " + selectedJadwal.getHari() + "\n" +
                "Waktu: " + selectedJadwal.getTimeRange() + "\n" +
                "Mata Pelajaran: " + selectedJadwal.getNamaMapel() + "\n" +
                "Kelas: " + selectedJadwal.getKelasDanTahunAjaran());
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (jadwalKelasDao.deleteJadwalKelas(selectedJadwal.getIdJadwalKelas())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Jadwal berhasil dihapus!");
                loadSchedules(); // Refresh schedule table
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus jadwal.");
            }
        }
    }

    @FXML
    private void handleAddSchedule(ActionEvent event) {
        String hari = dayChoiceBox.getSelectionModel().getSelectedItem();
        String startTimeStr = startTimeField.getText();
        String endTimeStr = endTimeField.getText();
        MataPelajaran selectedMapel = subjectChoiceBox.getSelectionModel().getSelectedItem();
        Kelas selectedKelas = classChoiceBox.getSelectionModel().getSelectedItem();
        Guru selectedGuru = teacherChoiceBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedTahunAjaran = schoolYearScheduleComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = semesterScheduleComboBox.getSelectionModel().getSelectedItem(); // NEW: Ambil semester

        if (hari == null || startTimeStr.isEmpty() || endTimeStr.isEmpty() || selectedMapel == null ||
                selectedKelas == null || selectedGuru == null || selectedTahunAjaran == null || selectedSemester == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua field jadwal harus diisi.");
            return;
        }

        try {
            LocalTime jamMulai = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime jamSelesai = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("HH:mm"));

            // Check if end time is after start time
            if (jamSelesai.isBefore(jamMulai) || jamSelesai.equals(jamMulai)) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Jam selesai harus setelah jam mulai.");
                return;
            }

            // UPDATED: Teruskan nilai semester ke constructor JadwalKelas
            JadwalKelas newJadwal = new JadwalKelas(0, hari, jamMulai, jamSelesai, selectedKelas.getIdKelas(), selectedMapel.getIdMapel(), selectedGuru.getNip(), selectedSemester);

            if (jadwalKelasDao.addJadwalKelas(newJadwal)) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Jadwal berhasil ditambahkan!");
                clearAddScheduleFields();
                loadSchedules(); // Refresh schedule table
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menambahkan jadwal.");
            }

        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Format Waktu Salah", "Format waktu harus HH:MM (contoh: 08:00).");
        }

//        String hari = dayChoiceBox.getSelectionModel().getSelectedItem();
//        String startTimeStr = startTimeField.getText();
//        String endTimeStr = endTimeField.getText();
//        MataPelajaran selectedMapel = subjectChoiceBox.getSelectionModel().getSelectedItem();
//        Kelas selectedKelas = classChoiceBox.getSelectionModel().getSelectedItem();
//        Guru selectedGuru = teacherChoiceBox.getSelectionModel().getSelectedItem();
//        TahunAjaran selectedTahunAjaran = schoolYearScheduleComboBox.getSelectionModel().getSelectedItem();
//        String selectedSemester = semesterScheduleComboBox.getSelectionModel().getSelectedItem();
//
//        if (hari == null || startTimeStr.isEmpty() || endTimeStr.isEmpty() || selectedMapel == null ||
//                selectedKelas == null || selectedGuru == null || selectedTahunAjaran == null || selectedSemester == null) {
//            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua field jadwal harus diisi.");
//            return;
//        }
//
//        try {
//            LocalTime jamMulai = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
//            LocalTime jamSelesai = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
//
//            // Check if end time is after start time
//            if (jamSelesai.isBefore(jamMulai) || jamSelesai.equals(jamMulai)) {
//                showAlert(Alert.AlertType.ERROR, "Input Error", "Jam selesai harus setelah jam mulai.");
//                return;
//            }
//
//            JadwalKelas newJadwal = new JadwalKelas(0, selectedKelas.getIdKelas(), selectedMapel.getIdMapel(),
//                    selectedGuru.getNip(), hari, jamMulai, jamSelesai);
//            // In JadwalKelas DAO, ensure that tahun_ajaran and semester are handled
//            // However, JadwalKelas table in DDL does not have year/semester. This logic might need adjustment.
//            // If JadwalKelas is specific to a year/semester, these should be FKs in JadwalKelas table.
//            // For now, I'll add them to the constructor for future extensibility or if your DAO handles them.
//            // But based on DDL, JadwalKelas is generic. So the model above is correct.
//
//            if (jadwalKelasDao.addJadwalKelas(newJadwal)) {
//                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Jadwal berhasil ditambahkan!");
//                clearAddScheduleFields();
//                // TODO: Refresh jadwal table if there is one
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menambahkan jadwal.");
//            }
//
//        } catch (DateTimeParseException e) {
//            showAlert(Alert.AlertType.ERROR, "Format Waktu Salah", "Format waktu harus HH:MM (contoh: 08:00).");
//        }

//        String hari = dayChoiceBox.getSelectionModel().getSelectedItem();
//        String startTimeStr = startTimeField.getText();
//        String endTimeStr = endTimeField.getText();
//        MataPelajaran selectedMapel = subjectChoiceBox.getSelectionModel().getSelectedItem();
//        Kelas selectedKelas = classChoiceBox.getSelectionModel().getSelectedItem();
//        Guru selectedGuru = teacherChoiceBox.getSelectionModel().getSelectedItem();
//        TahunAjaran selectedTahunAjaran = schoolYearScheduleComboBox.getSelectionModel().getSelectedItem();
//        String selectedSemester = semesterScheduleComboBox.getSelectionModel().getSelectedItem();
//
//        if (hari == null || startTimeStr.isEmpty() || endTimeStr.isEmpty() || selectedMapel == null ||
//                selectedKelas == null || selectedGuru == null || selectedTahunAjaran == null || selectedSemester == null) {
//            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua field jadwal harus diisi.");
//            return;
//        }
//
//        try {
//            LocalTime jamMulai = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
//            LocalTime jamSelesai = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
//
//            // Check if end time is after start time
//            if (jamSelesai.isBefore(jamMulai) || jamSelesai.equals(jamMulai)) {
//                showAlert(Alert.AlertType.ERROR, "Input Error", "Jam selesai harus setelah jam mulai.");
//                return;
//            }
//
//            JadwalKelas newJadwal = new JadwalKelas(0, hari, jamMulai, jamSelesai, selectedKelas.getIdKelas(), selectedMapel.getIdMapel(), selectedGuru.getNip());
//
//            if (jadwalKelasDao.addJadwalKelas(newJadwal)) {
//                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Jadwal berhasil ditambahkan!");
//                clearAddScheduleFields();
//                loadSchedules(); // Refresh schedule table
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menambahkan jadwal.");
//            }
//
//        } catch (DateTimeParseException e) {
//            showAlert(Alert.AlertType.ERROR, "Format Waktu Salah", "Format waktu harus HH:MM (contoh: 08:00).");
//        }
    }

    @FXML
    private void handleCreateClass(ActionEvent event) {
        String namaKelas = classNameInputField.getText();
        Guru selectedWaliKelas = homeroomTeacherChoiceBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedTahunAjaran = schoolYearCreateClassComboBox.getSelectionModel().getSelectedItem();
        // String selectedSemester = semesterCreateClassComboBox.getSelectionModel().getSelectedItem(); // Removed from FXML

        if (namaKelas.isEmpty() || selectedWaliKelas == null || selectedTahunAjaran == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua field kelas baru harus diisi.");
            return;
        }

        // Kelas model does not have semester directly
        Kelas newKelas = new Kelas(0, namaKelas, "General", selectedWaliKelas.getNip(), selectedTahunAjaran.getIdTahunAjaran()); // "General" for tingkat, adjust if needed
        if (kelasDao.addKelas(newKelas)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Kelas berhasil dibuat!");
            clearCreateClassFields();
            loadKelasIntoComboBoxes(); // Refresh class comboboxes
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal membuat kelas. Nama kelas mungkin sudah ada untuk tahun ajaran ini.");
        }
    }

    @FXML
    private void handleUpdateClass(ActionEvent event) {
        Kelas selectedKelas = classNameEditComboBox.getSelectionModel().getSelectedItem();
        if (selectedKelas == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih kelas yang akan diperbarui.");
            return;
        }

        Guru selectedWaliKelas = homeroomTeacherEditComboBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedTahunAjaran = schoolYearEditClassComboBox.getSelectionModel().getSelectedItem();
        // String selectedSemester = semesterEditClassComboBox.getSelectionModel().getSelectedItem(); // Removed from FXML

        if (selectedWaliKelas == null || selectedTahunAjaran == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Wali kelas dan tahun ajaran harus diisi.");
            return;
        }

        selectedKelas.setNipWaliKelas(selectedWaliKelas.getNip());
        selectedKelas.setIdTahunAjaran(selectedTahunAjaran.getIdTahunAjaran());
        // selectedKelas.setSemester(selectedSemester); // No semester in Kelas model

        if (kelasDao.updateKelas(selectedKelas)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Kelas berhasil diperbarui!");
            clearEditClassFields();
            loadKelasIntoComboBoxes();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memperbarui kelas.");
        }
    }

    @FXML
    private void handleDeleteClass(ActionEvent event) {
        Kelas selectedKelas = classNameEditComboBox.getSelectionModel().getSelectedItem();
        if (selectedKelas == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih kelas yang akan dihapus.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus kelas ini? Ini akan memutuskan kaitan siswa yang ditugaskan ke kelas ini.");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (kelasDao.deleteKelas(selectedKelas.getIdKelas())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Kelas berhasil dihapus!");
                clearEditClassFields();
                loadKelasIntoComboBoxes();
                loadAllStudents(); // Refresh student list because their class might be null now
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus kelas.");
            }
        }
    }

    @FXML
    private void handleSearchStudentToAssign() {
        String keyword = searchStudentAssignClassField.getText();
        if (keyword.isEmpty()) {
            studentsToAssignTable.setItems(studentList); // Show all students if search is empty
        } else {
            ObservableList<Siswa> filteredStudents = FXCollections.observableArrayList();
            for (Siswa siswa : studentList) {
                if (siswa.getNis().toLowerCase().contains(keyword.toLowerCase()) ||
                        siswa.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredStudents.add(siswa);
                }
            }
            studentsToAssignTable.setItems(filteredStudents);
        }
    }


    @FXML
    private void handleAssignStudent(ActionEvent event) {
        Siswa selectedStudent = studentsToAssignTable.getSelectionModel().getSelectedItem();
        Kelas selectedClass = classAssignComboBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedTahunAjaran = schoolYearAssignComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = semesterAssignComboBox.getSelectionModel().getSelectedItem();

        if (selectedStudent == null || selectedClass == null || selectedTahunAjaran == null || selectedSemester == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Pilih siswa, kelas, tahun ajaran, dan semester.");
            return;
        }

        if (siswaDao.assignStudentToClass(selectedStudent.getNis(), selectedClass.getIdKelas(), selectedTahunAjaran.getIdTahunAjaran(), selectedSemester)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil ditugaskan ke kelas!");
            loadAllStudents(); // Refresh all student data
            loadStudentsInSelectedClass(); // Refresh students in selected class if any is chosen
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal menugaskan siswa ke kelas.");
        }
    }

    @FXML
    private void handleEditSelectedStudentInClass(ActionEvent event) {
        Siswa selectedStudent = studentsInSelectedClassTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih siswa yang akan diedit.");
            return;
        }
        // This is a placeholder for actual edit functionality.
        // You would typically open the "Entry Student Data" tab and populate its "Edit Student Data" fields.
        //showAlert(Alert.AlertType.INFORMATION, "Fitur Belum Tersedia", "Fungsi edit siswa dari tabel ini belum diimplementasikan. Silakan gunakan tab 'Entry Student Data' untuk mengedit siswa.");

        // Optional: Programmatically switch tab and select the student in the edit combo box
         mainTabPane.getSelectionModel().select(entryStudentDataTab);
         selectNisEditComboBox.getSelectionModel().select(selectedStudent);
    }

    @FXML
    private void handleRemoveStudentFromClass(ActionEvent event) {
        Siswa selectedStudent = studentsInSelectedClassTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih siswa yang akan dihapus dari kelas.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus siswa ini dari kelas?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (siswaDao.removeClassInfoFromStudent(selectedStudent.getNis())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil dihapus dari kelas!");
                loadAllStudents(); // Refresh all student data
                loadStudentsInSelectedClass(); // Refresh current class view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus siswa dari kelas.");
            }
        }
    }


    @FXML
    private void handleAddSchoolAgenda(ActionEvent event) {
        String judul = agendaContentField.getText();
        LocalDate tanggalMulai = agendaStartDatePicker.getValue();
        LocalDate tanggalSelesai = agendaEndDatePicker.getValue();
        TahunAjaran selectedTahunAjaran = schoolYearAgendaAddComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = semesterAgendaAddComboBox.getSelectionModel().getSelectedItem();

        if (judul.isEmpty() || tanggalMulai == null || tanggalSelesai == null || selectedTahunAjaran == null || selectedSemester == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua field agenda sekolah harus diisi.");
            return;
        }
        if (tanggalSelesai.isBefore(tanggalMulai)) {
            showAlert(Alert.AlertType.ERROR, "Tanggal Salah", "Tanggal selesai harus setelah tanggal mulai.");
            return;
        }

        AgendaSekolah newAgenda = new AgendaSekolah(0, judul, "", tanggalMulai, tanggalSelesai, selectedTahunAjaran.getIdTahunAjaran(), selectedSemester); // deskripsi bisa kosong atau diambil dari judul
        if (agendaSekolahDao.addAgendaSekolah(newAgenda)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Agenda sekolah berhasil ditambahkan!");
            clearAddSchoolAgendaFields();
            loadSchoolAgenda(); // Reload agenda table
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal menambahkan agenda sekolah.");
        }
    }

    @FXML
    private void handleDeleteSchoolAgenda(ActionEvent event) {
        AgendaSekolah selectedAgenda = agendaTable.getSelectionModel().getSelectedItem();
        if (selectedAgenda == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih agenda yang akan dihapus.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus agenda ini?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (agendaSekolahDao.deleteAgendaSekolah(selectedAgenda.getIdAgendaSekolah())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Agenda sekolah berhasil dihapus!");
                loadSchoolAgenda(); // Reload agenda table
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus agenda sekolah.");
            }
        }
    }

    @FXML
    private void handleAddMentor(ActionEvent event) {
        Ekstrakurikuler selectedEkskul = extracurricularInputMentorComboBox.getSelectionModel().getSelectedItem();
        Guru mentor1 = mentor1ComboBox.getSelectionModel().getSelectedItem();
        Guru mentor2 = mentor2ComboBox.getSelectionModel().getSelectedItem();
        Guru mentor3 = mentor3ComboBox.getSelectionModel().getSelectedItem();
        Guru mentor4 = mentor4ComboBox.getSelectionModel().getSelectedItem();
        // String level = levelInputMentorComboBox.getSelectionModel().getSelectedItem(); // This is the level of the ekskul itself

        if (selectedEkskul == null || (mentor1 == null && mentor2 == null && mentor3 == null && mentor4 == null)) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Pilih ekstrakurikuler dan setidaknya satu mentor.");
            return;
        }

        boolean success = true;
        // Collect all selected mentors
        List<Guru> selectedMentors = new java.util.ArrayList<>();
        if (mentor1 != null) selectedMentors.add(mentor1);
        if (mentor2 != null && !selectedMentors.contains(mentor2)) selectedMentors.add(mentor2);
        if (mentor3 != null && !selectedMentors.contains(mentor3)) selectedMentors.add(mentor3);
        if (mentor4 != null && !selectedMentors.contains(mentor4)) selectedMentors.add(mentor4);

        for (Guru mentor : selectedMentors) {
            Pembina newPembina = new Pembina(0, mentor.getNip(), selectedEkskul.getIdEkstrakurikuler());
            if (!pembinaDao.addPembina(newPembina)) {
                success = false;
                System.err.println("Gagal menambahkan pembina " + mentor.getNama() + " untuk " + selectedEkskul.getNama());
            }
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Mentor berhasil ditambahkan ke ekstrakurikuler!");
            clearAddMentorFields();
            loadEkstrakurikulerIntoComboBoxes(); // Refresh extracurricular table
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Beberapa mentor mungkin gagal ditambahkan atau sudah menjadi pembina untuk ekskul ini.");
        }
    }

    @FXML
    private void handleDeleteExtracurricular(ActionEvent event) {
        Ekstrakurikuler selectedEkskul = extracurricularTable.getSelectionModel().getSelectedItem();
        if (selectedEkskul == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih ekstrakurikuler yang akan dihapus.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus ekstrakurikuler ini dan semua pembina serta pesertanya?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (ekstrakurikulerDao.deleteEkstrakurikuler(selectedEkskul.getIdEkstrakurikuler())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Ekstrakurikuler berhasil dihapus!");
                loadEkstrakurikulerIntoComboBoxes(); // Refresh extracurricular table
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus ekstrakurikuler.");
            }
        }
    }


    @FXML
    private void handleAddStudentToExtracurricular(ActionEvent event) {
        Siswa selectedSiswa = studentInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();
        Ekstrakurikuler selectedEkskul = extracurricularInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();

        if (selectedSiswa == null || selectedEkskul == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Pilih siswa dan ekstrakurikuler.");
            return;
        }

        PesertaEkskul newPeserta = new PesertaEkskul(0, selectedSiswa.getNis(), selectedEkskul.getIdEkstrakurikuler()); // ID 0 for auto-generated
        if (pesertaEkskulDao.addPesertaEkskul(newPeserta)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil ditambahkan ke ekstrakurikuler!");
            clearAddStudentToExtracurricularFields(); // Clear inputs
            loadStudentsInExtracurricularTable(); // Refresh table for the selected extracurricular
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal menambahkan siswa ke ekstrakurikuler. Siswa mungkin sudah terdaftar.");
        }
    }

    @FXML
    private void handleRemoveStudentFromExtracurricular(ActionEvent event) {
//        PesertaEkskul selectedPeserta = studentsInExtracurricularTable.getSelectionModel().getSelectedItem();
//        if (selectedPeserta == null) {
//            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih siswa dari tabel untuk dihapus dari ekstrakurikuler.");
//            return;
//        }
//
//        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus siswa ini dari ekstrakurikuler?");
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            if (pesertaEkskulDao.deletePesertaEkskul(selectedPeserta.getIdPeserta())) {
//                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil dihapus dari ekstrakurikuler!");
//                // Refresh table based on current filter or all if no filter applied
//                Ekstrakurikuler currentSelectedEkskul = extracurricularInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();
//                if (currentSelectedEkskul != null) {
//                    loadStudentsInExtracurricularTable();
//                } else {
//                    loadStudentsInExtracurricularTable();
//                }
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus siswa dari ekstrakurikuler.");
//            }
//        }

        Siswa selectedStudent = studentsInExtracurricularTable.getSelectionModel().getSelectedItem();
        Ekstrakurikuler selectedEkskul = extracurricularInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();

        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih siswa dari tabel yang akan dihapus dari ekstrakurikuler.");
            return;
        }
        if (selectedEkskul == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ekstrakurikuler belum dipilih.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Konfirmasi Hapus", "Anda yakin ingin menghapus siswa '" + selectedStudent.getNama() + "' dari ekstrakurikuler '" + selectedEkskul.getNama() + "'?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Panggil DAO untuk menghapus peserta ekskul
            if (pesertaEkskulDao.deletePesertaEkskulByNisAndEkskulId(selectedStudent.getNis(), selectedEkskul.getIdEkstrakurikuler())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil dihapus dari ekstrakurikuler!");
                loadStudentsInExtracurricularTable(); // Refresh the table
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus siswa dari ekstrakurikuler.");
            }
        }
    }


    // --- Generic Alert Helper ---
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    /*
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userDao = new UserDAO();
        siswaDao = new SiswaDAO();
        tahunAjaranDao = new TahunAjaranDAO();
        kelasDao = new KelasDAO();
        guruDao = new GuruDAO();
        mataPelajaranDao = new MataPelajaranDAO(); // Perbaikan nama class
        jadwalKelasDao = new JadwalKelasDAO();
        pengumumanDAO = new PengumumanDAO();
        agendaSekolahDao = new AgendaSekolahDAO();
        ekstrakurikulerDao = new EkstrakurikulerDAO();
        pembinaDao = new PembinaDAO();
        pesertaEkskulDao = new PesertaEkskulDAO();

        User loggedInUser = SessionManager.getLoggedInUser();

        if (loggedInUser != null) {
            welcome.setText("Welcome, " + loggedInUser.getUsername() + "!");
        } else {
            welcome.setText("Welcome, Guest!");
        }

        // --- Inisialisasi ComboBox dengan nilai statis/predefined ---
        genderComboBox.setItems(FXCollections.observableArrayList("Laki-laki", "Perempuan"));
        genderEditComboBox.setItems(FXCollections.observableArrayList("Laki-laki", "Perempuan"));
        semesterScheduleComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));
        semesterCreateClassComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));
        semesterEditClassComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));
        semesterAssignComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));
        semesterStudentsInSelectedClassComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));
        semesterAgendaAddComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));
        semesterAgendaViewComboBox.setItems(FXCollections.observableArrayList("Ganjil", "Genap"));
        levelInputMentorComboBox.setItems(FXCollections.observableArrayList("Siaga", "Penggalang"));
        levelInputSiswaEkskulComboBox.setItems(FXCollections.observableArrayList("Siaga", "Penggalang"));
        dayChoiceBox.setItems(FXCollections.observableArrayList("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"));

        // --- Pemuatan data awal untuk ComboBox dan Tabel ---
        loadCommonData();
        setupAnnouncementTable();
        loadAnnouncements();
        setupStudentTable();
        loadStudents();
        setupTeacherTable();
        loadTeachers();
        setupAssignStudentTables();
        setupStudentsInSelectedClassTable();
        setupAgendaTable();
        loadSchoolAgenda();
        setupExtracurricularTable();
        loadExtracurriculars();
        loadStudentsIntoAssignClassCombos();

        // --- Menambahkan Listener untuk pemuatan dinamis ---
        searchStudentField.setOnAction(this::handleSearchStudent);
        searchTeacherField.setOnAction(this::handleSearchTeacher);
        searchStudentAssignClassField.setOnAction(this::handleSearchStudentToAssign);

        selectNisEditComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                populateEditStudentFields(newVal);
            }
        });

        // Listeners for "STUDENT IN SELECTED CLASS" section
        // Memastikan semua ComboBox telah dipilih sebelum memuat data
        classStudentsInSelectedClassComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && schoolYearStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem() != null &&
                    semesterStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem() != null) {
                loadStudentsInSelectedClass();
            } else {
                studentsInSelectedClassTable.setItems(FXCollections.emptyObservableList());
            }
        });
        schoolYearStudentsInSelectedClassComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && classStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem() != null &&
                    semesterStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem() != null) {
                loadStudentsInSelectedClass();
            } else {
                studentsInSelectedClassTable.setItems(FXCollections.emptyObservableList());
            }
        });
        semesterStudentsInSelectedClassComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && classStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem() != null &&
                    schoolYearStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem() != null) {
                loadStudentsInSelectedClass();
            } else {
                studentsInSelectedClassTable.setItems(FXCollections.emptyObservableList());
            }
        });


        // Listener for EDIT/DELETE CLASS
        classNameEditComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                populateEditClassFields(newVal);
            } else {
                clearEditClassFields();
            }
        });

        // Listeners untuk filter Agenda Sekolah
        schoolYearAgendaViewComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadSchoolAgenda());
        semesterAgendaViewComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadSchoolAgenda());

        // Listener untuk pemilihan pengumuman di tabel
        announcementTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                announcementTitleField.setText(newSelection.getJudul());
                announcementContentArea.setText(newSelection.getDeskripsi());
                announcementAttachmentField.setText(newSelection.getLampiran());
            } else {
                clearAnnouncementFields();
            }
        });

        // Listener untuk ekstrakurikuler di bagian 'INPUT SISWA to Extracurricular'
        extracurricularInputSiswaEkskulComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadStudentsInExtracurricularTable();
            } else {
                studentsInExtracurricularTable.setItems(FXCollections.emptyObservableList());
            }
        });
    }

    @FXML
    public void actionLogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectdouble/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            SessionManager.clearSession();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Logout Gagal", "Terjadi kesalahan saat logout.");
        }
    }

    private String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        StringBuilder titleCase = new StringBuilder();
        boolean nextCharUpperCase = true;
        for (char ch : input.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                nextCharUpperCase = true;
            } else if (nextCharUpperCase) {
                ch = Character.toTitleCase(ch);
                nextCharUpperCase = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            titleCase.append(ch);
        }
        return titleCase.toString();
    }

    // --- Methods for Announcements Tab ---
    @FXML
    private void handleChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih File Lampiran");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            announcementAttachmentField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handlePublishAnnouncement(ActionEvent event) {
        String judul = announcementTitleField.getText();
        String deskripsi = announcementContentArea.getText();
        String lampiran = announcementAttachmentField.getText();
        LocalDateTime tanggal = LocalDateTime.now();

        if (judul.isEmpty() || deskripsi.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Judul dan Deskripsi pengumuman tidak boleh kosong.");
            return;
        }

        Pengumuman newPengumuman = new Pengumuman(0, judul, deskripsi, tanggal, lampiran); // Menggunakan LocalDateTime langsung

        if (pengumumanDao.addPengumuman(newPengumuman)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Pengumuman berhasil dipublikasikan.");
            clearAnnouncementFields();
            loadAnnouncements();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal mempublikasikan pengumuman.");
        }
    }

    @FXML
    private void handleUpdateSelectedAnnouncement(ActionEvent event) {
        Pengumuman selectedPengumuman = announcementTable.getSelectionModel().getSelectedItem();
        if (selectedPengumuman == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Pengumuman", "Pilih pengumuman yang ingin diperbarui dari tabel.");
            return;
        }

        String judul = announcementTitleField.getText();
        String deskripsi = announcementContentArea.getText();
        String lampiran = announcementAttachmentField.getText();
        LocalDateTime tanggal = LocalDateTime.now();

        if (judul.isEmpty() || deskripsi.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Judul dan Deskripsi pengumuman tidak boleh kosong.");
            return;
        }

        selectedPengumuman.setJudul(judul);
        selectedPengumuman.setDeskripsi(deskripsi);
        selectedPengumuman.setLampiran(lampiran);
        selectedPengumuman.setTanggal(tanggal); // Menggunakan LocalDateTime langsung

        if (pengumumanDao.updatePengumuman(selectedPengumuman)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Pengumuman berhasil diperbarui.");
            clearAnnouncementFields();
            loadAnnouncements();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui pengumuman.");
        }
    }

    @FXML
    private void handleDeleteSelectedAnnouncement(ActionEvent event) {
        Pengumuman selectedPengumuman = announcementTable.getSelectionModel().getSelectedItem();
        if (selectedPengumuman == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Pengumuman", "Pilih pengumuman yang ingin dihapus dari tabel.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus pengumuman ini?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (pengumumanDao.deletePengumuman(selectedPengumuman.getIdPengumuman())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Pengumuman berhasil dihapus.");
                clearAnnouncementFields();
                loadAnnouncements();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus pengumuman.");
            }
        }
    }

    private void setupAnnouncementTable() {
        colAnnouncementTitle.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colAnnouncementDate.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colAnnouncementContent.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        colAnnouncementAttachment.setCellValueFactory(new PropertyValueFactory<>("lampiran"));
    }

    private void loadAnnouncements() {
        ObservableList<Pengumuman> announcements = FXCollections.observableArrayList(pengumumanDao.getAllPengumuman());
        announcementTable.setItems(announcements);
        announcementTable.refresh();
    }

    private void clearAnnouncementFields() {
        if (announcementTitleField != null) announcementTitleField.clear();
        if (announcementContentArea != null) announcementContentArea.clear();
        if (announcementAttachmentField != null) announcementAttachmentField.clear();
        if (announcementTable != null) announcementTable.getSelectionModel().clearSelection();
    }

    // --- Methods for Entry Student Data Tab ---
    @FXML
    private void handleAddStudentData(ActionEvent event) {
        String nis = nisInputField.getText();
        String nama = studentNameInputField.getText();
        String jenisKelamin = genderComboBox.getSelectionModel().getSelectedItem();
        String tempatLahir = placeOfBirthInputField.getText();
        LocalDate tanggalLahir = dateOfBirthPicker.getValue();
        String alamat = addressTextArea.getText();

        if (nis.isEmpty() || nama.isEmpty() || jenisKelamin == null || tempatLahir.isEmpty() || tanggalLahir == null || alamat.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua kolom data siswa harus diisi.");
            return;
        }

        nama = toTitleCase(nama);
        tempatLahir = toTitleCase(tempatLahir);

        Siswa newSiswa = new Siswa(nis, nama, jenisKelamin, tempatLahir, tanggalLahir, alamat);
        if (siswaDao.addSiswa(newSiswa)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data siswa berhasil ditambahkan.");
            clearAddStudentFields();
            loadStudents();
            loadComboBoxesForStudentEdit();
            loadStudentsIntoAssignClassCombos();

            studentUsernameInputField.setText(nis);
            studentPasswordInputField.setText(tanggalLahir.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menambahkan data siswa. NIS mungkin sudah ada atau terjadi kesalahan database.");
        }
    }

    @FXML
    private void handleAddStudentUser(ActionEvent event) {
        String username = studentUsernameInputField.getText();
        String passwordString = studentPasswordInputField.getText();

        if (username.isEmpty() || passwordString.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Username (NIS) dan Password (DD/MM/YYYY) harus diisi.");
            return;
        }

        LocalDate passwordDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            passwordDate = LocalDate.parse(passwordString, formatter);
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Format Password Salah", "Password harus dalam format DD/MM/YYYY.");
            return;
        }

        Siswa existingSiswa = siswaDao.getSiswaByNis(username);
        if (existingSiswa == null) {
            showAlert(Alert.AlertType.ERROR, "Siswa Tidak Ditemukan", "NIS yang dimasukkan tidak terdaftar sebagai siswa.");
            return;
        }

        if (existingSiswa.getIdUser() != null && existingSiswa.getIdUser() != 0) {
            showAlert(Alert.AlertType.WARNING, "User Sudah Ada", "Siswa dengan NIS ini sudah memiliki akun user.");
            return;
        }

        if (!existingSiswa.getTanggalLahir().equals(passwordDate)) {
            showAlert(Alert.AlertType.ERROR, "Password Tidak Cocok", "Password (Tanggal Lahir) tidak cocok dengan tanggal lahir siswa.");
            return;
        }

        int siswaRoleId = -1;
        List<Role> roles = userDao.getAllRoles();
        for (Role r : roles) {
            if (r.getNamaRole().equalsIgnoreCase("Siswa")) {
                siswaRoleId = r.getIdRole();
                break;
            }
        }

        if (siswaRoleId == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Role 'Siswa' tidak ditemukan di database. Harap periksa data role.");
            return;
        }

        int newUserId = userDao.createNewUser(username, passwordString, siswaRoleId);

        if (newUserId != -1) {
            existingSiswa.setIdUser(newUserId);
            existingSiswa.setUsernameUser(username);

            if (siswaDao.updateSiswaUser(existingSiswa)) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "User siswa berhasil ditambahkan.");
                clearAddStudentUserFields();
                loadStudents();
                loadComboBoxesForStudentEdit();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal mengaitkan user dengan siswa.");
                userDao.deleteUser(newUserId);
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal membuat user siswa. Username (NIS) mungkin sudah ada.");
        }
    }

    @FXML
    private void handleUpdateStudentData(ActionEvent event) {
        Siswa selectedSiswa = selectNisEditComboBox.getSelectionModel().getSelectedItem();
        if (selectedSiswa == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Siswa", "Pilih siswa yang ingin diperbarui dari dropdown.");
            return;
        }

        String oldNis = selectedSiswa.getNis(); // Simpan NIS lama
        String nis = nisEditField.getText();
        String nama = nameEditField.getText();
        String jenisKelamin = genderEditComboBox.getSelectionModel().getSelectedItem();
        String tempatLahir = placeOfBirthEditField.getText();
        LocalDate tanggalLahir = dateOfBirthEditPicker.getValue();
        String alamat = addressEditArea.getText();

        if (nis.isEmpty() || nama.isEmpty() || jenisKelamin == null || tempatLahir.isEmpty() || tanggalLahir == null || alamat.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua kolom data siswa harus diisi.");
            return;
        }

        nama = toTitleCase(nama);
        tempatLahir = toTitleCase(tempatLahir);

        // Update objek Siswa yang dipilih dengan data baru
        selectedSiswa.setNis(nis); // NIS bisa berubah
        selectedSiswa.setNama(nama);
        selectedSiswa.setJenisKelamin(jenisKelamin);
        selectedSiswa.setTempatLahir(tempatLahir);
        selectedSiswa.setTanggalLahir(tanggalLahir);
        selectedSiswa.setAlamat(alamat);

        // Karena id_kelas, id_tahun_ajaran, semester, id_user, username_user, password_user
        // sudah ada di objek selectedSiswa (ditarik dari DAO),
        // maka updateSiswa(selectedSiswa) akan menyertakan data tersebut.
        // Jika Anda ingin mengubah info kelas dari sini, Anda harus menambahkan input FXML.
        // Saat ini, diasumsikan info kelas diupdate melalui bagian "Assign Student to Class".


        if (siswaDao.updateSiswa(selectedSiswa)) {
            // Jika NIS berubah, update username di tabel USER
            if (!oldNis.equals(nis)) {
                if (selectedSiswa.getIdUser() != null && selectedSiswa.getIdUser() != 0) {
                    userDao.updateUsername(selectedSiswa.getIdUser(), nis);
                }
            }
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data siswa berhasil diperbarui.");
            clearEditStudentFields();
            loadStudents();
            loadComboBoxesForStudentEdit();
            loadStudentsIntoAssignClassCombos();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui data siswa.");
        }
    }

    @FXML
    private void handleDeleteStudentData(ActionEvent event) {
        Siswa selectedSiswa = selectNisEditComboBox.getSelectionModel().getSelectedItem();
        if (selectedSiswa == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Siswa", "Pilih siswa yang ingin dihapus dari dropdown.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus data siswa ini?\nIni juga akan menghapus user login siswa yang terkait.", ButtonType.YES, ButtonType.NO);
        confirmAlert.getDialogPane().setPrefWidth(500);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (siswaDao.deleteSiswa(selectedSiswa.getNis())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data siswa berhasil dihapus.");
                clearEditStudentFields();
                loadStudents();
                loadComboBoxesForStudentEdit();
                loadStudentsIntoAssignClassCombos();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus data siswa.");
            }
        }
    }

    private void populateEditStudentFields(Siswa siswa) {
        if (siswa != null) {
            nisEditField.setText(siswa.getNis());
            nameEditField.setText(siswa.getNama());
            genderEditComboBox.getSelectionModel().select(siswa.getJenisKelamin());
            placeOfBirthEditField.setText(siswa.getTempatLahir());
            dateOfBirthEditPicker.setValue(siswa.getTanggalLahir());
            addressEditArea.setText(siswa.getAlamat());

            usernameEditLabel.setText(siswa.getUsernameUser() != null ? siswa.getUsernameUser() : "N/A");
            passwordEditLabel.setText(siswa.getPasswordUser() != null ? siswa.getPasswordUser() : "N/A");
        } else {
            clearEditStudentFields();
        }
    }

    private void clearAddStudentFields() {
        if (nisInputField != null) nisInputField.clear();
        if (studentNameInputField != null) studentNameInputField.clear();
        if (genderComboBox != null) genderComboBox.getSelectionModel().clearSelection();
        if (placeOfBirthInputField != null) placeOfBirthInputField.clear();
        if (dateOfBirthPicker != null) dateOfBirthPicker.setValue(null);
        if (addressTextArea != null) addressTextArea.clear();
    }

    private void clearAddStudentUserFields() {
        if (studentUsernameInputField != null) studentUsernameInputField.clear();
        if (studentPasswordInputField != null) studentPasswordInputField.clear();
    }

    private void clearEditStudentFields() {
        if (selectNisEditComboBox != null) selectNisEditComboBox.getSelectionModel().clearSelection();
        if (nisEditField != null) nisEditField.clear();
        if (nameEditField != null) nameEditField.clear();
        if (genderEditComboBox != null) genderEditComboBox.getSelectionModel().clearSelection();
        if (placeOfBirthEditField != null) placeOfBirthEditField.clear();
        if (dateOfBirthEditPicker != null) dateOfBirthEditPicker.setValue(null);
        if (addressEditArea != null) addressEditArea.clear();
        if (usernameEditLabel != null) usernameEditLabel.setText("");
        if (passwordEditLabel != null) passwordEditLabel.setText("");
    }

    private void loadComboBoxesForStudentEdit() {
        ObservableList<Siswa> allSiswa = FXCollections.observableArrayList(siswaDao.getAllSiswa());
        selectNisEditComboBox.setItems(allSiswa);
    }

    // --- Methods for View Students Tab ---
    @FXML
    private void handleSearchStudent(ActionEvent event) {
        String keyword = searchStudentField.getText();
        ObservableList<Siswa> searchResult = FXCollections.observableArrayList(siswaDao.searchSiswa(keyword));
        studentTable.setItems(searchResult);
    }

    private void setupStudentTable() {
        colStudentNis.setCellValueFactory(new PropertyValueFactory<>("nis"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colStudentGender.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        colStudentPlaceOfBirth.setCellValueFactory(new PropertyValueFactory<>("tempatLahir"));
        colStudentDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("tanggalLahir"));
        colStudentAddress.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colStudentClass.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getNamaKelas() != null ? cellData.getValue().getNamaKelas() : "N/A"
        ));
        colStudentSchoolYear.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getTahunAjaranLengkap() != null && !cellData.getValue().getTahunAjaranLengkap().isEmpty() ?
                        cellData.getValue().getTahunAjaranLengkap() + " " + cellData.getValue().getSemester() : "N/A"
        ));
    }

    private void loadStudents() {
        ObservableList<Siswa> students = FXCollections.observableArrayList(siswaDao.getAllSiswa());
        studentTable.setItems(students);
    }

    // --- Methods for View Teachers Tab ---
    @FXML
    private void handleSearchTeacher(ActionEvent event) {
        String keyword = searchTeacherField.getText();
        ObservableList<Guru> searchResult = FXCollections.observableArrayList(guruDao.searchGuru(keyword));
        teacherTable.setItems(searchResult);
    }

    @FXML
    private void handleDeleteSelectedTeacher(ActionEvent event) {
        Guru selectedGuru = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedGuru == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Guru", "Pilih guru yang ingin dihapus dari tabel.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus data guru ini? Ini juga akan menghapus user login guru.", ButtonType.YES, ButtonType.NO);
        confirmAlert.getDialogPane().setPrefWidth(500);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (guruDao.deleteGuru(selectedGuru.getNip())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data guru berhasil dihapus.");
                loadTeachers();
                loadCommonData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus data guru.");
            }
        }
    }

    private void setupTeacherTable() {
        colTeacherNip.setCellValueFactory(new PropertyValueFactory<>("nip"));
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colTeacherGender.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        colTeacherEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTeacherPhone.setCellValueFactory(new PropertyValueFactory<>("noHp"));
        colTeacherPassword.setCellValueFactory(cellData -> {
            Guru guru = cellData.getValue();
            return new SimpleStringProperty(guru.getPasswordUser() != null ? guru.getPasswordUser() : "N/A");
        });
    }

    private void loadTeachers() {
        ObservableList<Guru> teachers = FXCollections.observableArrayList(guruDao.getAllGuru());
        teacherTable.setItems(teachers);
    }

    // --- Methods for Manage Schedules Tab ---
    @FXML
    private void handleAddSchedule(ActionEvent event) {
        Kelas selectedClass = classChoiceBox.getSelectionModel().getSelectedItem();
        MataPelajaran selectedSubject = subjectChoiceBox.getSelectionModel().getSelectedItem();
        Guru selectedTeacher = teacherChoiceBox.getSelectionModel().getSelectedItem();
        // TahunAjaran selectedSchoolYear = schoolYearScheduleComboBox.getSelectionModel().getSelectedItem(); // Tidak digunakan di JadwalKelas (DDL)
        // String selectedSemester = semesterScheduleComboBox.getSelectionModel().getSelectedItem(); // Tidak digunakan di JadwalKelas (DDL)
        String day = dayChoiceBox.getSelectionModel().getSelectedItem();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();

        if (selectedClass == null || selectedSubject == null || selectedTeacher == null ||
                day == null || startTime.isEmpty() || endTime.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua kolom jadwal harus diisi.");
            return;
        }

        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);

            JadwalKelas newJadwal = new JadwalKelas(
                    0, selectedClass.getIdKelas(), selectedSubject.getIdMapel(), selectedTeacher.getNip(),
                    day, start, end // Menggunakan LocalTime
            );

            if (jadwalKelasDao.addJadwalKelas(newJadwal)) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Jadwal berhasil ditambahkan.");
                clearAddScheduleFields();
                // TODO: Refresh tabel jadwal jika ada
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menambahkan jadwal.");
            }
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Format Waktu Salah", "Format waktu harus HH:MM (misal: 08:00).");
        }
    }


    // --- Methods for Manage Classes Tab ---
    @FXML
    private void handleCreateClass(ActionEvent event) {
        String namaKelas = classNameInputField.getText();
        Guru selectedHomeroomTeacher = homeroomTeacherChoiceBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedTahunAjaran = schoolYearCreateClassComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = semesterCreateClassComboBox.getSelectionModel().getSelectedItem();

        if (namaKelas.isEmpty() || selectedHomeroomTeacher == null || selectedTahunAjaran == null || selectedSemester == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua kolom Kelas Baru harus diisi.");
            return;
        }

        String tingkat = "";
        if (namaKelas.matches("^\\d+[A-Za-z]?$")) {
            tingkat = namaKelas.replaceAll("[^\\d]", "");
            if (tingkat.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Format nama kelas tidak valid. Harap masukkan angka diikuti opsional huruf (misal: 2A, 5B).");
                return;
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Format nama kelas tidak valid. Harap masukkan angka diikuti opsional huruf (misal: 2A, 5B).");
            return;
        }

        Kelas newKelas = new Kelas(0, namaKelas, tingkat, selectedHomeroomTeacher.getNip(),
                selectedHomeroomTeacher.getNama(), selectedTahunAjaran.getIdTahunAjaran(),
                selectedTahunAjaran.getTahunLengkap(), selectedSemester);

        if (kelasDao.addKelas(newKelas)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Kelas baru berhasil dibuat.");
            clearCreateClassFields();
            loadCommonData();
            loadStudentsInSelectedClass();
            loadStudents();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal membuat kelas baru. Nama kelas mungkin sudah ada.");
        }
    }

    private void clearCreateClassFields() {
        if (classNameInputField != null) classNameInputField.clear();
        if (homeroomTeacherChoiceBox != null) homeroomTeacherChoiceBox.getSelectionModel().clearSelection();
        if (schoolYearCreateClassComboBox != null) schoolYearCreateClassComboBox.getSelectionModel().clearSelection();
        if (semesterCreateClassComboBox != null) semesterCreateClassComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleUpdateClass(ActionEvent event) {
        Kelas selectedKelas = classNameEditComboBox.getSelectionModel().getSelectedItem();
        if (selectedKelas == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Kelas", "Pilih kelas yang ingin diperbarui dari dropdown.");
            return;
        }

        String newNamaKelas = selectedKelas.getNamaKelas();
        Guru newHomeroomTeacher = homeroomTeacherEditComboBox.getSelectionModel().getSelectedItem();
        TahunAjaran newTahunAjaran = schoolYearEditClassComboBox.getSelectionModel().getSelectedItem();
        String newSemester = semesterEditClassComboBox.getSelectionModel().getSelectedItem();

        if (newHomeroomTeacher == null || newTahunAjaran == null || newSemester == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Wali kelas, tahun ajaran, dan semester harus diisi.");
            return;
        }

        String tingkat = "";
        if (newNamaKelas.matches("^\\d+[A-Za-z]?$")) {
            tingkat = newNamaKelas.replaceAll("[^\\d]", "");
            if (tingkat.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Format nama kelas tidak valid. Harap masukkan angka diikuti opsional huruf (misal: 2A, 5B).");
                return;
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Format nama kelas tidak valid. Harap masukkan angka diikuti opsional huruf (misal: 2A, 5B).");
            return;
        }

        selectedKelas.setNamaKelas(newNamaKelas);
        selectedKelas.setTingkat(tingkat);
        selectedKelas.setNipWaliKelas(newHomeroomTeacher.getNip());
        selectedKelas.setNamaWaliKelas(newHomeroomTeacher.getNama());
        selectedKelas.setIdTahunAjaran(newTahunAjaran.getIdTahunAjaran());
        selectedKelas.setTahunAjaranLengkap(newTahunAjaran.getTahunLengkap());
        selectedKelas.setSemester(newSemester);

        if (kelasDao.updateKelas(selectedKelas)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data kelas berhasil diperbarui.");
            clearEditClassFields();
            loadCommonData();
            loadStudentsInSelectedClass();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui data kelas.");
        }
    }

    @FXML
    private void handleDeleteClass(ActionEvent event) {
        Kelas selectedKelas = classNameEditComboBox.getSelectionModel().getSelectedItem();
        if (selectedKelas == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Kelas", "Pilih kelas yang ingin dihapus dari dropdown.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus kelas ini? Ini akan menghapus semua siswa dari kelas ini.", ButtonType.YES, ButtonType.NO);
        confirmAlert.getDialogPane().setPrefWidth(500);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (kelasDao.deleteKelas(selectedKelas.getIdKelas())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Kelas berhasil dihapus.");
                clearEditClassFields();
                loadCommonData();
                loadStudentsInSelectedClass();
                loadStudents();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus kelas.");
            }
        }
    }

    private void populateEditClassFields(Kelas kelas) {
        if (kelas != null) {
            homeroomTeacherEditComboBox.getSelectionModel().select(
                    homeroomTeacherEditComboBox.getItems().stream()
                            .filter(g -> g.getNip().equals(kelas.getNipWaliKelas()))
                            .findFirst()
                            .orElse(null)
            );

            schoolYearEditClassComboBox.getSelectionModel().select(
                    schoolYearEditClassComboBox.getItems().stream()
                            .filter(ta -> ta.getIdTahunAjaran() == kelas.getIdTahunAjaran())
                            .findFirst()
                            .orElse(null)
            );
            semesterEditClassComboBox.getSelectionModel().select(kelas.getSemester());
        } else {
            clearEditClassFields();
        }
    }


    private void clearEditClassFields() {
        if (classNameEditComboBox != null) classNameEditComboBox.getSelectionModel().clearSelection();
        if (homeroomTeacherEditComboBox != null) homeroomTeacherEditComboBox.getSelectionModel().clearSelection();
        if (schoolYearEditClassComboBox != null) schoolYearEditClassComboBox.getSelectionModel().clearSelection();
        if (semesterEditClassComboBox != null) semesterEditClassComboBox.getSelectionModel().clearSelection();
    }


    @FXML
    private void handleSearchStudentToAssign(ActionEvent event) {
        String keyword = searchStudentAssignClassField.getText();
        ObservableList<Siswa> searchResult = FXCollections.observableArrayList(siswaDao.searchSiswa(keyword));
        studentsToAssignTable.setItems(searchResult);
    }


    @FXML
    private void handleAssignStudent(ActionEvent event) {
        Siswa selectedStudent = studentsToAssignTable.getSelectionModel().getSelectedItem();
        Kelas selectedClass = classAssignComboBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedTahunAjaran = schoolYearAssignComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = semesterAssignComboBox.getSelectionModel().getSelectedItem();

        if (selectedStudent == null || selectedClass == null || selectedTahunAjaran == null || selectedSemester == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Pilih siswa, kelas, tahun ajaran, dan semester.");
            return;
        }

        // Cek apakah siswa sudah terdaftar di kelas yang sama untuk tahun ajaran dan semester yang sama
        // Ambil Siswa yang sudah update di database untuk cek validasi
        Siswa studentInDb = siswaDao.getSiswaByNis(selectedStudent.getNis());
        if (studentInDb != null && studentInDb.getIdKelas() != null &&
                studentInDb.getIdKelas().equals(selectedClass.getIdKelas()) &&
                studentInDb.getIdTahunAjaran() != null && studentInDb.getIdTahunAjaran().equals(selectedTahunAjaran.getIdTahunAjaran()) &&
                studentInDb.getSemester() != null && studentInDb.getSemester().equals(selectedSemester)) {
            showAlert(Alert.AlertType.WARNING, "Duplikat Data", "Siswa ini sudah terdaftar di kelas yang sama untuk tahun ajaran dan semester yang dipilih.");
            return;
        }


        if (siswaDao.assignStudentToClass(selectedStudent.getNis(), selectedClass.getIdKelas(), selectedTahunAjaran.getIdTahunAjaran(), selectedSemester)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil ditetapkan ke kelas.");
            loadStudentsInSelectedClass();
            loadStudentsIntoAssignClassCombos();
            loadStudents();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menetapkan siswa ke kelas.");
        }
    }

    @FXML
    private void handleEditSelectedStudentInClass(ActionEvent event) {
        Siswa selectedStudentInClass = studentsInSelectedClassTable.getSelectionModel().getSelectedItem();
        if (selectedStudentInClass == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Siswa", "Pilih siswa dari tabel yang ingin diedit.");
            return;
        }

        if (mainTabPane != null && entryStudentDataTab != null) {
            mainTabPane.getSelectionModel().select(entryStudentDataTab);

            loadComboBoxesForStudentEdit();

            Siswa siswaToSelect = null;
            for (Siswa s : selectNisEditComboBox.getItems()) {
                if (s.getNis().equals(selectedStudentInClass.getNis())) {
                    siswaToSelect = s;
                    break;
                }
            }
            if (siswaToSelect != null) {
                selectNisEditComboBox.getSelectionModel().select(siswaToSelect);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal menemukan siswa di daftar edit.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Tidak dapat berpindah ke tab 'Entry Student Data'.");
        }
    }


    @FXML
    private void handleRemoveStudentFromClass(ActionEvent event) {
        Siswa selectedStudentInClass = studentsInSelectedClassTable.getSelectionModel().getSelectedItem();
        if (selectedStudentInClass == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Siswa", "Pilih siswa dari tabel yang ingin dihapus dari kelas.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus siswa ini dari kelasnya? Ini tidak akan menghapus data siswa secara permanen, hanya melepaskan dari kelas.", ButtonType.YES, ButtonType.NO);
        confirmAlert.getDialogPane().setPrefWidth(500);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (siswaDao.removeClassInfoFromStudent(selectedStudentInClass.getNis())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil dihapus dari kelas.");
                loadStudentsInSelectedClass();
                loadStudentsIntoAssignClassCombos();
                loadStudents();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus siswa dari kelas.");
            }
        }
    }

    private void setupAssignStudentTables() {
        colAssignNis.setCellValueFactory(new PropertyValueFactory<>("nis"));
        colAssignName.setCellValueFactory(new PropertyValueFactory<>("nama"));
    }

    private void setupStudentsInSelectedClassTable() {
        colInClassNis.setCellValueFactory(new PropertyValueFactory<>("nis"));
        colInClassName.setCellValueFactory(new PropertyValueFactory<>("nama"));
    }

    private void loadStudentsIntoAssignClassCombos() {
        studentsToAssignTable.setItems(FXCollections.observableArrayList(siswaDao.getAllSiswa()));
        studentInputSiswaEkskulComboBox.setItems(FXCollections.observableArrayList(siswaDao.getAllSiswa()));
    }

    private void loadStudentsInSelectedClass() {
        Kelas selectedClass = classStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem();
        TahunAjaran selectedYear = schoolYearStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = semesterStudentsInSelectedClassComboBox.getSelectionModel().getSelectedItem();

        if (selectedClass != null && selectedYear != null && selectedSemester != null) {
            ObservableList<Siswa> studentsInClass = FXCollections.observableArrayList(
                    siswaDao.getStudentsInClass(selectedClass.getIdKelas(), selectedYear.getIdTahunAjaran(), selectedSemester)
            );
            studentsInSelectedClassTable.setItems(studentsInClass);
        } else {
            studentsInSelectedClassTable.setItems(FXCollections.emptyObservableList());
        }
    }

    // --- Methods for Manage School Agenda Tab ---
    @FXML
    private void handleAddSchoolAgenda(ActionEvent event) {
        String judul = agendaContentField.getText();
        LocalDate tanggalMulai = agendaStartDatePicker.getValue();
        LocalDate tanggalSelesai = agendaEndDatePicker.getValue();
        TahunAjaran selectedTahunAjaran = schoolYearAgendaAddComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = semesterAgendaAddComboBox.getSelectionModel().getSelectedItem();

        if (judul.isEmpty() || tanggalMulai == null || tanggalSelesai == null || selectedTahunAjaran == null || selectedSemester == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Semua kolom Agenda Sekolah harus diisi.");
            return;
        }

        AgendaSekolah newAgenda = new AgendaSekolah(
                0, judul, judul, tanggalMulai, tanggalSelesai, selectedTahunAjaran.getIdTahunAjaran(), selectedSemester
        );

        if (agendaSekolahDao.addAgendaSekolah(newAgenda)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Agenda sekolah berhasil ditambahkan.");
            clearAddSchoolAgendaFields();
            loadSchoolAgenda();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menambahkan agenda sekolah.");
        }
    }

    @FXML
    private void handleDeleteSchoolAgenda(ActionEvent event) {
        AgendaSekolah selectedAgenda = agendaTable.getSelectionModel().getSelectedItem();
        if (selectedAgenda == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Agenda", "Pilih agenda yang ingin dihapus dari tabel.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus agenda ini?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (agendaSekolahDao.deleteAgendaSekolah(selectedAgenda.getIdAgendaSekolah())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Agenda berhasil dihapus.");
                loadSchoolAgenda();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus agenda.");
            }
        }
    }

    // --- Methods for Extracurricular Tab ---
    @FXML
    private void handleAddMentor(ActionEvent event) {
        Ekstrakurikuler selectedEkskul = extracurricularInputMentorComboBox.getSelectionModel().getSelectedItem();
        Guru mentor1 = mentor1ComboBox.getSelectionModel().getSelectedItem();
        Guru mentor2 = mentor2ComboBox.getSelectionModel().getSelectedItem();
        Guru mentor3 = mentor3ComboBox.getSelectionModel().getSelectedItem();
        Guru mentor4 = mentor4ComboBox.getSelectionModel().getSelectedItem();

        if (selectedEkskul == null || mentor1 == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Ekstrakurikuler dan setidaknya Mentor 1 harus diisi.");
            return;
        }

        boolean success = true;
        if (!pembinaDao.addPembina(new Pembina(0, mentor1.getNip(), selectedEkskul.getIdEkstrakurikuler()))) {
            showAlert(Alert.AlertType.WARNING, "Sebagian Gagal", "Gagal menambahkan Mentor 1. Mungkin sudah ada atau duplikat.");
            success = false;
        }
        if (mentor2 != null && !mentor2.getNip().equals(mentor1.getNip())) {
            if (!pembinaDao.addPembina(new Pembina(0, mentor2.getNip(), selectedEkskul.getIdEkstrakurikuler()))) {
                showAlert(Alert.AlertType.WARNING, "Sebagian Gagal", "Gagal menambahkan Mentor 2. Mungkin sudah ada atau duplikat.");
                success = false;
            }
        }
        if (mentor3 != null && !mentor3.getNip().equals(mentor1.getNip()) && (mentor2 == null || !mentor3.getNip().equals(mentor2.getNip()))) {
            if (!pembinaDao.addPembina(new Pembina(0, mentor3.getNip(), selectedEkskul.getIdEkstrakurikuler()))) {
                showAlert(Alert.AlertType.WARNING, "Sebagian Gagal", "Gagal menambahkan Mentor 3. Mungkin sudah ada atau duplikat.");
                success = false;
            }
        }
        if (mentor4 != null && !mentor4.getNip().equals(mentor1.getNip()) && (mentor2 == null || !mentor4.getNip().equals(mentor2.getNip())) && (mentor3 == null || !mentor4.getNip().equals(mentor3.getNip()))) {
            if (!pembinaDao.addPembina(new Pembina(0, mentor4.getNip(), mentor4.getNama(), selectedEkskul.getIdEkstrakurikuler(), selectedEkskul.getNama()))) {
                showAlert(Alert.AlertType.WARNING, "Sebagian Gagal", "Gagal menambahkan Mentor 4. Mungkin sudah ada atau duplikat.");
                success = false;
            }
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Mentor berhasil ditambahkan ke ekstrakurikuler.");
        }
        clearAddMentorFields();
        loadExtracurriculars();
    }

    @FXML
    private void handleDeleteExtracurricular(ActionEvent event) {
        Ekstrakurikuler selectedEkskul = extracurricularTable.getSelectionModel().getSelectedItem();
        if (selectedEkskul == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Ekstrakurikuler", "Pilih ekstrakurikuler yang ingin dihapus dari tabel.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus ekstrakurikuler ini? Ini akan menghapus semua pembina dan peserta terkait.", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (ekstrakurikulerDao.deleteEkstrakurikuler(selectedEkskul.getIdEkstrakurikuler())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Ekstrakurikuler berhasil dihapus.");
                loadExtracurriculars();
                loadCommonData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus ekstrakurikuler.");
            }
        }
    }

    @FXML
    private void handleAddStudentToExtracurricular(ActionEvent event) {
        Ekstrakurikuler selectedEkskul = extracurricularInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();
        Siswa selectedStudent = studentInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();

        if (selectedEkskul == null || selectedStudent == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Pilih Ekstrakurikuler dan Siswa.");
            return;
        }

        PesertaEkskul newPeserta = new PesertaEkskul(0, selectedStudent.getNis(), selectedEkskul.getIdEkstrakurikuler());

        if (pesertaEkskulDao.addPesertaEkskul(newPeserta)) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil ditambahkan ke ekstrakurikuler.");
            clearAddStudentToExtracurricularFields();
            loadStudentsInExtracurricularTable();
        } else {
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menambahkan siswa ke ekstrakurikuler. Mungkin siswa sudah terdaftar di ekstrakurikuler ini.");
        }
    }

    @FXML
    private void handleRemoveStudentFromExtracurricular(ActionEvent event) {
        Siswa selectedStudent = studentsInExtracurricularTable.getSelectionModel().getSelectedItem();
        Ekstrakurikuler selectedEkskul = extracurricularInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();

        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Siswa", "Pilih siswa dari tabel yang ingin dihapus dari ekstrakurikuler.");
            return;
        }
        if (selectedEkskul == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Ekstrakurikuler", "Pilih ekstrakurikuler dari ComboBox 'INPUT SISWA to Extracurricular' terlebih dahulu.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus siswa '" + selectedStudent.getNama() + "' dari ekstrakurikuler '" + selectedEkskul.getNama() + "'?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (pesertaEkskulDao.deletePesertaEkskulByNisAndEkskulId(selectedStudent.getNis(), selectedEkskul.getIdEkstrakurikuler())) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Siswa berhasil dihapus dari ekstrakurikuler.");
                loadStudentsInExtracurricularTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus siswa dari ekstrakurikuler.");
            }
        }
    }

    private void loadStudentsInExtracurricularTable() {
        Ekstrakurikuler selectedEkskul = extracurricularInputSiswaEkskulComboBox.getSelectionModel().getSelectedItem();
        if (selectedEkskul != null) {
            ObservableList<Siswa> students = FXCollections.observableArrayList(pesertaEkskulDao.getStudentsByExtracurricular(selectedEkskul.getIdEkstrakurikuler()));
            studentsInExtracurricularTable.setItems(students);
        } else {
            studentsInExtracurricularTable.setItems(FXCollections.emptyObservableList());
        }
    }


    // --- General Methods ---
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadCommonData() {
        ObservableList<Guru> teachers = FXCollections.observableArrayList(guruDao.getAllGuru());
        homeroomTeacherChoiceBox.setItems(teachers);
        homeroomTeacherEditComboBox.setItems(teachers);
        teacherChoiceBox.setItems(teachers);
        mentor1ComboBox.setItems(teachers);
        mentor2ComboBox.setItems(teachers);
        mentor3ComboBox.setItems(teachers);
        mentor4ComboBox.setItems(teachers);
        System.out.println("Jumlah Guru dimuat: " + teachers.size());

        ObservableList<TahunAjaran> schoolYears = FXCollections.observableArrayList(tahunAjaranDao.getAllTahunAjaran());
        schoolYearCreateClassComboBox.setItems(schoolYears);
        schoolYearEditClassComboBox.setItems(schoolYears);
        schoolYearAssignComboBox.setItems(schoolYears);
        schoolYearStudentsInSelectedClassComboBox.setItems(schoolYears);
        schoolYearAgendaAddComboBox.setItems(schoolYears);
        schoolYearAgendaViewComboBox.setItems(schoolYears);
        schoolYearScheduleComboBox.setItems(schoolYears);
        schoolYearInputSiswaEkskulComboBox.setItems(schoolYears);
        System.out.println("Jumlah Tahun Ajaran dimuat: " + schoolYears.size());

        ObservableList<Kelas> classes = FXCollections.observableArrayList(kelasDao.getAllKelas());
        classNameEditComboBox.setItems(classes);
        classAssignComboBox.setItems(classes);
        classStudentsInSelectedClassComboBox.setItems(classes);
        classChoiceBox.setItems(classes);
        classInputSiswaEkskulComboBox.setItems(classes);
        System.out.println("Jumlah Kelas dimuat: " + classes.size());

        ObservableList<MataPelajaran> subjects = FXCollections.observableArrayList(mataPelajaranDao.getAllMataPelajaran());
        subjectChoiceBox.setItems(subjects);
        System.out.println("Jumlah Mata Pelajaran dimuat: " + subjects.size());

        ObservableList<Ekstrakurikuler> extracurriculars = FXCollections.observableArrayList(ekstrakurikulerDao.getAllEkstrakurikuler());
        extracurricularInputMentorComboBox.setItems(extracurriculars);
        extracurricularInputSiswaEkskulComboBox.setItems(extracurriculars);
        System.out.println("Jumlah Ekstrakurikuler dimuat: " + extracurriculars.size());
    }

    private void loadSchoolAgenda() {
        ObservableList<AgendaSekolah> agendaList;
        TahunAjaran selectedYear = schoolYearAgendaViewComboBox.getSelectionModel().getSelectedItem();
        String selectedSemester = semesterAgendaViewComboBox.getSelectionModel().getSelectedItem();

        if (selectedYear != null && selectedSemester != null) {
            agendaList = FXCollections.observableArrayList(
                    agendaSekolahDao.getAgendaByTahunAjaranAndSemester(selectedYear.getIdTahunAjaran(), selectedSemester)
            );
        } else {
            agendaList = FXCollections.observableArrayList(agendaSekolahDao.getAllAgendaSekolah());
        }
        agendaTable.setItems(agendaList);
        agendaTable.refresh();
    }

    private void loadExtracurriculars() {
        ObservableList<Ekstrakurikuler> extracurricularList = FXCollections.observableArrayList(ekstrakurikulerDao.getAllEkstrakurikulerWithMentors());
        extracurricularTable.setItems(extracurricularList);
        extracurricularTable.refresh();
    }

    private void setupAgendaTable() {
        colAgendaContent.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colAgendaStart.setCellValueFactory(new PropertyValueFactory<>("tanggalMulai"));
        colAgendaEnd.setCellValueFactory(new PropertyValueFactory<>("tanggalSelesai"));
    }

    private void setupExtracurricularTable() {
        colExtracurricularName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colExtracurricularLevel.setCellValueFactory(new PropertyValueFactory<>("tingkat"));
        colExtracurricularMentor.setCellValueFactory(cellData -> {
            Ekstrakurikuler ekskul = cellData.getValue();
            return new SimpleStringProperty(ekskul.getMentorNames() != null && !ekskul.getMentorNames().isEmpty() ? ekskul.getMentorNames() : "Belum Ada Pembina");
        });
        colExtracurricularStudentNis.setCellValueFactory(new PropertyValueFactory<>("nis"));
        colExtracurricularStudentName.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colExtracurricularStudentClass.setCellValueFactory(cellData -> new SimpleStringProperty(
                // Asumsi namaKelas sudah ada di objek Siswa dari join di PesertaEkskulDAO
                cellData.getValue().getNamaKelas() != null ? cellData.getValue().getNamaKelas() : "N/A"
        ));
    }


    private void clearAddScheduleFields() {
        if (dayChoiceBox != null) dayChoiceBox.getSelectionModel().clearSelection();
        if (startTimeField != null) startTimeField.clear();
        if (endTimeField != null) endTimeField.clear();
        if (subjectChoiceBox != null) subjectChoiceBox.getSelectionModel().clearSelection();
        if (classChoiceBox != null) classChoiceBox.getSelectionModel().clearSelection();
        if (teacherChoiceBox != null) teacherChoiceBox.getSelectionModel().clearSelection();
        if (schoolYearScheduleComboBox != null) schoolYearScheduleComboBox.getSelectionModel().clearSelection();
        if (semesterScheduleComboBox != null) semesterScheduleComboBox.getSelectionModel().clearSelection();
    }

    private void clearAddSchoolAgendaFields() {
        if (agendaContentField != null) agendaContentField.clear();
        if (agendaStartDatePicker != null) agendaStartDatePicker.setValue(null);
        if (agendaEndDatePicker != null) agendaEndDatePicker.setValue(null);
        if (schoolYearAgendaAddComboBox != null) schoolYearAgendaAddComboBox.getSelectionModel().clearSelection();
        if (semesterAgendaAddComboBox != null) semesterAgendaAddComboBox.getSelectionModel().clearSelection();
        if (agendaTable != null) agendaTable.getSelectionModel().clearSelection();
    }

    private void clearAddMentorFields() {
        if (extracurricularInputMentorComboBox != null) extracurricularInputMentorComboBox.getSelectionModel().clearSelection();
        if (mentor1ComboBox != null) mentor1ComboBox.getSelectionModel().clearSelection();
        if (mentor2ComboBox != null) mentor2ComboBox.getSelectionModel().clearSelection();
        if (mentor3ComboBox != null) mentor3ComboBox.getSelectionModel().clearSelection();
        if (mentor4ComboBox != null) mentor4ComboBox.getSelectionModel().clearSelection();
        if (levelInputMentorComboBox != null) levelInputMentorComboBox.getSelectionModel().clearSelection();
    }
    private void clearAddStudentToExtracurricularFields() {
        if (classInputSiswaEkskulComboBox != null) classInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        if (schoolYearInputSiswaEkskulComboBox != null) schoolYearInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        if (semesterInputSiswaEkskulComboBox != null) semesterInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        if (extracurricularInputSiswaEkskulComboBox != null) extracurricularInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        if (levelInputSiswaEkskulComboBox != null) levelInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
        if (studentInputSiswaEkskulComboBox != null) studentInputSiswaEkskulComboBox.getSelectionModel().clearSelection();
    }

     */
}
