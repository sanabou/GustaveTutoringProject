package com.GustaveEiffelTutoring.server;

import com.GustaveEiffelTutoring.common.ITutor;
import com.GustaveEiffelTutoring.common.ITutorsList;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TutorsList extends UnicastRemoteObject implements ITutorsList, Serializable {
	private static final long serialVersionUID = 1L;

	private final HashMap<Integer, ITutor> staticRegisteredTutors = new HashMap<>();
	private final HashMap<Integer, ITutor> dynamicRegisteredTutors = new HashMap<>();
	int id;
	public TutorsList() throws RemoteException {
		id = 0;
	}

	public boolean staticTutorRegistration(ITutor tutor) throws RemoteException {
		try {
			if (!tutorRegistered(tutor.getEmail())) {
				this.setId(this.id + 1);
				tutor.setId(this.id);
				staticRegisteredTutors.put(tutor.getId(), tutor);
				System.out.println(tutor.getTutor());
				System.out.println("Static tutor registered with ID: " + tutor.getId());
				return true;
			} else {
				System.out.println("This email address is already used by an account");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean dynamicTutorRegistration(ITutor tutor) throws RemoteException {
		try {
			if (!tutorRegistered(tutor.getEmail())) {
				this.setId(this.id + 1);
				tutor.setId(this.id);
				dynamicRegisteredTutors.put(tutor.getId(), tutor);
				System.out.println(tutor.getTutor());
				System.out.println("Dynamic tutor registered with ID: " + tutor.getId());
				return true;
			} else {
				System.out.println("This email address is already used by an account");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean tutorRegistered(String email) throws RemoteException {
		for (ITutor tutor : getAllTutors().values()) {
			if (tutor.getEmail().toLowerCase().trim().equals(email.toLowerCase().trim())) {
				return true;
			}
		}
		return false;
	}

	public HashMap<Integer, ITutor> getAllTutors() throws RemoteException {
		HashMap<Integer, ITutor> mergedMap = new HashMap<>(staticRegisteredTutors);
		mergedMap.putAll(dynamicRegisteredTutors);
		System.out.println(mergedMap);
		return mergedMap;
	}

	public ITutor searchTutorByID(int id) throws RemoteException {
		for (ITutor tutor : getAllTutors().values()) {
			if (tutor.getId() == id) {
				return tutor;
			}
		}
		return null;
	}

	public ITutor searchTutorByEmail(String email) throws RemoteException {
		for (ITutor tutor : getAllTutors().values()) {
			if (tutor.getEmail().toLowerCase().trim().equals(email.toLowerCase().trim())) {
				return tutor;
			}
		}
		return null;
	}

	public List<ITutor> searchTutorsByExpertise(String subject) throws RemoteException {
		List<ITutor> matchingTutors = new ArrayList<>();
		for (ITutor tutor : getAllTutors().values()) {
			List<String> tutorExpertise = tutor.getExpertise();
			for (String expertise : tutorExpertise) {
				if (expertise.toLowerCase().trim().contains(subject.toLowerCase().trim())) {
					matchingTutors.add(tutor);
					break;
				}
			}
		}
		return matchingTutors;
	}

	public List<ITutor> searchTutorsByName(String name) throws RemoteException {
		List<ITutor> matchingTutors = new ArrayList<>();
		for (ITutor tutor : getAllTutors().values()) {
			if (tutor.getName().toLowerCase().contains(name.toLowerCase())) {
				matchingTutors.add(tutor);
			}
		}
		return matchingTutors;
	}

	public int getId() throws RemoteException{
		return id;
	}

	public void setId(int id) throws RemoteException{
		this.id = id;
	}

}
