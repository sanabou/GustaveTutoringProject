package server;

import java.io.Serializable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.ITutor;
import common.ITutorsList;

public class TutorsList extends UnicastRemoteObject implements ITutorsList, Serializable {
	private static final long serialVersionUID = 1L;

	private static HashMap<Integer, ITutor> staticRegisteredtutors;
	private static HashMap<Integer, ITutor> dynamicRegisteredtutors;
	int id;

	public TutorsList() throws RemoteException {
		staticRegisteredtutors = new HashMap<>();
		dynamicRegisteredtutors = new HashMap<>();
		id = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean staticTutorRegistration(ITutor tutor) throws RemoteException {
		try {
			if (!tutorRegistered(tutor.getEmail())) {
				this.setId(this.id + 1);
	            tutor.setId(this.id);
				staticRegisteredtutors.put(tutor.getId(), tutor);
				System.out.println(tutor.toString());
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
				dynamicRegisteredtutors.put(tutor.getId(), tutor);
				System.out.println(tutor.toString());
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
		HashMap<Integer, ITutor> mergedMap = new HashMap<>(staticRegisteredtutors);
		mergedMap.putAll(dynamicRegisteredtutors);
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
				if (expertise.toLowerCase().contains(subject.toLowerCase())) {
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

	public void printTutors() throws RemoteException {
		for (ITutor tutor : getAllTutors().values()) {
			System.out.println(tutor.printTutor());
		}
	}

}
