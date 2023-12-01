package com.GustaveEiffelTutoring.gui;

import com.GustaveEiffelTutoring.common.IAppointmentList;
import com.GustaveEiffelTutoring.common.ITutor;
import com.GustaveEiffelTutoring.common.ITutorsList;
import com.GustaveEiffelTutoring.server.AppointmentList;
import com.GustaveEiffelTutoring.server.Tutor;
import com.GustaveEiffelTutoring.server.TutorsList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SignupController
{
    @FXML
    private TextField tf_fullname;
    @FXML
    private Button signup_button;
    @FXML
    private Button signin_link_btn;
    @FXML
    private PasswordField tf_password;
    @FXML
    private TextField tf_email;

    ITutorsList tutors ;
    IAppointmentList appointments ;
    @FXML
    public void initialize() throws RemoteException {
        tutors = new TutorsList();
        appointments = new AppointmentList();
    }

    @FXML
    public void registration(ActionEvent actionEvent) throws RemoteException {
        String fullname = String.valueOf(tf_fullname.getText());
        String email = String.valueOf(tf_email.getText());
        String password = String.valueOf(tf_password.getText());
        ITutor tutor = new Tutor(fullname, email, password);
        tutors.dynamicTutorRegistration(tutor);
        for (ITutor tut : tutors.getAllTutors().values())
        {
            System.out.println(tut.getTutor());
        }
        redirect_signin(actionEvent);
    }

    @FXML
    public void redirect_signin(ActionEvent actionEvent) {
        try {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/sign-in.fxml"));
            Parent root = loader.load();
            SigninController controller = loader.getController();
            controller.setAppointments(appointments);
            controller.setTutors(tutors);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setFill(Color.TRANSPARENT);
            stage.setTitle("Sign In");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setTutors (ITutorsList tutors){
        this.tutors = tutors;
    }

    public void setAppointments (IAppointmentList appointments) {
        this.appointments = appointments;
    }

}