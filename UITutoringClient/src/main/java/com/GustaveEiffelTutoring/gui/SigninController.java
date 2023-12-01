package com.GustaveEiffelTutoring.gui;

import com.GustaveEiffelTutoring.client.StudentsList;
import com.GustaveEiffelTutoring.common.IStudent;
import com.GustaveEiffelTutoring.common.IStudentsList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SigninController
{
    @FXML
    private PasswordField tf_password;
    @FXML
    private Button signin_button;
    @FXML
    private Button signup_link_btn;
    @FXML
    private TextField tf_email;

    private IStudentsList students;

    @FXML
    public void initialize() throws RemoteException {
         students = new StudentsList();
    }

    @FXML
    public void sign_in(ActionEvent actionEvent) throws RemoteException {
        IStudent s = students.searchStudentByEmail(tf_email.getText());
        if (s != null){
            if (s.getPassword().equals(tf_password.getText())) {
                try {
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/landing-student.fxml"));
                    Parent root = loader.load();
                    LandingStudentController controller = loader.getController();
                    controller.setStudent(s);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Home");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }else{
                System.out.println("Wrong password");
            }
        }else{
            System.out.println("This email doesn't exist");
        }
    }

    @FXML
    public void redirect_signup(ActionEvent actionEvent) {
        try {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/GustaveEiffelTutoring/gui/sign-up.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setFill(Color.TRANSPARENT);
            stage.setTitle("Sign Up");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setStudentsList (IStudentsList students)  throws RemoteException{
        this.students = students;
    }
}