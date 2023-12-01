package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import common.IAppointment;
import common.IStudent;
import common.ITutor;

public class Appointment extends UnicastRemoteObject implements IAppointment, Serializable {
	private static final long serialVersionUID = 1L;

	public enum AppointmentStatus {
		PENDING, VALIDATED, CANCELED
	}

	private int id;
	private IStudent student;
	private int externStudent;
	private ITutor tutor;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private double cost;
	private LocalDateTime reservation_date;
	private AppointmentStatus status;
	
	public Appointment() throws RemoteException {
	}

	public Appointment(IStudent student, ITutor tutor, LocalDate date, LocalTime startTime, LocalTime endTime)
			throws RemoteException {
		this.student = student;
		this.externStudent = -1;
		this.tutor = tutor;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = AppointmentStatus.PENDING;
		this.cost = 0.0;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		this.reservation_date = LocalDateTime.now();
	}
	
	public Appointment(int externStudent, ITutor tutor, LocalDate date, LocalTime startTime, LocalTime endTime)
			throws RemoteException {
		this.externStudent = externStudent;
		this.student = null;
		this.tutor = tutor;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = AppointmentStatus.PENDING;
		this.cost = 0.0;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		this.reservation_date = LocalDateTime.now();
	}

	
	public int getExternStudent() throws RemoteException {
		return externStudent;
	}

	public void setExternStudent(int externStudent) throws RemoteException {
		this.externStudent = externStudent;
	}

	public IStudent getStudent() throws RemoteException {
		return student;
	}

	public void setStudent(IStudent student) throws RemoteException {
		this.student = student;
	}

	public ITutor getTutor() throws RemoteException {
		return tutor;
	}

	public void setTutor(ITutor tutor) throws RemoteException {
		this.tutor = tutor;
	}

	public LocalDate getDate() throws RemoteException {
		return date;
	}

	public void setDate(LocalDate date) throws RemoteException {
		this.date = date;
	}

	public LocalTime getStartTime() throws RemoteException {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) throws RemoteException {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() throws RemoteException {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) throws RemoteException {
		this.endTime = endTime;
	}

	public void setStatus(String status) throws RemoteException {
		this.status = AppointmentStatus.valueOf(status);
	}

	public String getStatus() throws RemoteException {
		return status.name();
	}

	public void validateAppointment() throws RemoteException {
		this.status = AppointmentStatus.VALIDATED;
		System.out.println("Appointment validated");
	}

	public void cancelAppointment() throws RemoteException {
		this.status = AppointmentStatus.CANCELED;
		System.out.println("Appointment canceled");
	}

	public double getCost() throws RemoteException {
		return cost;
	}

	public void setCost(double cost) throws RemoteException {
		this.cost = cost;
	}

	public LocalDateTime getReservation_date() throws RemoteException {
		return reservation_date;
	}

	public void setReservation_date(LocalDateTime reservation_date) throws RemoteException {
		this.reservation_date = reservation_date;
	}

	public String getAppointment() throws RemoteException {
		return "Appointment [student=" + this.student.getIdStudent() + ", tutor=" + this.tutor.getId() + ", date=" + this.date + ", startTime=" + this.startTime
				+ ", endTime=" + this.endTime + ", status=" + this.status + "]";
	}

	public int getId() throws RemoteException {
		return id;
	}

	public void setId(int id) throws RemoteException {
		this.id = id;
	}

}
