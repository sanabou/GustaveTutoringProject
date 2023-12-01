package bank;

public class Customer {
	private String name;
	private Long rib;
	private double balance;
	private String currency;

	public Customer() {
		super();
	}

	public Customer(String name, Long rib, double balance, String currency) {
		super();
		this.name = name;
		this.rib = rib;
		this.balance = balance;
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRib() {
		return rib;
	}

	public void setRib(Long rib) {
		this.rib = rib;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String printCustomer() {
		return "Customer [name=" + name + ", rib=" + rib + ", balance=" + balance + ", currency=" + currency + "]";
	}
}
