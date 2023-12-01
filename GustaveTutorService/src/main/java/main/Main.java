package main;

import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.xml.rpc.ServiceException;

import bank.*;
import common.*;
import externStudent.*;

public class Main {

	public static void main(String[] args) {
		try {
			StudentsList externStudents = new StudentsListServiceLocator().getStudentsList();
			((StudentsListSoapBindingStub) externStudents).setMaintainSession(true);

			Student extern1 = new Student("khalil@esprit.tn", 1, "Khalil", "EXTERN", "azertyuiop", "GBP", 0);
			Student extern2 = new Student("nour@esprit.tn", 2, "Nour", "EXTERN", "azertyuiop", "USD", 0);

			externStudents.staticStudentsRegistration(extern1);
			externStudents.staticStudentsRegistration(extern2);

			System.out.println(externStudents.getAllStudents());

			ITutorsList tutors = (ITutorsList) Naming.lookup("rmi://localhost:1090/tutors");
			IAppointmentList appointments = (IAppointmentList) Naming.lookup("rmi://localhost:1090/appointments");

			for (Student stu : externStudents.getAllStudents()) {
				System.out.println(stu.toString());
			}
			// Book appointment and test banking
			ITutor t = tutors.getAllTutors().values().iterator().next();
			System.out.println(t.printTutor());

			System.out.println("======== Making an appointment with an extern ======== ");

			System.out.println("Total expenses of student = " + extern1.getTotalExpenses());
			System.out.println("Total earnings of tutor = " + t.getTotalEarnings());

			appointments.makeAppointment2(t, extern1.getIdStudent(), LocalDate.parse("2023-11-24"),
					LocalTime.parse("09:10"), LocalTime.parse("10:30"));
			
			List<IAppointment> app = appointments.getAppointmentsExternStudent(extern1.getIdStudent());
			
			for (IAppointment extAppp : appointments.getExternList().values()) {
				LocalDateTime startDateTime = LocalDateTime.of(extAppp.getDate(), extAppp.getStartTime());
				LocalDateTime endDateTime = LocalDateTime.of(extAppp.getDate(), extAppp.getEndTime());
				Duration duration = Duration.between(startDateTime, endDateTime);
				double cost = (duration.toMinutes() * t.getHourlyRate()) / 60;
				extern1.setTotalExpenses(cost);
			}
			
			IAppointment firstAppointment = null;
			for (IAppointment a : app) {
				firstAppointment = a;
				break;
			}
			
			Student st = externStudents.searchStudentByID(firstAppointment.getExternStudent());

			System.out.println("Appointment : " + firstAppointment.getTutor().getName() + " with "
					+ st.getName() + " on " + firstAppointment.getDate() + " at "
					+ firstAppointment.getStartTime() + " , cost = " + firstAppointment.getCost() + " : "
					+ firstAppointment.getStatus());

			System.out.println("Total expenses of student = " + extern1.getTotalExpenses());
			System.out.println("Total earnings of tutor = " + t.getTotalEarnings());
			System.out.println(t.printTutor());

			// Banking
			System.out.println("============== Bank ============= ");

			Bank bankService = new BankServiceLocator().getBank();
			((BankSoapBindingStub) bankService).setMaintainSession(true);
			
			System.out.println("============== Before Bank ============= ");

			bankService.printCustomers();


			if (appointments != null) {
				for (IAppointment appl : appointments.getValidatedAppointments().values()) {
					// Tutors
					bankService.newAccount(appl.getTutor().getName(), 0, appl.getTutor().getPreferredCurrency());
					Customer tutor = bankService.retrieveCustomerByName(appl.getTutor().getName());

					if (appl.getStudent()!= null) {
						bankService.newAccount(appl.getStudent().getName(), 200, appl.getStudent().getPreferredCurrency());
						Customer stu = bankService.retrieveCustomerByName(appl.getStudent().getName());
						bankService.virement(stu.getRib(), tutor.getRib(), appl.getCost());

					}else if (appl.getExternStudent() != -1) {
						Student s = externStudents.searchStudentByID(appl.getExternStudent());
						bankService.newAccount(s.getName(), 200, s.getPreferredCurrency());
						Customer stu = bankService.retrieveCustomerByName(s.getName());
						bankService.virement(stu.getRib(), tutor.getRib(), appl.getCost());

					}
					System.out.println("============== ");

				}
				
				System.out.println("============== After Bank ============= ");

				bankService.printCustomers();
			}

		} catch (RemoteException | ServiceException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}

	}

}
