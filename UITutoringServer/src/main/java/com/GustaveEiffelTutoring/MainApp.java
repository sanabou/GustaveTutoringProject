package com.GustaveEiffelTutoring;

import com.GustaveEiffelTutoring.common.IAppointmentList;
import com.GustaveEiffelTutoring.common.IStudentsList;
import com.GustaveEiffelTutoring.common.ITutor;
import com.GustaveEiffelTutoring.common.ITutorsList;
import com.GustaveEiffelTutoring.gui.SignupController;
import com.GustaveEiffelTutoring.server.AppointmentList;
import com.GustaveEiffelTutoring.server.RebindService;
import com.GustaveEiffelTutoring.server.Tutor;
import com.GustaveEiffelTutoring.server.TutorsList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApp extends Application {
    private static ITutorsList tutors;

    static {
        try {
            tutors = new TutorsList();
            initializeStaticTutors();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void initializeStaticTutors() {
        try {
            ITutor tutor1 = new Tutor("François Come", "françois.come@univ-eiffel.fr", "azertyuiop");
            ITutor tutor2 = new Tutor("Neila Bhouri", "neila.bhouri@univ-eiffel.fr", "azertyuiop");
            ITutor tutor3 = new Tutor("Amelie Cerisier", "amelie.cerisier@univ-eiffel.fr", "azertyuiop");

            List<String> expertiseList1 = Arrays.asList("français", "anglais", "visualisation");
            List<String> expertiseList2 = Arrays.asList("français", "maths", "graphe");
            List<String> expertiseList3 = Arrays.asList("physique", "chimie", "apprentissage");
            List<LocalDateTime> availableSlotsStatic = parseAvailableSlots("2023-11-24T09:10, 2023-11-29T10:10");

            tutor1.setExpertise(expertiseList1);
            tutor2.setExpertise(expertiseList2);
            tutor3.setExpertise(expertiseList3);

            tutor1.setHourlyRate(44.0);
            tutor2.setHourlyRate(27.0);
            tutor3.setHourlyRate(32.0);

            tutor1.setAvailableSlots(availableSlotsStatic);
            tutor2.setAvailableSlots(availableSlotsStatic);
            tutor3.setAvailableSlots(availableSlotsStatic);

            tutors.staticTutorRegistration(tutor1);
            tutors.staticTutorRegistration(tutor2);
            tutors.staticTutorRegistration(tutor3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/sign-up.fxml"));
        Parent root = loader.load();
        SignupController controller = loader.getController();
        controller.setTutors(tutors);
        Scene scene = new Scene(root, 620, 400);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Gustave Tutoring");
        stage.setScene(scene);
        stage.show();

        // Start tutor rebinding service
        RebindService tutorRebindService = new RebindService(tutors);
        tutorRebindService.startRebinding();
        IAppointmentList appointments = new AppointmentList();
        Naming.rebind("rmi://localhost:1095/appointments", appointments);
    }

    public static void main(String[] args) {
        launch();
    }

    private static List<LocalDateTime> parseAvailableSlots(String slotsInput) {
        List<LocalDateTime> slotsList = new ArrayList<>();
        Arrays.stream(slotsInput.split(",")).map(String::trim).forEach(slot -> {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(slot, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                slotsList.add(dateTime);
            } catch (Exception e) {
                System.out.println(
                        "Invalid slot format: " + slot + ". Please use the YYYY-MM-DDThh:mm date-time format.");
            }
        });
        return slotsList;
    }
}
