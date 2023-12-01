package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import common.ITutor;

public class Tutor extends UnicastRemoteObject implements ITutor, Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String email;
	private String password;
	private String name;
	private List<String> expertise;
	private List<LocalDateTime> availableSlots;
	private double hourlyRate;
	private double totalEarnings;
	private String preferredCurrency;

	public Tutor() throws RemoteException {
	}

	public Tutor(String name, String email, String password) throws RemoteException {
		this.name = name;
		this.password = password;
		this.email = email;
		this.hourlyRate = 0.0;
		this.expertise = new ArrayList<>();
		this.availableSlots = new ArrayList<>();
		this.preferredCurrency = "EUR";
		this.totalEarnings = 0.0;
	}

	public int getId() throws RemoteException {
		return id;
	}

	public void setId(int uniqueId) throws RemoteException {
		this.id = uniqueId;
	}

	public String getPassword() throws RemoteException {
		return password;
	}

	public void setPassword(String password) throws RemoteException {
		this.password = password;
	}

	public String getName() throws RemoteException {
		return name;
	}

	public String getEmail() throws RemoteException {
		return email;
	}

	public void setEmail(String email) throws RemoteException {
		this.email = email;
	}

	public List<String> getExpertise() throws RemoteException {
		return expertise;
	}

	public double getHourlyRate() throws RemoteException {
		return hourlyRate;
	}

	public double getTotalEarnings() throws RemoteException {
		return totalEarnings;
	}

	public void addEarnings(double earnings) throws RemoteException {
		this.totalEarnings += earnings;
	}

	public void setAvailableSlots(List<LocalDateTime> availableSlots) throws RemoteException {
		this.availableSlots = availableSlots;
	}

	public List<LocalDateTime> getAvailableSlots() throws RemoteException {
		return availableSlots;
	}

	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	public void setExpertise(List<String> expertise) throws RemoteException {
		this.expertise = expertise;
	}

	public void setHourlyRate(double rate) throws RemoteException {
		this.hourlyRate = rate;
	}

	public String printTutor() throws RemoteException {
		return "Tutor [name=" + name + ", expertise=" + expertise + ", availableSlots=" + availableSlots
				+ ", hourlyRate=" + hourlyRate + ", currency = "+ preferredCurrency + "]";
	}

	public boolean IsAvailable(LocalDateTime time) throws RemoteException {
		if (this.availableSlots.contains(time)) {
			return true;
		}
		return false;
	}

	public String getPreferredCurrency() throws RemoteException {
		return preferredCurrency;
	}

	public void setPreferredCurrency(String preferredCurrency) throws RemoteException {
		this.preferredCurrency = preferredCurrency;
	}

}
