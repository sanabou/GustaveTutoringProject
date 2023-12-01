package externStudent;

public class Student {
	public enum StudentNature {
		INTERN, EXTERN
	}

	private int idStudent;
	private String name;
	private String password;
	private String email;
	private double totalExpenses;
	private StudentNature nature;
	private String preferredCurrency;

	public Student() {
	}

	public Student(String name, String email, String password){
		this.name = name;
		this.email = email;
		this.password = password;
		this.preferredCurrency = "EUR";
		this.totalExpenses = 0.0;
		if (email.toLowerCase().contains("@edu.univ-eiffel.fr")) {
			this.nature = StudentNature.INTERN;
		} else {
			this.nature = StudentNature.EXTERN;
		}
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getEmail(){
		return email;
	}

	public String getNature() {
		return nature.name();
	}

	public void setNature(String nature) {
		this.nature = StudentNature.valueOf(nature);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalExpenses(){
		return totalExpenses;
	}

	public void addExpenses(double expenses) {
		this.totalExpenses += expenses;
	}

	public String printStudent() {
        return "Student [id = " + idStudent + ", name= " + name + ", email= " + email + ", nature= " + nature.toString() +
                ", expenses= " + totalExpenses + "currency = " + preferredCurrency +"]";
    }

	public String getPreferredCurrency() {
		return preferredCurrency;
	}

	public void setPreferredCurrency(String preferredCurrency) {
		this.preferredCurrency = preferredCurrency;
	}

}
