package com.GustaveEiffelTutoring.gui;

import com.GustaveEiffelTutoring.common.*;
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
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class LandingStudentController {
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
    private Button logout;
    @FXML
    private Label welcome;
    @FXML
    private TableView<ITutor> tutorsTable;
    @FXML
    private TableColumn<ITutor, String> tutorName;
    @FXML
    private TableColumn<ITutor, String> tutorEmail;
    @FXML
    private TableColumn<ITutor, List<String>> tutorExpertises;
    @FXML
    private TableColumn<ITutor, String> tutorSlots;
    @FXML
    private TableColumn<ITutor, Double> tutorRate;
    @FXML
    private Button appointments;
    @FXML
    private Button makeApp_btn;

    IStudent student;
    ITutorsList tutors;
    IAppointmentList appointmentsList;

    public ObservableList<ITutor> listTutors = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws MalformedURLException, NotBoundException, RemoteException {
        this.tutors = (ITutorsList) Naming.lookup("rmi://localhost:1095/tutors");
        this.appointmentsList = (IAppointmentList) Naming.lookup("rmi://localhost:1095/appointments");

        try {
            Map<Integer, ITutor> tutorsMap = tutors.getAllTutors();
            listTutors.clear();
            listTutors.addAll(tutorsMap.values());

            tutorName.setCellValueFactory(cellData -> {
                try {
                    return new SimpleObjectProperty<String>(cellData.getValue().getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return null;
            });
            tutorEmail.setCellValueFactory(cellData -> {
                try {
                    return new SimpleObjectProperty<String>(cellData.getValue().getEmail());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return null;
            });
            tutorExpertises.setCellValueFactory(cellData -> {
                try {
                    return new SimpleObjectProperty<>(cellData.getValue().getExpertise());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return null;
            });
            tutorSlots.setCellValueFactory(cellData -> {
                try {
                    return new SimpleObjectProperty<String>(cellData.getValue().getAvailableSlots().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return null;
            });
            tutorRate.setCellValueFactory(cellData -> {
                try {
                    return new SimpleObjectProperty<Double>(cellData.getValue().getHourlyRate());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return null;
            });
            tutorsTable.setItems(listTutors);

        } catch (Exception e) {
            System.out.println("Error getting tutors: " + e.getMessage());
        }
    }

    public void setStudent(IStudent student) throws RemoteException {
        this.student = student;
        welcome.setText("Welcome " + student.getName());
        expenses_label.setText(student.getTotalExpenses() + "€");
    }

    @FXML
    public void searchByName(ActionEvent actionEvent) throws RemoteException {
        String searchName = search_name.getText();
        Map<Integer, ITutor> tutorsMap = tutors.getAllTutors();
        if ( !searchName.trim().equals("")) {
            try {
                List<ITutor> filteredTutors = new ArrayList<>();
                for (ITutor tutor : tutorsMap.values()) {
                    if (tutor.getName().equalsIgnoreCase(searchName)) {
                        filteredTutors.add(tutor);
                    }
                }
                tutorsTable.setItems(FXCollections.observableArrayList(filteredTutors));

            } catch (Exception e) {
                System.out.println("Error searching tutors by name: " + e.getMessage());
            }
        }else{
            tutorsTable.setItems(FXCollections.observableArrayList(tutorsMap.values()));
        }
    }

    @FXML
    public void searchByExpertise(ActionEvent actionEvent) throws RemoteException {
        String searchExpertise = search_expertise.getText();
        Map<Integer, ITutor> tutorsMap = tutors.getAllTutors();
        if ( !searchExpertise.trim().equals("")) {
            try {
                List<ITutor> filteredTutors = new ArrayList<>();
                for (ITutor tutor : tutorsMap.values()) {
                    if (tutor.getExpertise().contains(searchExpertise)) {
                        filteredTutors.add(tutor);
                    }
                }
            tutorsTable.setItems(FXCollections.observableArrayList(filteredTutors));

            } catch (Exception e) {
                System.out.println("Error searching tutors by expertise: " + e.getMessage());
            }
        }else{
            tutorsTable.setItems(FXCollections.observableArrayList(tutorsMap.values()));
        }

    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        try {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/sign-in.fxml"));
            SigninController controller = loader.getController();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Student Sign in");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void makeAppointment(ActionEvent actionEvent) throws RemoteException {
        ITutor selectedTutor = tutorsTable.getSelectionModel().getSelectedItem();
        if (selectedTutor != null) {
            List<LocalDateTime> availableSlots = selectedTutor.getAvailableSlots();

            if (!availableSlots.isEmpty()) {
                LocalDateTime firstAvailableSlot = availableSlots.get(0);

                if (firstAvailableSlot != null) {
                    // Separate LocalDateTime into date and time
                    LocalDate selectedDate = firstAvailableSlot.toLocalDate();
                    LocalTime selectedTime = firstAvailableSlot.toLocalTime();

                    System.out.println("Total expenses of student = " + student.getTotalExpenses());
                    System.out.println("Total earnings of tutor = " + selectedTutor.getTotalEarnings());

                    appointmentsList.makeAppointment(selectedTutor, student, selectedDate, selectedTime,
                            selectedTime.plusHours(1));

                    List<IAppointment> appo = appointmentsList.getAppointmentsStudent(student);
                    IAppointment firstAppointment = null;
                    for (IAppointment a : appo) {
                        firstAppointment = a;
                        break;
                    }
                    System.out.println("Appointment : " + firstAppointment.getTutor().getName() + " with "
                            + firstAppointment.getStudent().getName() + " on " + firstAppointment.getDate() + " at "
                            + firstAppointment.getStartTime() + " , cost = " + firstAppointment.getCost()+ " : " + firstAppointment.getStatus());

                    expenses_label.setText(student.getTotalExpenses() + "€");
                    tutorsTable.refresh();
                    System.out.println("Total expenses of student = " + student.getTotalExpenses());
                    System.out.println("Total earnings of tutor = " + selectedTutor.getTotalEarnings());

                } else {
                    System.out.println("First available slot is null.");
                }
            } else {
                appointmentsList.makeAppointment(selectedTutor, student, null, null,null);
            }
        }
    }

    @FXML
    public void displayAppointments(ActionEvent actionEvent) throws IOException {
        /*((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/appointments-student.fxml"));
        Parent root = loader.load();
        AppointmentStudentController controller = loader.getController();
        controller.setStudent(student);
        controller.setAppointments(appointmentsList);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();*/
    }
}
