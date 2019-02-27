package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model_class.Account;
import model_class.Customer;
import dao.AccountDaoImpl;
import dao.CustomerDaoImpl;
import dao.OrderDaoImpl;
import view.CustomerView;

@Component
public class CustomerController extends Controller {
	
	@Autowired CustomerDaoImpl customerDao;
	@Autowired AccountDaoImpl accountDao;
	@Autowired OrderDaoImpl orderDao;
	
	@Autowired CustomerView customerView;

	@Override
	public void runController() {
		int keuze = 1;
		Controller.newView = true;
		do {
			if (Controller.newView) {
				customerView.clearTerminal();
				customerView.printMenuHeader();
				customerView.printMenuOptions();
				Controller.newView = false;
			}

			keuze = customerView.requestMenuOption();
			switch (keuze) {
			case 1:
				createCustomer();
				requestNewMenu();
				break;
			case 2:
				viewAllCustomers();
				requestNewMenu();
				break;
			case 3:
				runEditPersonMenu(choosePersonFromList());
				break;
			case 9:
				keuze = 0;
				Controller.newView = true;
				break;
			case 0:
				customerView.logoutTimer();
				System.exit(0);
				break;
			default:
				customerView.invalidInput();
				break;
			}
		} while (keuze != 0);
	}

	private void viewAllCustomers() {
		if (workerOrAdminPermission() == false)
			customerView.noPermission();
		List<Customer> customerList = customerDao
				.readAllCustomers();
		if (customerList.size() <= 0) {
			customerView.noCustomerFound();
			return;
		}
		for (int i = 0; i < customerList.size(); i++) {
			customerView.printPersonListWithoutOption(customerList);
		}
	}

	public void runEditPersonMenu(Customer customer) {
		int keuze = 1;
		Controller.newView = true;
		do {
			if (Controller.newView) {
				customerView.clearTerminal();
				customerView.printEditMenuHeader();
				customerView.printEditMenuOptions();
				Controller.newView = false;
			}
			keuze = customerView.requestMenuOption();
			switch (keuze) {
			case 1:
				updateCustomer(customer);
				requestNewMenu();
				break;
			case 2:
				DeleteCustomer(customer.getId());
				requestNewMenu();
				break;
			case 9:
				keuze = 0;
				Controller.newView = true;
				break;
			case 0:
				customerView.logoutTimer();
				System.exit(0);
				break;
			default:
				customerView.invalidInput();
				break;
			}
		} while (keuze != 0);
	}

	public Customer choosePersonFromList() {
		if (workerOrAdminPermission() == false) {
			Customer customer = LoginController.loggedInCustomer;
			return customer;
		}
		List<Customer> customerList = customerDao
		// .readCustomersByLastname(customerView.RequestSurnameForList());
				.readAllCustomers();
		if (customerList.size() <= 0) {
			customerView.noPersonFound();
			return null;
		}
		customerView.printPersonList(customerList);
		int option = customerView.choosePerson(customerList.size());
		Customer customer = customerList.get(option);
		return customer;
	}

	private boolean customerIdHasAccount(int customerId) {
		Account account = accountDao.readAccountByCustomerId(
				customerId);
		if (account.getId() == 0)
			return false;
		else
			return true;
	}

	private void DeleteCustomer(int customerId) {
		if (!orderDao.readOrdersOfCustomerId(customerId)
				.isEmpty()) {
			customerView.firstDeleteOrders();
			return;
		}
		if (customerIdHasAccount(customerId))
			customerView.firstDeleteAccount();

		else
			customerDao.deleteCustomer(customerId);
		// Print succesfull message
	}

	private void updateCustomer(Customer customer) {
		customer.setFirstname(customerView.requestInputFirstname());
		customer.setMiddlename(customerView.requestInputMiddlename());
		customer.setSurname(customerView.RequestInputSurname());
		customerDao.updateCustomer(customer);
		// Print succesfull message
	}

	private void createCustomer() {
		// Check if Account has permission
		if (!workerOrAdminPermission()) {
			return;
		}
		Customer customer = new Customer();
		customer.setFirstname(customerView.requestInputFirstname());
		customer.setMiddlename(customerView.requestInputMiddlename());
		customer.setSurname(customerView.RequestInputSurname());
		if (customerDao.CustomerNameExists(customer) == 0)
			customerDao.createCustomer(customer);
		else {
			customerView.alreadyExists();
		}
	}

	// replaced with choosepersonfromlist
	public Customer selectCustomer() {
		Customer customer = choosePersonFromList();
		if (customer == null) {
			System.out.println("MELDING HIER");
			return null;
		}
		return customer;
	}
}
