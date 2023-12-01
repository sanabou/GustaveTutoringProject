package externStudent;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;

import common.IStudent;

public class StudentsList implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Student> staticRegisteredStudents;
    private HashMap<Integer, Student> dynamicRegisteredStudents;
    private int id;

    public StudentsList() {
        staticRegisteredStudents = new HashMap<Integer, Student>();
        dynamicRegisteredStudents = new HashMap<Integer, Student>();
        id = 0;
    }
    
    public boolean staticStudentsRegistration(Student student) {
        try {
            if (!studentRegistered(student.getEmail())) {
            	int nextId = getNextAvailableStudentId();
                student.setIdStudent(nextId);
                staticRegisteredStudents.put(student.getIdStudent(), student);
                System.out.println(student.printStudent());
                System.out.println("Static Student registered with ID: " + student.getIdStudent());
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

    public boolean dynamicStudentsRegistration(Student student) {
        try {
            if (!studentRegistered(student.getEmail())) {
            	int nextId = getNextAvailableStudentId();
                student.setIdStudent(nextId);
                dynamicRegisteredStudents.put(student.getIdStudent(), student);
                System.out.println(student.printStudent());
                System.out.println("Dynamic Student registered with ID: " + student.getIdStudent());
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
    
    public int getNextAvailableStudentId() throws RemoteException {
	    int maxId = 0;
	    for (Student student : getAllStudents()) {
	        if (student.getIdStudent() > maxId) {
	            maxId = student.getIdStudent();
	        }
	    }
	    return maxId + 1;
	}

    public boolean studentRegistered(String email) {
        for (Student student : getAllStudents()) {
            if (student.getEmail().toLowerCase().trim().equals(email.toLowerCase().trim())) {
			    return true;
			}
        }
        return false;
    }

    public Student[] getAllStudents() {
        HashMap<Integer, Student> mergedMap = new HashMap<>(staticRegisteredStudents);
        mergedMap.putAll(dynamicRegisteredStudents);

        Collection<Student> studentsCollection = mergedMap.values();
        return studentsCollection.toArray(new Student[0]);
    }

    public Student searchStudentByID(int id) {
        for (Student students : getAllStudents()) {
            if (students.getIdStudent() == id) {
                return students;
            }
        }
        return null;
    }

    public Student searchStudentByEmail(String email) {
        for (Student students : getAllStudents()) {
            if (students.getEmail().toLowerCase().trim().equals(email.toLowerCase().trim())) {
                return students;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
