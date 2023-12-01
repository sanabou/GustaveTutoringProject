package com.GustaveEiffelTutoring;

import com.GustaveEiffelTutoring.client.Student;
import com.GustaveEiffelTutoring.client.StudentsList;
import com.GustaveEiffelTutoring.common.*;
import com.GustaveEiffelTutoring.gui.SignupController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class MainApp extends Application {

    private static ITutorsList tutors;
    private static IAppointmentList appointment;
    private static IStudentsList studentsList;

    static {
        try {
            studentsList = new StudentsList();
            initializeStaticStudents();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private static void initializeStaticStudents() {
        // Static Student Registration
        try {
            IStudent intern1 = new Student("Heni Walha", "heni.walha@edu.univ-eiffel.fr", "azertyuiop");
            IStudent intern2 = new Student("Anis Bouhamed", "anis.bouhamed@edu.univ-eiffel.fr", "azertyuiop");
            studentsList.staticStudentsRegistration(intern1);
            studentsList.staticStudentsRegistration(intern2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tutor lookup
        /*try {
            tutors = (ITutorsList) Naming.lookup("rmi://localhost:1095/tutors");
            for (ITutor t : tutors.getAllTutors().values()) {
                System.out.println(t.getTutor());
            }
            appointment = (IAppointmentList) Naming.lookup("rmi://localhost:1095/appointments");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Redirecting to signup page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/sign-up.fxml"));
        Parent root = loader.load();
        SignupController controller = loader.getController();
        controller.setStudentsList(studentsList);
        Scene scene = new Scene(root, 620, 400);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Gustave Tutoring");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
