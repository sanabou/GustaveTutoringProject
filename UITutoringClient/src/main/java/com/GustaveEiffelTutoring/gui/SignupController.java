package com.GustaveEiffelTutoring.gui;

import com.GustaveEiffelTutoring.client.Student;
import com.GustaveEiffelTutoring.client.StudentsList;
import com.GustaveEiffelTutoring.common.*;
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

    IStudentsList studentsList;

    @FXML
    public void initialize() throws RemoteException {
        studentsList = new StudentsList();
    }

    @FXML
    public void registration(ActionEvent actionEvent) throws RemoteException {
        String fullname = String.valueOf(tf_fullname.getText());
        String email = String.valueOf(tf_email.getText());
        String password = String.valueOf(tf_password.getText());
        IStudent student = new Student(fullname, email, password);
        studentsList.dynamicStudentsRegistration(student);
        for (IStudent stud : studentsList.getAllStudents().values())
        {
            System.out.println(stud.studentPrint());
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
            controller.setStudentsList(studentsList);
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

    public void setStudentsList (IStudentsList students)  throws RemoteException{
        this.studentsList = students;
    }

}