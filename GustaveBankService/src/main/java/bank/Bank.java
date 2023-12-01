package bank;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.rmi.Naming;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import FxtopAPI.ConvertResult;
import FxtopAPI.FxtopServicesLocator;
import FxtopAPI.FxtopServicesPortType;

public class Bank {
	private HashMap<Long, Customer> customers;

	public Bank() {
		super();
		this.customers = new HashMap<Long, Customer>();

	}

	public Bank(HashMap<Long, Customer> customers) {
		super();
		this.customers = customers;
	}

	public Customer[] getCustomers() {

		List<Customer> customerList = new ArrayList<>(customers.values());
		Customer[] customerTab = new Customer[customerList.size()];

		for (int i = 0; i < customerList.size(); i++) {
			customerTab[i] = customerList.get(i);
		}

		return customerTab;
	}

	public long generateRIB() {
		long leftLimit = 00000000000L;
		long rightLimit = 99999999999L;
		long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		if ((int) (Math.log10(generatedLong) + 1) != 11) {
			return generateRIB();
		}
		return generatedLong;
	}

	public void newAccount(String name, double balance, String currency) {
		if (customers != null) {
			for (Customer customer : customers.values()) {

				if (customer.getName().equals(name)) {
					System.out.println("You already have an account here");
					return;
				}
			}

			Long rib = generateRIB();
			Customer newCustomer;
			newCustomer = new Customer(name, rib, balance, currency);
			customers.put(rib, newCustomer);
			System.out.println("Account created successfully");
		} else {
			System.out.println("There are no bank accounts");
		}

	}

	public double getBalance(Long rib) {
		if (customers != null) {
			for (Customer customer : customers.values()) {
				if (customer.getRib().equals(rib)) {
					return customer.getBalance();
				}
			}
		} else {
			System.out.println("There are no bank accounts");
		}

		return -1;
	}

	public double currencyConversion(double amount, String from, String to) {
		try {
			FxtopServicesPortType service = new FxtopServicesLocator().getFxtopServicesPort();

			ConvertResult converted = service.convert(String.valueOf(amount), from, to, "", "", "");
			double convertedd = Double.parseDouble(converted.getResultAmount());
			BigDecimal round = new BigDecimal(convertedd).setScale(2, RoundingMode.HALF_UP);
			System.out.println(
					"Conversion : amount = " + amount + " From currency " + from + " to " + to + " = " + round);
			return round.doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void virement(Long fromRib, Long toRib, double amount) {
		try {
			if (customers != null) {

				Customer from = new Customer();
				Customer to = new Customer();
				for (Customer customer : customers.values()) {
					if (customer.getRib().equals(fromRib))
						from = customer;
					if (customer.getRib().equals(toRib))
						to = customer;
				}

				if (from.getName() != null && to.getName() != null) {
					double convertedAmount = currencyConversion(amount, to.getCurrency(), from.getCurrency()); 
					if (from.getBalance() >= convertedAmount) {
						from.setBalance(from.getBalance() - convertedAmount);
						to.setBalance(to.getBalance() + amount);
						
//						ITutorsList tutors = (ITutorsList) Naming.lookup("rmi://localhost:1090/tutors");
//						ITutor tutor = (ITutor) tutors.searchTutorsByName(to.getName());
//						
//						
//						IAppointmentList appointments = (IAppointmentList) Naming.lookup("rmi://localhost:1090/appointments");
//						IAppointment = appointments.getAppointment(tutor, null)

						System.out.println("Transaction Success : from = " + from.getName() + ", to = " + to.getName()
								+ ", amount from = " + amount + ", amount to = " + convertedAmount);

					} else {
						System.out.println("You have " + from.getBalance() + from.getCurrency()
								+ " in your account. That is not enough to pay : " + amount + to.getCurrency() + " = "
								+ convertedAmount + from.getCurrency());
					}
				}
			} else {
				System.out.println("There are no bank accounts");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void printCustomers() {
		if (customers != null) {

			for (Customer customer : customers.values()) {
				System.out.println("Account : Name = " + customer.getName() + ", RIB = " + customer.getRib()
						+ ", balance = " + customer.getBalance() + ", currency = " + customer.getCurrency());
			}
		} else {
			System.out.println("There are no bank accounts");
		}
	}

	public Customer retrieveCustomerByName(String name) {
		if (customers != null) {

			for (Customer customer : customers.values()) {
				if (customer.getName().trim().toLowerCase().equals(name.trim().toLowerCase())) {
					return customer;
				}
			}
		} else {
			System.out.println("There are no bank accounts");
		}
		return null;
	}
}
