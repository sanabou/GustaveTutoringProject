package com.GustaveEiffelTutoring.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public interface ITutor extends Remote {
	public int getId() throws RemoteException;

	public void setId(int uniqueId) throws RemoteException;

	public String getPassword() throws RemoteException;

	public void setPassword(String password) throws RemoteException;

	public String getName() throws RemoteException;

	public String getEmail() throws RemoteException;

	public void setEmail(String email) throws RemoteException;

	public List<String> getExpertise() throws RemoteException;

	public double getHourlyRate() throws RemoteException;

	public double getTotalEarnings() throws RemoteException;

	public void addEarnings(double earnings) throws RemoteException;

	public void setAvailableSlots(List<LocalDateTime> availableSlots) throws RemoteException;

	public List<LocalDateTime> getAvailableSlots() throws RemoteException;

	public void setName(String name) throws RemoteException;

	public void setExpertise(List<String> expertise) throws RemoteException;

	public void setHourlyRate(double rate) throws RemoteException;

	public String getTutor() throws RemoteException;

	public boolean isAvailable(LocalDateTime time) throws RemoteException ;

}
