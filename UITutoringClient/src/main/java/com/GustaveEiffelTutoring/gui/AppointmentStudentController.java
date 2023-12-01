package com.GustaveEiffelTutoring.gui;

import com.GustaveEiffelTutoring.client.Student;
import com.GustaveEiffelTutoring.common.IAppointment;
import com.GustaveEiffelTutoring.common.IAppointmentList;
import com.GustaveEiffelTutoring.common.IStudent;
import com.GustaveEiffelTutoring.common.ITutorsList;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentStudentController {

    @FXML
    private Label welcome;
    @FXML
    private Label expenses_label;
    @FXML
    private TextField search_name;
    @FXML
    private Button search_name_button;
    @FXML
    private TextField search_expertise;
    @FXML
    private Button search_expertise_button;
    @FXML
    private TableView<IAppointment> AppointmentsTable;
    @FXML
    private TableColumn<IAppointment, String> tutorName;
    @FXML
    private TableColumn<IAppointment, LocalDate> AppointmentDate;
    @FXML
    private TableColumn<IAppointment, LocalTime> AppointmentTime;
    @FXML
    private TableColumn<IAppointment, Double> AppointmentRate;
    @FXML
    private TableColumn<IAppointment, String> AppointmentStatus;
    @FXML
    private Button backButton;

    private IAppointmentList appointmentList;
    private List<IAppointment> studentAppointments;
    private IStudent student;
    private ITutorsList tutors;
    private ObservableList<IAppointment> listAppointments = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws RemoteException, MalformedURLException, NotBoundException {
        student = new Student();
        tutors = (ITutorsList) Naming.lookup("rmi://localhost:1095/tutors");
        if (appointmentList == null) {
            appointmentList = (IAppointmentList) Naming.lookup("rmi://localhost:1095/appointments");
        }
        studentAppointments = appointmentList.getAppointmentsStudent(student);
        listAppointments.clear();
        listAppointments.addAll(studentAppointments);

        tutorName.setCellValueFactory(cellData ->
                {
                    try {
                        return new SimpleObjectProperty<>(getCellDataValue(cellData.getValue().getTutor().getName()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        AppointmentDate.setCellValueFactory(cellData ->
                {
                    try {
                        return new SimpleObjectProperty<>(getCellDataValue(cellData.getValue().getDate()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        AppointmentTime.setCellValueFactory(cellData ->
                {
                    try {
                        return new SimpleObjectProperty<>(getCellDataValue(cellData.getValue().getStartTime()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        AppointmentRate.setCellValueFactory(cellData ->
                {
                    try {
                        return new SimpleObjectProperty<>(getCellDataValue(cellData.getValue().getCost()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        AppointmentStatus.setCellValueFactory(cellData ->
                {
                    try {
                        return new SimpleObjectProperty<>(getCellDataValue(cellData.getValue().getStatus()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        AppointmentsTable.setItems(listAppointments);
    }

    private <T> T getCellDataValue(T value) {
        return value != null ? value : (T) "";
    }

    @FXML
    public void searchByName(ActionEvent actionEvent) throws RemoteException {
        String searchName = search_name.getText().trim();
        filterAppointmentsByName(searchName);
    }

    @FXML
    public void searchByExpertise(ActionEvent actionEvent) throws RemoteException {
        String searchExpertise = search_expertise.getText().trim();
        filterAppointmentsByExpertise(searchExpertise);
    }

    private void filterAppointmentsByName(String searchName) throws RemoteException {
        if (!searchName.isEmpty()) {
            List<IAppointment> filteredAppointments = new ArrayList<>();
            for (IAppointment app : studentAppointments) {
                if (app.getTutor().getName().equalsIgnoreCase(searchName)) {
                    filteredAppointments.add(app);
                }
            }
            AppointmentsTable.setItems(FXCollections.observableArrayList(filteredAppointments));
        } else {
            AppointmentsTable.setItems(listAppointments);
        }
    }

    private void filterAppointmentsByExpertise(String searchExpertise) throws RemoteException {
        if (!searchExpertise.isEmpty()) {
            List<IAppointment> filteredAppointments = new ArrayList<>();
            for (IAppointment app : studentAppointments) {
                if (app.getTutor().getExpertise().contains(searchExpertise)) {
                    filteredAppointments.add(app);
                }
            }
            AppointmentsTable.setItems(FXCollections.observableArrayList(filteredAppointments));
        } else {
            AppointmentsTable.setItems(listAppointments);
        }
    }

    @Deprecated
    public void redirectBack(ActionEvent actionEvent) {
        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/landing-student.fxml"));
            Parent root = loader.load();
            LandingStudentController controller = loader.getController();
            controller.setStudent(student);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setStudent(IStudent student) throws RemoteException {
        this.student = student;
        welcome.setText("Welcome " + student.getName());
        expenses_label.setText(student.getTotalExpenses() + "€");
    }

    public void setAppointments(IAppointmentList appointments) throws RemoteException {
        this.appointmentList = appointments;
    }

    @FXML
    public void redirect_back(ActionEvent actionEvent) throws IOException {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/landing-student.fxml"));
        Parent root = loader.load();
        LandingStudentController controller = loader.getController();
        controller.setStudent(student);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }
}
