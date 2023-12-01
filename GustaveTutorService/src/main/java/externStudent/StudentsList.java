/**
 * StudentsList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package externStudent;

public interface StudentsList extends java.rmi.Remote {
    public boolean studentRegistered(java.lang.String email) throws java.rmi.RemoteException;
    public externStudent.Student[] getAllStudents() throws java.rmi.RemoteException;
    public externStudent.Student searchStudentByID(int id) throws java.rmi.RemoteException;
    public boolean dynamicStudentsRegistration(externStudent.Student student) throws java.rmi.RemoteException;
    public externStudent.Student searchStudentByEmail(java.lang.String email) throws java.rmi.RemoteException;
    public boolean staticStudentsRegistration(externStudent.Student student) throws java.rmi.RemoteException;
    public int getId() throws java.rmi.RemoteException;
    public void setId(int id) throws java.rmi.RemoteException;
}
