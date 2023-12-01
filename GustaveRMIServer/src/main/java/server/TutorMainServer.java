package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import common.IAppointmentList;
import common.ITutor;
import common.ITutorsList;

public class TutorMainServer {
	public static void main(String[] args) {
		try {

			// Tutor registration
			ITutorsList tutors = new TutorsList();
			// Static Tutor Registration

			ITutor tutor1 = new Tutor("Sana", "sana.bouhaouala@edu.univ-eiffel.fr", "azertyuiop");
			ITutor tutor2 = new Tutor("Ghailene", "ghailene.boughzala@edu.univ-eiffel.fr", "azertyuiop");
			ITutor tutor3 = new Tutor("Melek", "melek.boussif@edu.univ-eiffel.fr", "azertyuiop");

			List<String> expertiseList1 = Arrays.asList("français", "anglais");
			List<String> expertiseList2 = Arrays.asList("français", "maths");
			List<String> expertiseList3 = Arrays.asList("physique", "chimie");
			List<LocalDateTime> availableSlotsStatic = parseAvailableSlots("2023-11-24T09:10, 2023-11-29T10:10");

			tutor1.setExpertise(expertiseList1);
			tutor2.setExpertise(expertiseList2);
			tutor3.setExpertise(expertiseList3);

			tutor1.setAvailableSlots(availableSlotsStatic);
			tutor2.setAvailableSlots(availableSlotsStatic);
			tutor3.setAvailableSlots(availableSlotsStatic);

			tutor1.setHourlyRate(22.0);
			tutor2.setHourlyRate(44);
			tutor3.setHourlyRate(35);

			tutor2.setPreferredCurrency("USD"); // American Dollar
			tutor3.setPreferredCurrency("CAD"); // Canadian Dollar

			tutors.staticTutorRegistration(tutor1);
			tutors.staticTutorRegistration(tutor2);
			tutors.staticTutorRegistration(tutor3);

			// Tutor Registration
			Scanner scanner = new Scanner(System.in);

			System.out.println("Enter tutor name:");
			String tutorName = scanner.nextLine();

			System.out.println("Enter tutor email:");
			String tutorEmail = scanner.nextLine();

			System.out.println("Enter tutor password:");
			String password = scanner.nextLine();

			System.out.println("Enter hourly rate:");
			double hourlyRate = scanner.nextDouble();

			String expertiseInput;
			List<String> expertiseList;
			while (true) {
				scanner.nextLine();
				System.out.println("Enter expertise (comma-separated):");
				expertiseInput = scanner.nextLine().trim();
				if (!expertiseInput.isEmpty()) {
					expertiseList = Arrays.asList(expertiseInput.split(","));
					break;
				}
				System.out.println("Expertise cannot be empty. Please enter at least one expertise.");
			}

			System.out.println(
					"Enter available slots (comma-separated and date-time, e.g., 2023-11-24T09:10, 2023-11-29T10:10):");
			String slotsInput = scanner.nextLine();
			List<LocalDateTime> availableSlots = parseAvailableSlots(slotsInput);

			ITutor tutor = new Tutor(tutorName, tutorEmail, password);
			tutor.setExpertise(expertiseList);
			tutor.setAvailableSlots(availableSlots);
			tutor.setHourlyRate(hourlyRate);

			tutors.dynamicTutorRegistration(tutor);

			System.out.println("Registered Tutor: " + tutor.printTutor());

			scanner.close();

			tutors.printTutors();

			// Rebinding
			LocateRegistry.createRegistry(1090);
			Naming.rebind("rmi://localhost:1090/tutors", tutors);

			System.out.println("TutorMainServer is running...");

			IAppointmentList appointments = new AppointmentList();
			Naming.rebind("rmi://localhost:1090/appointments", appointments);

		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
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
