package com.GustaveEiffelTutoring.gui;

import com.GustaveEiffelTutoring.common.IAppointmentList;
import com.GustaveEiffelTutoring.common.ITutor;
import com.GustaveEiffelTutoring.server.AppointmentList;
import com.GustaveEiffelTutoring.server.Tutor;
import com.GustaveEiffelTutoring.server.TutorsList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class LandingMentorController
{
    @FXML
    private Label earnings_label;
    @FXML
    private Label welcomeLabel;
    @FXML
    private TableColumn StudentColumn;
    @FXML
    private TableColumn DayColumn;
    @FXML
    private TableColumn TimeColumn;
    @FXML
    private TableColumn CostColumn;
    @FXML
    private TableColumn StatusColumn;
    @FXML
    private Button cancelButton;
    @FXML
    private Button ValidateButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;

    @FXML
    private TableView AppointmentTable;

    ITutor tutor;
    IAppointmentList appointments;

    @FXML
    public void initialize() throws RemoteException {
        tutor = new Tutor();
        appointments = new AppointmentList();
    }

    public void setTutor(ITutor tutor) throws RemoteException {
        this.tutor = tutor;
        if (tutor != null) {
            welcomeLabel.setText("Welcome " + tutor.getName().substring(0, 1).toUpperCase() + tutor.getName().substring(1));
            earnings_label.setText(String.valueOf(tutor.getTotalEarnings()) + "€");
        }
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        try {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/sign-in.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tutor Sign in");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void CancelAppointment(ActionEvent actionEvent) {
    }

    @FXML
    public void validateAppointment(ActionEvent actionEvent) {
    }

    @FXML
                public void redirect_profile(ActionEvent actionEvent) {
        try {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/profile-mentor.fxml"));
            Parent root = loader.load();
            ProfileMentorController controller = loader.getController();
            controller.setTutor(tutor);
            controller.setAppointments(appointments);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tutor Profile");
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