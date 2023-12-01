package client;

import common.IAppointment;

import common.IAppointmentList;
import common.IStudent;
import common.IStudentsList;
import common.ITutor;
import common.ITutorsList;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class StudentMainClient {
	public static void main(String[] args) {
		try {
			ITutorsList tutors = (ITutorsList) Naming.lookup("rmi://localhost:1090/tutors");
			IAppointmentList appointments = (IAppointmentList) Naming.lookup("rmi://localhost:1090/appointments");

//            // Student looking for a tutor by expertise
			Scanner scanner = new Scanner(System.in);

			System.out.println("Enter the expertise you are looking for (comma-separated):");
			String expertiseSearch = scanner.nextLine().trim();

			List<ITutor> matchingTutors = tutors.searchTutorsByExpertise(expertiseSearch);

			// Display matching tutors
			if (!matchingTutors.isEmpty()) {
				System.out.println("Matching Tutors:");
				for (ITutor t : matchingTutors) {
					System.out.println(t.printTutor());
				}
			} else {
				System.out.println("No matching tutors found.");
			}

//			scanner.close();

			// Student looking for a tutor by name
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Enter the name you are looking for:");
//            String nameSearch = scanner.next().trim();
//
//            List<ITutor> matchingTutors = tutors.searchTutorsByName(nameSearch);
//
//            // Display matching tutors
//            if (!matchingTutors.isEmpty()) {
//                System.out.println("Matching Tutors:");
//                for (ITutor t : matchingTutors) {
//                    System.out.println(t.getTutor());
//                }
//            } else {
//                System.out.println("No matching tutors found.");
//            }
//
//            scanner.close();

			// Student registration
			IStudentsList students = new StudentsList();

			// ==== Student static Registration

			IStudent intern1 = new Student("Heni Walha", "heni.walha@edu.univ-eiffel.fr", "azertyuiop");
			IStudent intern2 = new Student("Anis Bouhamed", "anis.bouhamed@edu.univ-eiffel.fr", "azertyuiop");

			IStudent extern1 = new Student("Khalil", "khalil@gmail.com", "azertyuiop");
			IStudent extern2 = new Student("Nour", "nour@gmail.com", "azertyuiop");
			
			extern1.setPreferredCurrency("GBP"); // Pound Sterling
			extern2.setPreferredCurrency("CHF"); // Swiss Franc

			students.staticStudentsRegistration(intern1);
			students.staticStudentsRegistration(intern2);
			students.staticStudentsRegistration(extern1);
			students.staticStudentsRegistration(extern2);

			IStudent testExistant = new Student("Heni", "heni.walha@edu.univ-eiffel.fr", "azertyuiop");
			students.staticStudentsRegistration(testExistant);

			// ==== Student Dynamic Registration

			System.out.println("Enter student name:");
			String studentName = scanner.nextLine();

			System.out.println("Enter student email:");
			String studentEmail = scanner.nextLine();
			
			System.out.println("Enter student password:");
			String password = scanner.nextLine();

			IStudent student = new Student(studentName, studentEmail, password);			
			scanner.close();
			
			students.dynamicStudentsRegistration(student);

			System.out.println("======== Students ======== ");

			// Displaying all students
			for (IStudent stu : students.getAllStudents().values()) {
				System.out.println(stu.printStudent());
			}

			System.out.println("======== Appointments ======== ");

			// Test appointment
			ITutor t = tutors.getAllTutors().values().iterator().next();
			Iterator<ITutor> iterator = tutors.getAllTutors().values().iterator();
			if (iterator.hasNext()) {
			    ITutor firstTutor = iterator.next();
			    if (iterator.hasNext()) {
			        t = iterator.next();
			    } else {
			        System.out.println("There is no second tutor.");
			    }
			} else {
			    System.out.println("There are no tutors.");
			}
			System.out.println(t.printTutor());
			System.out.println("======== Making an appointment with an intern ======== ");

			System.out.println("Total expenses of student = " + intern1.getTotalExpenses());
			System.out.println("Total earnings of tutor = " + t.getTotalEarnings());

			appointments.makeAppointment(t, intern1, LocalDate.parse("2023-11-24"), LocalTime.parse("09:10"),
					LocalTime.parse("10:30"));

			List<IAppointment> app = appointments.getAppointmentsStudent(intern1);
			IAppointment firstAppointment = null;
			for (IAppointment a : app) {
				firstAppointment = a;
				break;
			}
			System.out.println("Appointment : " + firstAppointment.getTutor().getName() + " with "
					+ firstAppointment.getStudent().getName() + " on " + firstAppointment.getDate() + " at "
					+ firstAppointment.getStartTime() + " , cost = " + firstAppointment.getCost() + " : "
					+ firstAppointment.getStatus());

			System.out.println("Total expenses of student = " + intern1.getTotalExpenses());
			System.out.println("Total earnings of tutor = " + t.getTotalEarnings());
			System.out.println(t.printTutor());

			// Test waiting list
			appointments.makeAppointment(t, intern2, LocalDate.parse("2023-11-24"), LocalTime.parse("09:10"),
					LocalTime.parse("10:30"));

			List<IAppointment> app2 = appointments.getAppointmentsStudent(intern2);
			IAppointment firstAppointment2 = null;
			for (IAppointment a : app2) {
				firstAppointment2 = a;
				break;
			}

			System.out.println("Appointment : " + firstAppointment2.getTutor().getName() + " with "
					+ firstAppointment2.getStudent().getName() + " on " + firstAppointment2.getDate() + " at "
					+ firstAppointment2.getStartTime() + " : " + firstAppointment2.getStatus());
			

			System.out.println("StudentMainClient running...");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
