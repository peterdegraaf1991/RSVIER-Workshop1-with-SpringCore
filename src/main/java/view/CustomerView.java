package view;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import model_class.Customer;

public class CustomerView extends View {

	@Override
	public void printMenuHeader() {
		terminal.println("Customer Menu \n");
	}

	public void printEditMenuHeader() {
		terminal.println("Edit Customer Menu \n");
	}

	@Override
	public void printMenuOptions() {
		terminal.println("1. Create new user");
		terminal.println("2. View all users");
		terminal.println("3. Edit user");
		terminal.println("9. Back");
		terminal.println("0. Logout & Exit");
	}

	public void printEditMenuOptions() {
		terminal.println("1. Change Name");
		terminal.println("2. Delete Customer");
		terminal.println("9. Back");
		terminal.println("0. Logout & Exit");
	}

	public String RequestInputSurname() {
		String lastname = textIO.newStringInputReader()
				.withDefaultValue("Graaf").read("Enter surname");
		return lastname;
	}

	public String requestSurnameForList() {
		String lastname = textIO.newStringInputReader()
				.withDefaultValue("Graaf").read("Enter the lastname of the person you wish to select");
		return lastname;
	}
	public void printPersonList(List<Customer> customerList) {
		clearTerminal();
		terminal.println(StringUtils.center(
				"Overview of Customers & Employees", 46));
		terminal.println("----------------------------------------------");
		terminal.print(StringUtils.center("Option", 11)
				+ StringUtils.center("Name", 25)
				+ StringUtils.center("Account", 10));
		terminal.println();
		terminal.println("----------------------------------------------");

		for (int i = 0; i < customerList.size(); i++) {
			String accountDescription;
			if (customerList.get(i).getAccountDescription() == null){
				accountDescription = "None";
			}
			else
				accountDescription = customerList.get(i)
						.getAccountDescription();
			terminal.print(StringUtils.center(Integer.toString(i), 11)
					+ StringUtils.center(customerList.get(i).toString(), 25)
					+ StringUtils.center(accountDescription, 10));
			terminal.println();
		}
		terminal.println("----------------------------------------------");

	}

	public int choosePerson(int i) {
		int option = textIO.newIntInputReader().withMinVal(0).withMaxVal(i - 1)
				.read("Choose the person you want to select for this action");
		return option;
	}

	public void noPersonFound() {
		terminal.println("No person with that lastname found\nPlease create a person first");
	}

	public String requestInputFirstname() {
		String firstname = textIO.newStringInputReader()
				.read("Enter firstname");
		return firstname;
	}

	public String requestInputMiddlename() {
		String middlename = textIO.newStringInputReader().withMinLength(0)
				.read("Enter middlename");
		return middlename;
	}

	public void alreadyExists() {
		terminal.println("Cannot create customer: customer with that name already exists");
	}

	public void firstDeleteAccount() {
		terminal.println("First delete the account of this person");

	}

	public void firstDeleteOrders() {
		terminal.println("First delete all orders of this customer");

	}

	public void noCustomerFound() {
		terminal.println("No users have been found");

	}

	public void printPersonListWithoutOption(List<Customer> customerList) {
		clearTerminal();
		terminal.println(StringUtils.center(
				"Overview of Customers & Employees ", 46));
		terminal.println("----------------------------------------------");
		terminal.print(StringUtils.center("ID", 11)
				+ StringUtils.center("Name", 25)
				+ StringUtils.center("Account", 10));
		terminal.println();
		terminal.println("----------------------------------------------");

		for (int i = 0; i < customerList.size(); i++) {
			String accountDescription;
			if (customerList.get(i).getAccountDescription() == null)
				accountDescription = "None";
			else
				accountDescription = customerList.get(i)
						.getAccountDescription();
			terminal.print(StringUtils.center(
					Integer.toString(customerList.get(i).getId()), 11)
					+ StringUtils.center(customerList.get(i).toString(), 25)
					+ StringUtils.center(accountDescription, 10));
			terminal.println();
		}
		terminal.println("----------------------------------------------");

	}

}