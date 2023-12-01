/**
 * StudentsListService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package externStudent;

public interface StudentsListService extends javax.xml.rpc.Service {
    public java.lang.String getStudentsListAddress();

    public externStudent.StudentsList getStudentsList() throws javax.xml.rpc.ServiceException;

    public externStudent.StudentsList getStudentsList(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
