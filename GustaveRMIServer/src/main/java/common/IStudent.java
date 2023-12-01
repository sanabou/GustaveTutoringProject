package common;

import java.rmi.Remote;

import java.rmi.RemoteException;

public interface IStudent extends Remote {

	public String getName() throws RemoteException;

	public String getPassword() throws RemoteException;

	public void setPassword(String password) throws RemoteException;

	public String getEmail() throws RemoteException;

	public String getNature() throws RemoteException;

	public void setNature(String nature) throws RemoteException;

	public void setEmail(String email) throws RemoteException;

	public int getIdStudent() throws RemoteException;

	public void setIdStudent(int idStudent) throws RemoteException;

	public void setName(String name) throws RemoteException;

	public double getTotalExpenses() throws RemoteException;

	public void addExpenses(double expenses) throws RemoteException;

	public String printStudent() throws RemoteException;

	public String getPreferredCurrency() throws RemoteException;

	public void setPreferredCurrency(String preferredCurrency) throws RemoteException;

}
