package com.GustaveEiffelTutoring.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ITutorsList extends Remote {

	public boolean staticTutorRegistration(ITutor tutor) throws RemoteException;

	public boolean dynamicTutorRegistration(ITutor tutor) throws RemoteException;

	public boolean tutorRegistered(String email) throws RemoteException;

	public HashMap<Integer, ITutor> getAllTutors() throws RemoteException;
	public ITutor searchTutorByID(int id) throws RemoteException;

	public ITutor searchTutorByEmail(String email) throws RemoteException;

	public List<ITutor> searchTutorsByExpertise(String subject) throws RemoteException;

	public List<ITutor> searchTutorsByName(String name) throws RemoteException;

}
