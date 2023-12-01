package com.GustaveEiffelTutoring.client;

import com.GustaveEiffelTutoring.common.IStudent;
import com.GustaveEiffelTutoring.common.IStudentsList;

import java.io.Serializable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class StudentsList extends UnicastRemoteObject implements IStudentsList, Serializable{
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, IStudent> staticRegisteredStudents ;
	private HashMap<Integer, IStudent> dynamicRegisteredStudents ;
	private int id;
	
	public StudentsList() throws RemoteException {
		staticRegisteredStudents = new HashMap<Integer, IStudent>();
		dynamicRegisteredStudents = new HashMap<Integer, IStudent>();
		id = 0;
	}
	
	public boolean staticStudentsRegistration(IStudent student) throws RemoteException {
		try {
			if (!studentRegistered(student.getEmail())) {
				this.setId(this.id + 1);
				student.setIdStudent(this.id);
				staticRegisteredStudents.put(student.getIdStudent(), student);
				System.out.println(student.studentPrint());
				System.out.println("Static Student registered with ID: " + student.getIdStudent());
				return true;
			}else {
				System.out.println("This email address is already used by an account");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean dynamicStudentsRegistration(IStudent student) throws RemoteException {
		try {
			if (!studentRegistered(student.getEmail())) {
				this.setId(this.id + 1);
				student.setIdStudent(this.id);
				dynamicRegisteredStudents.put(student.getIdStudent(), student);
				System.out.println(student.studentPrint());
				System.out.println("Dynamic Student registered with ID: " + student.getIdStudent());
				return true;
			}else {
				System.out.println("This email address is already used by an account");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	
	public boolean studentRegistered(String email) throws RemoteException {
    	for (IStudent student : getAllStudents().values()) {
			if (student.getEmail().toLowerCase().trim().equals(email.toLowerCase().trim())) {
				return true;
			}
		}
		return false;
    }

	public HashMap<Integer, IStudent> getAllStudents() throws RemoteException {
		HashMap<Integer, IStudent> mergedMap = new HashMap<>(staticRegisteredStudents);
		mergedMap.putAll(dynamicRegisteredStudents);
		System.out.println(mergedMap);
		return mergedMap;
	}
	
	public IStudent searchStudentByID(int id) throws RemoteException {
		for (IStudent students : getAllStudents().values()) {
			if (students.getIdStudent() == id) {
				return students;
			}
		}
		return null;
	}
	
	public IStudent searchStudentByEmail(String email) throws RemoteException {
		for (IStudent students : getAllStudents().values()) {
			if (students.getEmail().toLowerCase().trim().equals(email.toLowerCase().trim())) {
				return students;
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
