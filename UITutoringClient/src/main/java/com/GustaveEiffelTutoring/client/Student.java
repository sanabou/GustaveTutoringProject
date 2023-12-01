package com.GustaveEiffelTutoring.client;

import com.GustaveEiffelTutoring.common.IStudent;

import java.io.Serializable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Student extends UnicastRemoteObject implements IStudent, Serializable {
	private static final long serialVersionUID = 1L;	

	public enum StudentNature {
		INTERN, EXTERN
	}

	private int idStudent;
	private String name;
	private String password;
	private String email;
	private double totalExpenses;
	private StudentNature nature;

	public Student() throws RemoteException {
	}

	public Student(String name, String email, String password) throws RemoteException {
		this.name = name;
		this.email = email;
		this.password = password;
		this.totalExpenses = 0.0;
		if (email.toLowerCase().contains("@edu.univ-eiffel.fr")) {
			this.nature = StudentNature.INTERN;
		} else {
			this.nature = StudentNature.EXTERN;
		}
	}

	public String getName() throws RemoteException {
		return name;
	}

	
	public String getPassword() throws RemoteException{
		return password;
	}

	public void setPassword(String password) throws RemoteException{
		this.password = password;
	}

	public String getEmail() throws RemoteException {
		return email;
	}

	public String getNature() throws RemoteException {
		return nature.name();
	}

	public void setNature(String nature) throws RemoteException {
		this.nature = StudentNature.valueOf(nature);
	}

	public void setEmail(String email) throws RemoteException {
		this.email = email;
	}

	public int getIdStudent() throws RemoteException {
		return idStudent;
	}

	public void setIdStudent(int idStudent) throws RemoteException {
		this.idStudent = idStudent;
	}

	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	public double getTotalExpenses() throws RemoteException {
		return totalExpenses;
	}

	public void addExpenses(double expenses) throws RemoteException {
		this.totalExpenses += expenses;
	}

	public String studentPrint() throws RemoteException {
		return "Student [id = " + idStudent + ", name= " + name + ", email= " + email +", nature= " + nature.toString() +
				", expenses= "+ totalExpenses + "]";
	}

}
