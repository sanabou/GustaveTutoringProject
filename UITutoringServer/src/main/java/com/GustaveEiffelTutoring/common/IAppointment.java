package com.GustaveEiffelTutoring.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface IAppointment extends Remote {

	public IStudent getStudent() throws RemoteException;

	public void setStudent(IStudent student) throws RemoteException;

	public ITutor getTutor() throws RemoteException;

	public void setTutor(ITutor tutor) throws RemoteException;

	public LocalDate getDate() throws RemoteException;

	public void setDate(LocalDate date) throws RemoteException;

	public LocalTime getStartTime() throws RemoteException;

	public void setStartTime(LocalTime startTime) throws RemoteException;

	public LocalTime getEndTime() throws RemoteException;

	public void setEndTime(LocalTime endTime) throws RemoteException;

	public void setStatus(String status) throws RemoteException;

	public String getStatus() throws RemoteException;

	public void validateAppointment() throws RemoteException;

	public void cancelAppointment() throws RemoteException;

	public double getCost() throws RemoteException;

	public void setCost(double cost) throws RemoteException;

	public LocalDateTime getReservation_date() throws RemoteException;

	public void setReservation_date(LocalDateTime reservation_date) throws RemoteException;

	public String getAppointment() throws RemoteException;

	public int getId() throws RemoteException;

	public void setId(int id) throws RemoteException;
}
