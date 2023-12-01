package com.GustaveEiffelTutoring.gui;

import com.GustaveEiffelTutoring.common.*;
import com.GustaveEiffelTutoring.server.AppointmentList;
import com.GustaveEiffelTutoring.server.TutorsList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SigninController
{
    @javafx.fxml.FXML
    private PasswordField tf_password;
    @javafx.fxml.FXML
    private Button signin_button;
    @javafx.fxml.FXML
    private Button signup_link_btn;
    @FXML
    private TextField tf_email;

    private ITutorsList tutors;
    private IAppointmentList appointments;

    @javafx.fxml.FXML
    public void initialize() throws RemoteException {
        tutors = new TutorsList();
        appointments = new AppointmentList();
    }

    @javafx.fxml.FXML
    public void sign_in(ActionEvent actionEvent) throws RemoteException {
        ITutor t = tutors.searchTutorByEmail(tf_email.getText());
        if (t != null){
            if (t.getPassword().equals(tf_password.getText())) {
                if (t.getExpertise().isEmpty()){
                    try {
                        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/profile-mentor.fxml"));
                        Parent root = loader.load();
                        ProfileMentorController controller = loader.getController();
                        controller.setTutor(t);
                        controller.setTutor(t);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Tutor Profile");
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }else {
                    try {
                        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/landing-mentor.fxml"));
                        Parent root = loader.load();
                        LandingMentorController controller = loader.getController();
                        controller.setTutor(t);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Home");
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }else{
                System.out.println("Wrong password");
            }
        }else{
            System.out.println("This email doesn't exist");
        }
    }

    @javafx.fxml.FXML
    public void redirect_signup(ActionEvent actionEvent) {
        try {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/com/GustaveEiffelTutoring/gui/sign-up.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setTutors (ITutorsList tutors) {
        this.tutors = tutors;
    }

    public void setAppointments (IAppointmentList appointments) {
        this.appointments = appointments;
    }
}