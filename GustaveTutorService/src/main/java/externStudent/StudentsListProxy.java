package externStudent;

public class StudentsListProxy implements externStudent.StudentsList {
  private String _endpoint = null;
  private externStudent.StudentsList studentsList = null;
  
  public StudentsListProxy() {
    _initStudentsListProxy();
  }
  
  public StudentsListProxy(String endpoint) {
    _endpoint = endpoint;
    _initStudentsListProxy();
  }
  
  private void _initStudentsListProxy() {
    try {
      studentsList = (new externStudent.StudentsListServiceLocator()).getStudentsList();
      if (studentsList != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)studentsList)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)studentsList)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (studentsList != null)
      ((javax.xml.rpc.Stub)studentsList)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public externStudent.StudentsList getStudentsList() {
    if (studentsList == null)
      _initStudentsListProxy();
    return studentsList;
  }
  
  public boolean studentRegistered(java.lang.String email) throws java.rmi.RemoteException{
    if (studentsList == null)
      _initStudentsListProxy();
    return studentsList.studentRegistered(email);
  }
  
  public externStudent.Student[] getAllStudents() throws java.rmi.RemoteException{
    if (studentsList == null)
      _initStudentsListProxy();
    return studentsList.getAllStudents();
  }
  
  public externStudent.Student searchStudentByID(int id) throws java.rmi.RemoteException{
    if (studentsList == null)
      _initStudentsListProxy();
    return studentsList.searchStudentByID(id);
  }
  
  public boolean dynamicStudentsRegistration(externStudent.Student student) throws java.rmi.RemoteException{
    if (studentsList == null)
      _initStudentsListProxy();
    return studentsList.dynamicStudentsRegistration(student);
  }
  
  public externStudent.Student searchStudentByEmail(java.lang.String email) throws java.rmi.RemoteException{
    if (studentsList == null)
      _initStudentsListProxy();
    return studentsList.searchStudentByEmail(email);
  }
  
  public boolean staticStudentsRegistration(externStudent.Student student) throws java.rmi.RemoteException{
    if (studentsList == null)
      _initStudentsListProxy();
    return studentsList.staticStudentsRegistration(student);
  }
  
  public int getId() throws java.rmi.RemoteException{
    if (studentsList == null)
      _initStudentsListProxy();
    return studentsList.getId();
  }
  
  public void setId(int id) throws java.rmi.RemoteException{
    if (studentsList == null)
      _initStudentsListProxy();
    studentsList.setId(id);
  }
  
  
}