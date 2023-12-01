package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public interface IAppointmentList extends Remote {

	public List<IAppointment> getAppointmentsTutor(ITutor tutor) throws RemoteException;

	public List<IAppointment> getAppointmentsStudent(IStudent student) throws RemoteException;

	public IAppointment getAppointment(ITutor tutor, IStudent student) throws RemoteException;

	public void makeAppointment(ITutor tutor, IStudent student, LocalDate date, LocalTime startTime, LocalTime endTime)
			throws RemoteException;

	public void makeAppointment2(ITutor tutor, int student, LocalDate date, LocalTime startTime, LocalTime endTime)
			throws RemoteException;

	public HashMap<Integer, IAppointment> getValidatedAppointments() throws RemoteException;

	public HashMap<Integer, IAppointment> getWaitingList() throws RemoteException;

	public HashMap<Integer, IAppointment> getMergedAppointments() throws RemoteException;

	public IAppointment searchAppointmentByID(int id) throws RemoteException;

	public int getId() throws RemoteException;

	public void setId(int id) throws RemoteException;

	public List<IAppointment> getAppointmentsExternStudent(int externStudent) throws RemoteException;

	public IAppointment getAppointmentExtern(ITutor tutor, int student) throws RemoteException;

	public HashMap<Integer, IAppointment> getExternList() throws RemoteException ;

}
