package com.GustaveEiffelTutoring.server;

import com.GustaveEiffelTutoring.common.IAppointment;
import com.GustaveEiffelTutoring.common.IAppointmentList;
import com.GustaveEiffelTutoring.common.IStudent;
import com.GustaveEiffelTutoring.common.ITutor;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AppointmentList extends UnicastRemoteObject implements IAppointmentList, Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, IAppointment> waitingList;
	private HashMap<Integer, IAppointment>  appointments;
	int id;

	public AppointmentList() throws RemoteException {
		waitingList = new HashMap<>();
		appointments = new HashMap<>();
		id = 0;
	}

	public List<IAppointment> getAppointmentsTutor(ITutor tutor) throws RemoteException {
		List<IAppointment> tutorAppointments = new ArrayList<>();
		for (IAppointment appointment : getMergedAppointments().values()) {
			if (appointment.getTutor().getId() == tutor.getId()) {
				tutorAppointments.add(appointment);
			}
		}
		return tutorAppointments;
	}

	public List<IAppointment> getAppointmentsStudent(IStudent student) throws RemoteException {
	    List<IAppointment> studentAppointments = new ArrayList<>();

	    for (IAppointment appointment : getMergedAppointments().values()) {
	        if (appointment.getStudent().getIdStudent() == student.getIdStudent()) {
	            studentAppointments.add(appointment);
	        }
	    }
	    return studentAppointments;
	}

	public IAppointment getAppointment(ITutor tutor, IStudent student) throws RemoteException {
		for (IAppointment appointment : getMergedAppointments().values()) {
			if (appointment.getTutor().getId() == tutor.getId()
					&& appointment.getStudent().getIdStudent() == student.getIdStudent()) {
				return appointment;
			}
		}
		return null;
	}

	public void makeAppointment(ITutor tutor, IStudent student, LocalDate date, LocalTime startTime, LocalTime endTime)
			throws RemoteException {

		IAppointment appointment = new Appointment();

		LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
		LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
		Duration duration = Duration.between(startDateTime, endDateTime);
		System.out.println(startDateTime);

		double cost = (duration.toMinutes() * tutor.getHourlyRate()) / 60;

		try {
			this.setId(id + 1);
			appointment.setId(this.id);
			appointment.setTutor(tutor);
			appointment.setStudent(student);
			appointment.setDate(date);
			appointment.setStartTime(startTime);
			appointment.setEndTime(endTime);

			if (student.getNature().equals("INTERN")) {
				appointment.setCost(0);
			} else {
				appointment.setCost(cost);
				tutor.addEarnings(cost);
				student.addExpenses(cost);
			}

			if (tutor.isAvailable(startDateTime)) {
				appointment.setStatus(Appointment.AppointmentStatus.VALIDATED.name());

				List<LocalDateTime> updatedSlots = new ArrayList<>(tutor.getAvailableSlots());
				updatedSlots.remove(startDateTime);
				tutor.setAvailableSlots(updatedSlots);

				appointments.put(appointment.getId(), appointment);
				System.out.println(appointment.toString());
				System.out.println("Your appointment with " + tutor.getName() + " on " + date + " at " + startTime
						+ " has been added successfully ");

			} else {
				appointment.setStatus(Appointment.AppointmentStatus.PENDING.name());
				waitingList.put(appointment.getId(), appointment);
				System.out.println(this.toString());
				System.out.println("You are on the waiting list for the appointment with " + tutor.getName() + " on "
						+ date + " at " + startTime);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public HashMap<Integer, IAppointment> getValidatedAppointments() throws RemoteException {
		return appointments;
	}

	public HashMap<Integer, IAppointment> getWaitingList() throws RemoteException {
		return waitingList;
	}
	
	public HashMap<Integer, IAppointment> getMergedAppointments() throws RemoteException {
		HashMap<Integer, IAppointment> mergedMap = new HashMap<>(appointments);
		mergedMap.putAll(waitingList);
		System.out.println(mergedMap);
		return mergedMap;
	}
	
	public IAppointment searchAppointmentByID(int id) throws RemoteException {
		for (IAppointment app : getMergedAppointments().values()) {
			if (app.getId() == id) {
				return app;
			}
		}
		return null;
	}

	public int getId() throws RemoteException{
		return id;
	}

	public void setId(int id) throws RemoteException{
		this.id = id;
	}

}
