package com.GustaveEiffelTutoring.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface IStudentsList extends Remote {

	public boolean staticStudentsRegistration(IStudent student) throws RemoteException;

	public boolean dynamicStudentsRegistration(IStudent student) throws RemoteException;

	public boolean studentRegistered(String email) throws RemoteException;

	public HashMap<Integer, IStudent> getAllStudents() throws RemoteException;

	public IStudent searchStudentByID(int id) throws RemoteException;

	public IStudent searchStudentByEmail(String email) throws RemoteException;

	public int getId() throws RemoteException;

	public void setId(int id) throws RemoteException;
}
