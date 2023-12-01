package com.GustaveEiffelTutoring.gui;

import com.GustaveEiffelTutoring.common.IAppointmentList;
import com.GustaveEiffelTutoring.common.ITutor;
import com.GustaveEiffelTutoring.server.AppointmentList;
import com.GustaveEiffelTutoring.server.Tutor;
import com.GustaveEiffelTutoring.server.TutorsList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileMentorController
{
    @javafx.fxml.FXML
    private TextArea tf_expertise;
    @javafx.fxml.FXML
    private TextField ts_dateslot1;
    @javafx.fxml.FXML
    private TextField tf_dateslot2;
    @javafx.fxml.FXML
    private TextField tf_dateslot3;
    @javafx.fxml.FXML
    private TextField tf_timeslot1;
    @javafx.fxml.FXML
    private TextField tf_timeslot2;
    @javafx.fxml.FXML
    private TextField tf_timeslot3;
    @javafx.fxml.FXML
    private Button save_profile_button;
    @javafx.fxml.FXML
    private TextField tf_rate;

    ITutor tutor;
    IAppointmentList appointments;

    @javafx.fxml.FXML
    public void initialize() throws RemoteException {
        tutor = new Tutor();
        appointments = new AppointmentList();
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(dateTimeFormatter);
    }

    private String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(timeFormatter);
    }

    public void setTutor(ITutor tutor) throws RemoteException {
        this.tutor = tutor;
        if (tutor != null) {
            if (!tutor.getExpertise().isEmpty()) {
                tf_expertise.setText(String.join(", ", tutor.getExpertise()));
            }

            if (!tutor.getAvailableSlots().isEmpty()) {
                if (tutor.getAvailableSlots().size() > 0) {
                    ts_dateslot1.setText(formatDateTime(tutor.getAvailableSlots().get(0)));
                    tf_timeslot1.setText(formatTime(tutor.getAvailableSlots().get(0)));
                }
                if (tutor.getAvailableSlots().size() > 1) {
                    tf_dateslot2.setText(formatDateTime(tutor.getAvailableSlots().get(1)));
                    tf_timeslot2.setText(formatTime(tutor.getAvailableSlots().get(1)));
                }
                if (tutor.getAvailableSlots().size() > 2) {
                    tf_dateslot3.setText(formatDateTime(tutor.getAvailableSlots().get(2)));
                    tf_timeslot3.setText(formatTime(tutor.getAvailableSlots().get(2)));
                }
            }

            tf_rate.setText(String.valueOf(tutor.getHourlyRate()));
        }
    }

    @javafx.fxml.FXML
    public void update_profile(ActionEvent actionEvent) throws RemoteException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<LocalDateTime> availableSlots = new ArrayList<>();

        LocalDate dateSlot1 = LocalDate.parse(ts_dateslot1.getText(), dateFormatter);
        LocalTime timeSlot1 = LocalTime.parse(tf_timeslot1.getText(), timeFormatter);
        LocalDateTime slot1 = LocalDateTime.of(dateSlot1, timeSlot1);
        availableSlots.add(slot1);

        if (!tf_dateslot2.getText().isEmpty()){
            LocalDate dateSlot2 = LocalDate.parse(tf_dateslot2.getText(), dateFormatter);
            LocalTime timeSlot2 = LocalTime.parse(tf_timeslot2.getText(), timeFormatter);
            LocalDateTime slot2 = LocalDateTime.of(dateSlot2, timeSlot2);
            availableSlots.add(slot2);
        }

        if (!tf_dateslot3.getText().isEmpty()){
            LocalDate dateSlot3 = LocalDate.parse(tf_dateslot3.getText(), dateFormatter);
            LocalTime timeSlot3 = LocalTime.parse(tf_timeslot3.getText(), timeFormatter);
            LocalDateTime slot3 = LocalDateTime.of(dateSlot3, timeSlot3);
            availableSlots.add(slot3);
        }
        tutor.setAvailableSlots(availableSlots);
        tutor.setExpertise(Arrays.asList(tf_expertise.getText().replace(" ", "").split(",")));
        tutor.setHourlyRate(Double.parseDouble(tf_rate.getText()));
        System.out.println(tutor.getTutor());

        try {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/landing-mentor.fxml"));
            Parent root = loader.load();
            LandingMentorController controller = loader.getController();
            controller.setTutor(tutor);
            controller.setAppointments(appointments);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tutor Home");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setAppointments (IAppointmentList appointments) {
        this.appointments = appointments;
    }

}