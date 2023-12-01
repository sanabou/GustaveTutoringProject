package server;

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

import common.*;
import server.Appointment.AppointmentStatus;

public class AppointmentList extends UnicastRemoteObject implements IAppointmentList, Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, IAppointment> waitingList;
	private HashMap<Integer, IAppointment> appointments;
	private HashMap<Integer, IAppointment> externAppointments;
	int id;

	public AppointmentList() throws RemoteException {
		waitingList = new HashMap<>();
		appointments = new HashMap<>();
		externAppointments = new HashMap<>();
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
	
	public List<IAppointment> getAppointmentsExternStudent(int externStudent) throws RemoteException {
        List<IAppointment> studentAppointments = new ArrayList<>();

        for (IAppointment appointment : getMergedAppointments().values()) {
            if (appointment.getExternStudent() == externStudent) {
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
	
	public IAppointment getAppointmentExtern(ITutor tutor, int student) throws RemoteException {
        for (IAppointment appointment : getMergedAppointments().values()) {
            if (appointment.getTutor().getId() == tutor.getId()
                    && appointment.getExternStudent() == student) {
                return appointment;
            }
        }
        return null;
    }
	
	public int getNextAvailableAppointmentId() throws RemoteException {
	    int maxId = 0;
	    for (IAppointment app : getMergedAppointments().values()) {
	        if (app.getId() > maxId) {
	            maxId = app.getId();
	        }
	    }
	    return maxId + 1;
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
			int nextId = getNextAvailableAppointmentId();
			appointment.setId(nextId);
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

			if (tutor.IsAvailable(startDateTime)) {
				appointment.setStatus(AppointmentStatus.VALIDATED.name());

				List<LocalDateTime> updatedSlots = new ArrayList<>(tutor.getAvailableSlots());
				updatedSlots.remove(startDateTime);
				tutor.setAvailableSlots(updatedSlots);

				appointments.put(appointment.getId(), appointment);
				System.out.println(appointment.toString());
				System.out.println("Your appointment with " + tutor.getName() + " on " + date + " at " + startTime
						+ " has been added successfully ");

			} else {
				appointment.setStatus(AppointmentStatus.PENDING.name());
				waitingList.put(appointment.getId(), appointment);
				System.out.println(this.toString());
				System.out.println("You are on the waiting list for the appointment with " + tutor.getName() + " on "
						+ date + " at " + startTime);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void makeAppointment2(ITutor tutor, int student, LocalDate date, LocalTime startTime, LocalTime endTime)
			throws RemoteException {

		System.out.println("hello");
		IAppointment appointment = new Appointment();

		LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
		LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
		Duration duration = Duration.between(startDateTime, endDateTime);
		System.out.println(startDateTime);

		double cost = (duration.toMinutes() * tutor.getHourlyRate()) / 60;

		try {
//			int nextId = getNextAvailableAppointmentId();
			appointment.setId(student);
			appointment.setTutor(tutor);
			appointment.setExternStudent(student);
			appointment.setDate(date);
			appointment.setStartTime(startTime);
			appointment.setEndTime(endTime);

			appointment.setCost(cost);
			tutor.addEarnings(cost);

			if (tutor.IsAvailable(startDateTime)) {
				appointment.setStatus(AppointmentStatus.VALIDATED.name());

				List<LocalDateTime> updatedSlots = new ArrayList<>(tutor.getAvailableSlots());
				updatedSlots.remove(startDateTime);
				tutor.setAvailableSlots(updatedSlots);

				externAppointments.put(appointment.getId(), appointment);
				System.out.println(appointment.toString());
				System.out.println("Your appointment with " + tutor.getName() + " on " + date + " at " + startTime
						+ " has been added successfully ");

			} else {
				appointment.setStatus(AppointmentStatus.PENDING.name());
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
		HashMap<Integer, IAppointment> mergedMap = new HashMap<>(appointments);
		mergedMap.putAll(externAppointments);
		return mergedMap;
	}

	public HashMap<Integer, IAppointment> getWaitingList() throws RemoteException {
		return waitingList;
	}
	
	public HashMap<Integer, IAppointment> getExternList() throws RemoteException {
		return externAppointments;
	}

	public HashMap<Integer, IAppointment> getMergedAppointments() throws RemoteException {
		HashMap<Integer, IAppointment> mergedMap = new HashMap<>(appointments);
		mergedMap.putAll(waitingList);
		mergedMap.putAll(externAppointments);
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

	public int getId() throws RemoteException {
		return id;
	}

	public void setId(int id) throws RemoteException {
		this.id = id;
	}

}
